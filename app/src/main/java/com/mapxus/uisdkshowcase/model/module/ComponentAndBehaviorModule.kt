package com.mapxus.uisdkshowcase.model.module

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.ui.screen.ComponentAndBehaviorScreen

object ComponentAndBehaviorModule : Module {
    override val title: String = "Component & Behavior"

    override val items: List<Item>? = null

    @Composable
    override fun DetailScreen(modifier: Modifier) {
        ComponentAndBehaviorScreen(modifier)
    }
}
