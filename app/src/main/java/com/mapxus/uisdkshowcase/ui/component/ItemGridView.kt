package com.mapxus.uisdkshowcase.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mapxus.uisdkshowcase.model.item.AppearanceMode
import com.mapxus.uisdkshowcase.model.item.Colors
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.model.item.Shapes
import com.mapxus.uisdkshowcase.ui.theme.UISDKShowcaseTheme

@Composable
fun ItemGridView(items: List<Item>, modifier: Modifier = Modifier, onItemClicked: (Item) -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items.forEach { item ->
            ItemCard(
                item = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClicked(item) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemGridViewPreview() {
    UISDKShowcaseTheme {
        val sampleItems = listOf(
            AppearanceMode,
            Colors,
            Shapes
        )
        ItemGridView(
            items = sampleItems,
            onItemClicked = {}
        )
    }
}
