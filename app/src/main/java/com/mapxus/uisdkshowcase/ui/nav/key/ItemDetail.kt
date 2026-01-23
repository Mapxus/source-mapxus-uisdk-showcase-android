package com.mapxus.uisdkshowcase.ui.nav.key

import androidx.navigation3.runtime.NavKey
import com.mapxus.uisdkshowcase.model.item.Item
import kotlinx.serialization.Serializable

@Serializable
class ItemDetail(val item: Item) : NavKey