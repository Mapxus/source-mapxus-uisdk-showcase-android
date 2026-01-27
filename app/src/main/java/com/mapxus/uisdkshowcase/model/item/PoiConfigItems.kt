package com.mapxus.uisdkshowcase.model.item

import com.mapxus.uisdkshowcase.R

object FixedDisplayCategories : Item {
    override val title: String = "Fixed Display Categories"
    override val description: String = "Configure categories that are always displayed"
    override val photoResInt: Int = R.drawable.ic_launcher_background
}

object PoiDetailSections : Item {
    override val title: String = "POI Detail Sections"
    override val description: String = "Configure visible sections in POI detail view"
    override val photoResInt: Int = R.drawable.ic_launcher_background
}

object RecommendedCategories : Item {
    override val title: String = "Recommended Categories"
    override val description: String = "Configure recommended categories for discovery"
    override val photoResInt: Int = R.drawable.ic_launcher_background
}

object RecommendedPoiIds : Item {
    override val title: String = "Recommended POI IDs"
    override val description: String = "Configure specific POIs to be recommended"
    override val photoResInt: Int = R.drawable.ic_launcher_background
}

object PoiSorting : Item {
    override val title: String = "POI Sorting"
    override val description: String = "Configure the sorting order of POIs"
    override val photoResInt: Int = R.drawable.ic_launcher_background
}

object CategoryListConfig : Item {
    override val title: String = "Category List Config"
    override val description: String = "Configure the appearance and behavior of category lists"
    override val photoResInt: Int = R.drawable.ic_launcher_background
}
