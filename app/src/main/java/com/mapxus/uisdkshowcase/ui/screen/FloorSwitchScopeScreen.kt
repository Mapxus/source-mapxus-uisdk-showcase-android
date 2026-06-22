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
import com.mapxus.dropin.uicore.api.FloorSwitchScope as FloorSwitchScopeType
import com.mapxus.uisdkshowcase.ConfigHolder
import com.mapxus.uisdkshowcase.model.item.FloorSwitchScope
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.ui.component.ItemDetailFramework
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FloorSwitchScopeScreen(item: Item, modifier: Modifier = Modifier, onBack: () -> Unit) {
    var selectedScope by remember { mutableStateOf(ConfigHolder.floorSwitchScope) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    ItemDetailFramework(
        item = item,
        modifier = modifier,
        onBack = onBack,
        snackbarHostState = snackbarHostState,
        onSaveClicked = {
            ConfigHolder.floorSwitchScope = selectedScope
            scope.launch {
                snackbarHostState.showSnackbar("Floor switch scope saved")
            }
        }
    ) {
        FlowRow(modifier = Modifier.padding(20.dp)) {
            FloorSwitchScopeType.entries.forEach {
                FilterChip(
                    modifier = Modifier.padding(end = 5.dp),
                    selected = selectedScope == it,
                    onClick = {
                        selectedScope = it
                    },
                    label = { Text(it.name) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FloorSwitchScopeScreenPreview() {
    FloorSwitchScopeScreen(item = FloorSwitchScope, onBack = {})
}

