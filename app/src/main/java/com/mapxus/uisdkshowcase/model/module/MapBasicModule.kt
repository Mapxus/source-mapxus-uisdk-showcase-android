package com.mapxus.uisdkshowcase.model.module

import com.mapxus.uisdkshowcase.model.item.InitialBounds
import com.mapxus.uisdkshowcase.model.item.InitialMapBearing
import com.mapxus.uisdkshowcase.model.item.InitialMapPitch
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.model.item.FloorSwitchScope
import com.mapxus.uisdkshowcase.model.item.MapBoundsRestriction
import com.mapxus.uisdkshowcase.model.item.MapStyle
import com.mapxus.uisdkshowcase.model.item.MapStyleDark
import com.mapxus.uisdkshowcase.model.item.SelectedBuildingBorderStyle

object MapBasicModule : Module {
    override val title: String = "Map Basic"

    override val items: List<Item> = listOf(
        InitialBounds,
        MapBoundsRestriction,
        MapStyle,
        MapStyleDark,
        InitialMapBearing,
        InitialMapPitch,
        SelectedBuildingBorderStyle,
        FloorSwitchScope
    )
}
