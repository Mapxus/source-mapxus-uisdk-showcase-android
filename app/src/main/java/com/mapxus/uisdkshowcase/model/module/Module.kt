package com.mapxus.uisdkshowcase.model.module

import androidx.compose.runtime.Composable
import com.mapxus.uisdkshowcase.model.item.Item

sealed interface Module {
    val title: String
    val items: List<Item>
}