package com.mapxus.uisdkshowcase

import com.mapxus.dropin.uicore.api.DIConfigBuilder

object ConfigHolder {
    private val configBuilder = DIConfigBuilder()

    fun getConfig() = configBuilder.build()

    var initialBounds
        get() = configBuilder.initialBounds
        set(value) { configBuilder.initialBounds = value }

    var mapBoundsRestriction
        get() = configBuilder.mapBoundsRestriction
        set(value) {
            configBuilder.mapBoundsRestriction = value
        }

    var initialMapBearing
        get() = configBuilder.initialMapBearing
        set(value) {
            configBuilder.initialMapBearing = value
        }

    var mapStyle
        get() = configBuilder.mapStyle
        set(value) {
            configBuilder.mapStyle = value
        }

    var appearanceMode
        get() = configBuilder.appearanceMode
        set(value) {
            configBuilder.appearanceMode = value
        }

    var colors
        get() = configBuilder.colors
        set(value) {
            configBuilder.colors = value
        }

    var shapes
        get() = configBuilder.shapes
        set(value) {
            configBuilder.shapes = value
        }

    var materialResourcePath
        get() = configBuilder.materialResourcePath
        set(value) {
            configBuilder.materialResourcePath = value
        }
}