package com.mapxus.uisdkshowcase.model.item

import com.mapxus.uisdkshowcase.R

object CopyrightConfig : Item {
    override val title: String = "Copyright Config"
    override val description: String = "Configure copyright information"
    override val photoResInt: Int = R.drawable.ic_launcher_background
}

object AttributionConfig : Item {
    override val title: String = "Attribution Config"
    override val description: String = "Configure attribution information"
    override val photoResInt: Int = R.drawable.ic_launcher_background
}

object IsLegalLinksVisible : Item {
    override val title: String = "Is Legal Links Visible"
    override val description: String = "Configure visibility of legal links"
    override val photoResInt: Int = R.drawable.ic_launcher_background
}
