package com.mapxus.uisdkshowcase.model.module

import com.mapxus.uisdkshowcase.model.item.InitialBounds
import com.mapxus.uisdkshowcase.model.item.InitialMapBearing
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.model.item.MapBoundsRestriction
import com.mapxus.uisdkshowcase.model.item.MapStyle

object MapBasicModule : Module {
    override val title: String = "Map Basic"

    override val items: List<Item> = listOf(
        InitialBounds,
        MapBoundsRestriction,
        MapStyle,
        InitialMapBearing
    )
}
