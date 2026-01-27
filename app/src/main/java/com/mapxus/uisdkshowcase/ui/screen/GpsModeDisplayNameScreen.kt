package com.mapxus.uisdkshowcase.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.ui.component.ItemDetailFramework

@Composable
fun GpsModeDisplayNameScreen(item: Item, onBack: () -> Unit) {
    ItemDetailFramework(
        item = item,
        onBack = onBack,
        onSaveClicked = { /* TODO */ }
    ) {
        Text("GPS Mode Display Name Configuration", modifier = Modifier.padding(16.dp))
    }
}
