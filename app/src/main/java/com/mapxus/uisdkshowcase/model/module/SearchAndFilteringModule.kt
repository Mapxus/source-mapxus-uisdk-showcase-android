package com.mapxus.uisdkshowcase.model.module

import com.mapxus.uisdkshowcase.model.item.FilterChipsConfig
import com.mapxus.uisdkshowcase.model.item.GlobalFilterModes
import com.mapxus.uisdkshowcase.model.item.GlobalFilterTagIds
import com.mapxus.uisdkshowcase.model.item.Item

object SearchAndFilteringModule : Module {
    override val title: String = "Search and Filtering"

    override val items: List<Item> = listOf(
        GlobalFilterModes,
        GlobalFilterTagIds,
        FilterChipsConfig
    )
}
