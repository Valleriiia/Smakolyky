package com.smakolyky.ui.screens.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smakolyky.domain.repository.ShoppingItem
import com.smakolyky.domain.repository.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val repository: ShoppingRepository,
) : ViewModel() {

    val items: StateFlow<List<ShoppingItem>> = repository.observeItems()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun toggleChecked(item: ShoppingItem) {
        viewModelScope.launch { repository.toggleChecked(item) }
    }

    fun clearChecked() {
        viewModelScope.launch { repository.clearChecked() }
    }
}
