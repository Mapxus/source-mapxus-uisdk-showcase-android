package com.mapxus.uisdkshowcase.model.item

import com.mapxus.uisdkshowcase.R

object AppearanceMode : Item {
    override val title: String = "Appearance Mode"
    override val description: String = "Configure light or dark appearance mode"
    override val photoResInt: Int = R.drawable.ic_launcher_background
}

object Colors : Item {
    override val title: String = "Colors"
    override val description: String = "Configure theme colors"
    override val photoResInt: Int = R.drawable.ic_launcher_background
}

object Shapes : Item {
    override val title: String = "Shapes"
    override val description: String = "Configure theme shapes"
    override val photoResInt: Int = R.drawable.ic_launcher_background
}

object MaterialResourcePath : Item {
    override val title: String = "Material Resource Path"
    override val description: String = "Configure path for material resources"
    override val photoResInt: Int = R.drawable.ic_launcher_background
}
