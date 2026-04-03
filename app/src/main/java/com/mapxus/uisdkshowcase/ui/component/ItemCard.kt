package com.mapxus.uisdkshowcase.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mapxus.uisdkshowcase.model.item.InitialBounds
import com.mapxus.uisdkshowcase.model.item.Item

@Composable
fun ItemCard(item: Item, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Row(
            Modifier.padding(20.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Column(modifier.weight(1f)) {
                Text(item.title, fontWeight = FontWeight.Bold)
                Text(item.description)
            }

            Icon(
                imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                contentDescription = "Go to details",
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Preview
@Composable
private fun ItemCardPreview() {
    ItemCard(item = InitialBounds)
}