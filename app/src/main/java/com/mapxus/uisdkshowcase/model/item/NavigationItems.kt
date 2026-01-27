package com.mapxus.uisdkshowcase.model.item

import com.mapxus.uisdkshowcase.R

object NavigationModes : Item {
    override val title: String = "Navigation Modes"
    override val description: String = "Configure supported navigation modes"
    override val photoResInt: Int = R.drawable.ic_launcher_background
}

object PublicTransportModes : Item {
    override val title: String = "Public Transport Modes"
    override val description: String = "Configure supported public transport modes"
    override val photoResInt: Int = R.drawable.ic_launcher_background
}

object MaximumRoutePlanningDistance : Item {
    override val title: String = "Max Route Planning Distance"
    override val description: String = "Configure maximum allowed distance for route planning"
    override val photoResInt: Int = R.drawable.ic_launcher_background
}

object NavigationRoadSnapStrength : Item {
    override val title: String = "Navigation Road Snap Strength"
    override val description: String = "Configure how strongly the navigation snaps to roads"
    override val photoResInt: Int = R.drawable.ic_launcher_background
}

object NoRouteAvailableTitle : Item {
    override val title: String = "No Route Available Title"
    override val description: String = "Configure title when no route is available"
    override val photoResInt: Int = R.drawable.ic_launcher_background
}

object NoRouteAvailableMessage : Item {
    override val title: String = "No Route Available Message"
    override val description: String = "Configure message when no route is available"
    override val photoResInt: Int = R.drawable.ic_launcher_background
}
