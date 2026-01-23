package com.mapxus.uisdkshowcase.model.module

import com.mapxus.uisdkshowcase.model.item.InitialBounds
import com.mapxus.uisdkshowcase.model.item.Item

object MapBasicModule : Module {
    override val title: String = "Map Basic"

    override val items: List<Item> = listOf(InitialBounds)
}