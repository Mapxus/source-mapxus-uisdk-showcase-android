package com.mapxus.uisdkshowcase.model.module

import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.model.item.EnableBearingCalibration
import com.mapxus.uisdkshowcase.model.item.EnableReroute
import com.mapxus.uisdkshowcase.model.item.MaximumRoutePlanningDistance
import com.mapxus.uisdkshowcase.model.item.NavigationModes
import com.mapxus.uisdkshowcase.model.item.NavigationRoadSnapStrength
import com.mapxus.uisdkshowcase.model.item.NoRouteAvailableMessage
import com.mapxus.uisdkshowcase.model.item.PublicTransportModes
import com.mapxus.uisdkshowcase.model.item.RouteRenderMode

object NavigationModule : Module {
    override val title: String = "Navigation"

    override val items: List<Item> = listOf(
        NavigationModes,
        PublicTransportModes,
        MaximumRoutePlanningDistance,
        NavigationRoadSnapStrength,
        RouteRenderMode,
        EnableReroute,
        EnableBearingCalibration,
//        NoRouteAvailableTitle,
        NoRouteAvailableMessage
    )
}
