package com.mapxus.uisdkshowcase.ui.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.ui.component.ItemDetailFramework
import kotlinx.coroutines.launch

@Composable
fun SharedFloorsUnifiedNamesScreen(item: Item, onBack: () -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    ItemDetailFramework(
        item = item,
        onBack = onBack,
        snackbarHostState = snackbarHostState,
        onSaveClicked = {
            scope.launch {
                snackbarHostState.showSnackbar("Shared Floors Unified Names saved")
            }
        }
    ) {
        Text(
            text = "Placeholder for Shared Floors Unified Names configuration UI",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}
