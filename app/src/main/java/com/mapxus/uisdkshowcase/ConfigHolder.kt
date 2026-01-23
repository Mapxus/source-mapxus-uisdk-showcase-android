package com.mapxus.uisdkshowcase

import com.mapxus.dropin.uicore.api.DIConfig
import com.mapxus.dropin.uicore.api.DIConfigBuilder

object ConfigHolder {
    private val configBuilder = DIConfigBuilder()

    fun getConfig() = configBuilder.build()

    var initialBounds
        get() = configBuilder.initialBounds
        set(value) { configBuilder.initialBounds = value }
}