package com.mapxus.uisdkshowcase.model.module

import com.mapxus.uisdkshowcase.model.item.AppearanceMode
import com.mapxus.uisdkshowcase.model.item.Colors
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.model.item.MaterialResourcePath
import com.mapxus.uisdkshowcase.model.item.Shapes

object AppearanceModule : Module {
    override val title: String = "UI Appearance & Theme"
    override val items: List<Item> = listOf(
        AppearanceMode,
        Colors,
        Shapes,
        MaterialResourcePath
    )
}
