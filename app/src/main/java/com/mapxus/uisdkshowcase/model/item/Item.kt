package com.mapxus.uisdkshowcase.model.item

import androidx.annotation.IntegerRes

sealed interface Item {
    val title: String
    val description: String
    val photoResInt: Int
}