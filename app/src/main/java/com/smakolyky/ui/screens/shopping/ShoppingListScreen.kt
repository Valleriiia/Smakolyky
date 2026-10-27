package com.smakolyky.ui.screens.shopping

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smakolyky.domain.repository.ShoppingItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(viewModel: ShoppingListViewModel = hiltViewModel()) {
    val items by viewModel.items.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Список покупок") },
                actions = {
                    IconButton(onClick = viewModel::clearChecked) {
                        Icon(Icons.Default.Delete, contentDescription = "Очистити позначені")
                    }
                },
            )
        },
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
            items(items.groupBy { it.recipeTitle ?: "Інше" }.toList()) { (recipeTitle, group) ->
                Text(
                    text = recipeTitle,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                )
                group.forEach { item -> ShoppingRow(item, onToggle = { viewModel.toggleChecked(item) }) }
            }
        }
    }
}

@Composable
private fun ShoppingRow(item: ShoppingItem, onToggle: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 2.dp),
    ) {
        Checkbox(checked = item.isChecked, onCheckedChange = { onToggle() })
        Text("${item.ingredientName} — ${item.measure}")
    }
}
