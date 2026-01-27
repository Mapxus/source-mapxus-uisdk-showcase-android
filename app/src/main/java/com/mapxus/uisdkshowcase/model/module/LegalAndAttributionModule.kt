package com.mapxus.uisdkshowcase.model.module

import com.mapxus.uisdkshowcase.model.item.AttributionConfig
import com.mapxus.uisdkshowcase.model.item.CopyrightConfig
import com.mapxus.uisdkshowcase.model.item.IsLegalLinksVisible
import com.mapxus.uisdkshowcase.model.item.Item

object LegalAndAttributionModule : Module {
    override val title: String = "Legal & Attribution"

    override val items: List<Item> = listOf(
        CopyrightConfig,
        AttributionConfig,
        IsLegalLinksVisible
    )
}
