package com.mapxus.uisdkshowcase.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mapxus.uisdkshowcase.model.item.Item

@Composable
fun ItemViewPager(items: List<Item>, modifier: Modifier = Modifier, onItemClicked: (Item) -> Unit) {
    val pagerState = rememberPagerState { items.size }
    HorizontalPager(pagerState, modifier) {
        val item = items[it]
        ItemCard(item, Modifier.clickable { onItemClicked(item) })
    }
}