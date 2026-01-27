package com.mapxus.uisdkshowcase.model.module

import com.mapxus.uisdkshowcase.model.item.CloseButtonTitle
import com.mapxus.uisdkshowcase.model.item.EventModuleTitle
import com.mapxus.uisdkshowcase.model.item.EventOverviewTitle
import com.mapxus.uisdkshowcase.model.item.GpsModeDisplayName
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.model.item.Language
import com.mapxus.uisdkshowcase.model.item.PublicHolidayDisplayName
import com.mapxus.uisdkshowcase.model.item.SendLogButtonTitle
import com.mapxus.uisdkshowcase.model.item.SettingsLanguageOptions
import com.mapxus.uisdkshowcase.model.item.ToolTipsConfig

object LanguageModule : Module {
    override val title: String = "Language"

    override val items: List<Item> = listOf(
        Language,
        SettingsLanguageOptions,
        PublicHolidayDisplayName,
        GpsModeDisplayName,
        CloseButtonTitle,
        SendLogButtonTitle,
        EventModuleTitle,
        EventOverviewTitle,
        ToolTipsConfig
    )
}
