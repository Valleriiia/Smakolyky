package com.smakolyky.ui.screens.recipedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smakolyky.common.UiState
import com.smakolyky.domain.model.Recipe
import com.smakolyky.domain.repository.FavoritesRepository
import com.smakolyky.domain.repository.RecipeRepository
import com.smakolyky.domain.repository.ShoppingRepository
import com.smakolyky.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel
    @Inject
    constructor(
        private val recipeRepository: RecipeRepository,
        private val favoritesRepository: FavoritesRepository,
        private val shoppingRepository: ShoppingRepository,
        savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val recipeId: String = checkNotNull(savedStateHandle[Screen.RecipeDetails.ARG_RECIPE_ID])

        private val _uiState = MutableStateFlow<UiState<Recipe>>(UiState.Loading)
        val uiState: StateFlow<UiState<Recipe>> = _uiState.asStateFlow()

        val isFavorite: StateFlow<Boolean> =
            favoritesRepository
                .isFavorite(recipeId)
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

        init {
            viewModelScope.launch {
                recipeRepository.getRecipe(recipeId).collect { recipe ->
                    _uiState.value = if (recipe != null) UiState.Success(recipe) else UiState.Error("Рецепт не знайдено")
                }
            }
        }

        fun onFavoriteClick() {
            val recipe = (_uiState.value as? UiState.Success)?.data ?: return
            viewModelScope.launch { favoritesRepository.toggleFavorite(recipe) }
        }

        fun addToShoppingList() {
            val recipe = (_uiState.value as? UiState.Success)?.data ?: return
            viewModelScope.launch { shoppingRepository.addFromRecipe(recipe) }
        }
    }
