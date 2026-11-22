package com.smakolyky.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smakolyky.domain.model.Category

@Composable
fun FilterBar(
    categories: List<Category>,
    selectedCategory: String?,
    onCategorySelected: (String?) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding =
            androidx.compose.foundation.layout
                .PaddingValues(horizontal = 12.dp),
        modifier = modifier,
    ) {
        items(categories, key = { it.id }) { category ->
            val isSelected = category.name == selectedCategory
            FilterChip(
                selected = isSelected,
                onClick = { onCategorySelected(if (isSelected) null else category.name) },
                label = { androidx.compose.material3.Text(category.name) },
            )
        }
    }
}
