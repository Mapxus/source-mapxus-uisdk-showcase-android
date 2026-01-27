package com.mapxus.uisdkshowcase.model.module

import com.mapxus.uisdkshowcase.model.item.IsShoplusButtonVisible
import com.mapxus.uisdkshowcase.model.item.IsVenueBackButtonVisible
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.model.item.ShareDisplayMode

object ComponentAndBehaviorModule : Module {
    override val title: String = "Component & Behavior"

    override val items: List<Item> = listOf(
        IsShoplusButtonVisible,
        IsVenueBackButtonVisible,
        ShareDisplayMode
    )
}
