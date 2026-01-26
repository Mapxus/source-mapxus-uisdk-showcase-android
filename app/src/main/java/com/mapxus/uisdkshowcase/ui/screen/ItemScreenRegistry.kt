package com.mapxus.uisdkshowcase.ui.screen

import androidx.compose.runtime.Composable
import com.mapxus.uisdkshowcase.model.item.AppearanceMode
import com.mapxus.uisdkshowcase.model.item.Colors
import com.mapxus.uisdkshowcase.model.item.InitialBounds
import com.mapxus.uisdkshowcase.model.item.InitialMapBearing
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.model.item.MapBoundsRestriction
import com.mapxus.uisdkshowcase.model.item.MapStyle
import com.mapxus.uisdkshowcase.model.item.MaterialResourcePath
import com.mapxus.uisdkshowcase.model.item.Shapes

@Composable
fun Item.Screen(onBack: () -> Unit) {
    when (this) {
        InitialBounds -> InitialBoundsScreen(this, onBack = onBack)
        MapBoundsRestriction -> MapBoundsRestrictionScreen(this, onBack = onBack)
        InitialMapBearing -> InitialMapBearingScreen(this, onBack = onBack)
        MapStyle -> MapStyleScreen(this, onBack = onBack)
        AppearanceMode -> AppearanceModeScreen(this, onBack = onBack)
        Colors -> ColorsScreen(this, onBack = onBack)
        Shapes -> ShapesScreen(this, onBack = onBack)
        MaterialResourcePath -> MaterialResourcePathScreen(this, onBack = onBack)
    }
}
