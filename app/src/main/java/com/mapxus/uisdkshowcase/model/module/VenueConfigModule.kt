package com.mapxus.uisdkshowcase.model.module

import com.mapxus.uisdkshowcase.model.item.BuildingSectionTitle
import com.mapxus.uisdkshowcase.model.item.IsBuildingListVisible
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.model.item.SharedFloorsUnifiedNames
import com.mapxus.uisdkshowcase.model.item.VenueAnchorPoiConfigs
import com.mapxus.uisdkshowcase.model.item.VenueDefaultSharedFloorIds
import com.mapxus.uisdkshowcase.model.item.VenueHighlightedShopTitle
import com.mapxus.uisdkshowcase.model.item.VenueLevelFacilityInfoConfigs

object VenueConfigModule : Module {
    override val title: String = "Venue Configuration"
    override val items: List<Item> = listOf(
        IsBuildingListVisible,
        BuildingSectionTitle,
        VenueHighlightedShopTitle,
//        FloorSelectorCategories,
        VenueDefaultSharedFloorIds,
        SharedFloorsUnifiedNames,
        VenueAnchorPoiConfigs,
        VenueLevelFacilityInfoConfigs
    )
}
