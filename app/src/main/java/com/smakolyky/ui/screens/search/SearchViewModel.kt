package com.smakolyky.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smakolyky.common.UiState
import com.smakolyky.domain.model.Area
import com.smakolyky.domain.model.Category
import com.smakolyky.domain.model.Recipe
import com.smakolyky.domain.model.RecipeFilter
import com.smakolyky.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel
    @Inject
    constructor(
        private val repository: RecipeRepository,
    ) : ViewModel() {
        private val _query = MutableStateFlow("")
        val query: StateFlow<String> = _query.asStateFlow()

        private val _filter = MutableStateFlow(RecipeFilter())
        val filter: StateFlow<RecipeFilter> = _filter.asStateFlow()

        private val _categories = MutableStateFlow<List<Category>>(emptyList())
        val categories: StateFlow<List<Category>> = _categories.asStateFlow()

        private val _areas = MutableStateFlow<List<Area>>(emptyList())
        val areas: StateFlow<List<Area>> = _areas.asStateFlow()

        private val _uiState = MutableStateFlow<UiState<List<Recipe>>>(UiState.Success(emptyList()))
        val uiState: StateFlow<UiState<List<Recipe>>> = _uiState.asStateFlow()

        init {
            viewModelScope.launch { repository.getCategories().catch { }.collect { _categories.value = it } }
            viewModelScope.launch { repository.getAreas().catch { }.collect { _areas.value = it } }

            combine(
                _query.debounce(500).distinctUntilChanged(),
                _filter,
            ) { q, f -> q to f }
                .flatMapLatest { (q, f) ->
                    flow<UiState<List<Recipe>>> {
                        emit(UiState.Loading)
                        val source =
                            when {
                                f.category != null -> repository.getByCategory(f.category)
                                f.area != null -> repository.getByArea(f.area)
                                q.isNotBlank() -> repository.searchRecipes(q)
                                else -> flowOf(emptyList<Recipe>())
                            }
                        emitAll(source.map { UiState.Success(it) })
                    }.catch { e -> emit(UiState.Error(e.message ?: "Сталася помилка")) }
                }.onEach { _uiState.value = it }
                .launchIn(viewModelScope)
        }

        fun onQueryChange(newQuery: String) {
            _query.value = newQuery
            if (newQuery.isNotBlank()) _filter.value = RecipeFilter()
        }

        fun onCategorySelected(category: String?) {
            _filter.value = RecipeFilter(category = category, area = null)
            if (category != null) _query.value = ""
        }

        fun onAreaSelected(area: String?) {
            _filter.value = RecipeFilter(category = null, area = area)
            if (area != null) _query.value = ""
        }
    }
