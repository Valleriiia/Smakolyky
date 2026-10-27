package com.smakolyky.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smakolyky.domain.model.Recipe
import com.smakolyky.domain.repository.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: FavoritesRepository,
) : ViewModel() {

    val favorites: StateFlow<List<Recipe>> = repository.observeFavorites()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun removeFavorite(recipe: Recipe) {
        viewModelScope.launch { repository.toggleFavorite(recipe) }
    }
}
