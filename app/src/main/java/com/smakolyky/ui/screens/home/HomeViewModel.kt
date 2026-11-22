package com.smakolyky.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smakolyky.common.UiState
import com.smakolyky.domain.model.Recipe
import com.smakolyky.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val repository: RecipeRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow<UiState<List<Recipe>>>(UiState.Loading)
        val uiState: StateFlow<UiState<List<Recipe>>> = _uiState.asStateFlow()

        init {
            loadHome()
        }

        fun loadHome() {
            viewModelScope.launch {
                _uiState.value = UiState.Loading
                // Тимчасова заглушка "Рецепта дня" — заміниться на random.php пізніше
                repository
                    .searchRecipes("chicken")
                    .catch { e -> _uiState.value = UiState.Error(e.message ?: "Не вдалося завантажити рецепти") }
                    .collect { recipes -> _uiState.value = UiState.Success(recipes) }
            }
        }

        fun loadRandom(onLoaded: (String) -> Unit) {
            viewModelScope.launch {
                repository
                    .getRandomRecipe()
                    .catch { }
                    .collect { onLoaded(it.id) }
            }
        }
    }
