package com.mapxus.uisdkshowcase.model.module

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mapxus.uisdkshowcase.model.item.Item

sealed interface Module {
    val title: String
    val items: List<Item>?

    @Composable
    fun DetailScreen(modifier: Modifier = Modifier) {
        Text("Placeholder for Module, you should not see this.")
    }
}