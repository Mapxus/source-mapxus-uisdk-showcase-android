package com.mapxus.uisdkshowcase.ui.screen

import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mapxus.uisdkshowcase.ConfigHolder
import com.mapxus.uisdkshowcase.model.item.AppearanceMode
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.ui.component.ItemDetailFramework
import kotlinx.coroutines.launch

@Composable
fun AppearanceModeScreen(item: Item, modifier: Modifier = Modifier, onBack: () -> Unit) {
    var mode by remember { mutableStateOf(ConfigHolder.appearanceMode) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    ItemDetailFramework(
        item = item,
        modifier = modifier,
        onBack = onBack,
        snackbarHostState = snackbarHostState,
        onSaveClicked = {
            ConfigHolder.appearanceMode = mode
            scope.launch { snackbarHostState.showSnackbar("saved!") }
        }
    ) {
        FlowRow(modifier = Modifier.padding(20.dp)) {
            com.mapxus.dropin.uicore.api.AppearanceMode.entries.forEach {
                FilterChip(
                    modifier = Modifier.padding(end = 5.dp),
                    selected = mode == it,
                    onClick = {
                        mode = it
                    },
                    label = { Text(it.toString().lowercase()) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun InitialBoundsScreenPreview() {
    AppearanceModeScreen(AppearanceMode) {}
}