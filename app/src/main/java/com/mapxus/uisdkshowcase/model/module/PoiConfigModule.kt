package com.mapxus.uisdkshowcase.model.module

import com.mapxus.uisdkshowcase.model.item.CategoryListConfig
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.model.item.PoiDetailSections
import com.mapxus.uisdkshowcase.model.item.PoiSorting
import com.mapxus.uisdkshowcase.model.item.RecommendedCategories
import com.mapxus.uisdkshowcase.model.item.RecommendedPoiIds

object PoiConfigModule : Module {
    override val title: String = "POI Configuration"
    override val items: List<Item> = listOf(
//        FixedDisplayCategories,
        PoiDetailSections,
        RecommendedCategories,
        RecommendedPoiIds,
        PoiSorting,
        CategoryListConfig
    )
}
