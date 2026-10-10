package com.mapxus.uisdkshowcase.ui.screen

import androidx.compose.foundation.layout.ExperimentalLayoutApi
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
import com.mapxus.dropin.uicore.api.RouteRenderMode as SdkRouteRenderMode
import com.mapxus.uisdkshowcase.ConfigHolder
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.model.item.RouteRenderMode
import com.mapxus.uisdkshowcase.ui.component.ItemDetailFramework
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RouteRenderModeScreen(item: Item, onBack: () -> Unit) {
    var mode by remember { mutableStateOf(ConfigHolder.routeRenderMode) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    ItemDetailFramework(
        item = item,
        onBack = onBack,
        snackbarHostState = snackbarHostState,
        onSaveClicked = {
            ConfigHolder.routeRenderMode = mode
            scope.launch {
                snackbarHostState.showSnackbar("Route Render Mode saved")
            }
        }
    ) {
        FlowRow(modifier = Modifier.padding(20.dp)) {
            SdkRouteRenderMode.entries.forEach { option ->
                FilterChip(
                    modifier = Modifier.padding(end = 5.dp),
                    selected = mode == option,
                    onClick = { mode = option },
                    label = { Text(option.name) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RouteRenderModeScreenPreview() {
    RouteRenderModeScreen(item = RouteRenderMode, onBack = {})
}
