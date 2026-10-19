package com.smakolyky.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smakolyky.common.UiState
import com.smakolyky.domain.model.Recipe
import com.smakolyky.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: RecipeRepository,
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _uiState = MutableStateFlow<UiState<List<Recipe>>>(UiState.Success(emptyList()))
    val uiState: StateFlow<UiState<List<Recipe>>> = _uiState.asStateFlow()

    init {
        _query
            .debounce(500)
            .distinctUntilChanged()
            .filter { it.isNotBlank() }
            .onEach { _uiState.value = UiState.Loading }
            .flatMapLatest { q -> repository.searchRecipes(q) }
            .catch { e -> _uiState.value = UiState.Error(e.message ?: "Сталася помилка") }
            .onEach { recipes -> _uiState.value = UiState.Success(recipes) }
            .launchIn(viewModelScope)
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
        if (newQuery.isBlank()) _uiState.value = UiState.Success(emptyList())
    }
}
