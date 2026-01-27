package com.mapxus.uisdkshowcase.model.module

import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.model.item.MaximumRoutePlanningDistance
import com.mapxus.uisdkshowcase.model.item.NavigationModes
import com.mapxus.uisdkshowcase.model.item.NavigationRoadSnapStrength
import com.mapxus.uisdkshowcase.model.item.NoRouteAvailableMessage
import com.mapxus.uisdkshowcase.model.item.NoRouteAvailableTitle
import com.mapxus.uisdkshowcase.model.item.PublicTransportModes

object NavigationModule : Module {
    override val title: String = "Navigation"

    override val items: List<Item> = listOf(
        NavigationModes,
        PublicTransportModes,
        MaximumRoutePlanningDistance,
        NavigationRoadSnapStrength,
        NoRouteAvailableTitle,
        NoRouteAvailableMessage
    )
}
