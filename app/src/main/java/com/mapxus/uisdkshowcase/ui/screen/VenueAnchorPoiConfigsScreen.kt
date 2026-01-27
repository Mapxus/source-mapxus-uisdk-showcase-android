package com.mapxus.uisdkshowcase.ui.screen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.delete
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mapxus.dropin.uicore.api.VenueAnchorPoiConfig
import com.mapxus.uisdkshowcase.ConfigHolder
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.model.item.VenueAnchorPoiConfigs
import com.mapxus.uisdkshowcase.ui.component.EditText
import com.mapxus.uisdkshowcase.ui.component.ItemDetailFramework
import kotlinx.coroutines.launch

private class VenueAnchorPoiConfigState(initialConfig: VenueAnchorPoiConfig) {
    val venueId = initialConfig.venueId
    val poiIds = mutableStateListOf(*initialConfig.poiIds.toTypedArray())
    var isExpanded by mutableStateOf(false)

    fun toVenueAnchorPoiConfig() = VenueAnchorPoiConfig(
        venueId = venueId,
        poiIds = poiIds.toList()
    )
}

@Composable
fun VenueAnchorPoiConfigsScreen(item: Item, onBack: () -> Unit) {
    val states = remember {
        mutableStateListOf<VenueAnchorPoiConfigState>().apply {
            addAll(ConfigHolder.venueAnchorPoiConfigs?.map { VenueAnchorPoiConfigState(it) }
                ?: emptyList())
        }
    }

    var showAddVenueDialog by remember { mutableStateOf(false) }
    val newVenueIdState = rememberTextFieldState("")

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    if (showAddVenueDialog) {
        AlertDialog(
            onDismissRequest = { showAddVenueDialog = false },
            title = { Text("Add Venue Anchor POI Config") },
            text = {
                EditText(
                    textState = newVenueIdState,
                    modifier = Modifier.fillMaxWidth(),
                    prefixText = "Venue ID"
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (newVenueIdState.text.isNotBlank()) {
                        states.add(
                            VenueAnchorPoiConfigState(
                                VenueAnchorPoiConfig(
                                    venueId = newVenueIdState.text.toString(),
                                    poiIds = emptyList()
                                )
                            )
                        )
                        newVenueIdState.edit { delete(0, length) }
                        showAddVenueDialog = false
                    }
                }) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddVenueDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    ItemDetailFramework(
        item = item,
        onBack = onBack,
        snackbarHostState = snackbarHostState,
        onSaveClicked = {
            ConfigHolder.venueAnchorPoiConfigs = states.map { it.toVenueAnchorPoiConfig() }
            scope.launch {
                snackbarHostState.showSnackbar("Venue Anchor POI Configs saved")
            }
        }
    ) {
        states.forEach { state ->
            VenueAnchorPoiConfigRow(
                state = state,
                onDelete = { states.remove(state) }
            )
        }
        TextButton(
            onClick = { showAddVenueDialog = true },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Add Venue Config")
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun VenueAnchorPoiConfigRow(
    state: VenueAnchorPoiConfigState,
    onDelete: () -> Unit
) {
    val elevation by animateDpAsState(
        targetValue = if (state.isExpanded) 8.dp else 2.dp,
        label = "elevation"
    )

    var showAddPoiDialog by remember { mutableStateOf(false) }
    val newPoiIdState = rememberTextFieldState("")

    if (showAddPoiDialog) {
        AlertDialog(
            onDismissRequest = { showAddPoiDialog = false },
            title = { Text("Add POI ID") },
            text = {
                EditText(
                    textState = newPoiIdState,
                    modifier = Modifier.fillMaxWidth(),
                    prefixText = "POI ID"
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (newPoiIdState.text.isNotBlank()) {
                        state.poiIds.add(newPoiIdState.text.toString())
                        newPoiIdState.edit { delete(0, length) }
                        showAddPoiDialog = false
                    }
                }) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddPoiDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = elevation)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = state.venueId)
                Row {
                    IconButton(onClick = { state.isExpanded = !state.isExpanded }) {
                        Icon(
                            imageVector = if (state.isExpanded) Icons.Default.Check else Icons.Default.Edit,
                            contentDescription = if (state.isExpanded) "Collapse" else "Edit"
                        )
                    }
                    IconButton(onClick = onDelete) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            }
            if (state.isExpanded) {
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    state.poiIds.forEachIndexed { index, poiId ->
                        InputChip(
                            selected = false,
                            onClick = { },
                            label = { Text(poiId) },
                            trailingIcon = {
                                IconButton(
                                    onClick = { state.poiIds.removeAt(index) },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        Icons.Default.Close,
                                        contentDescription = "Delete",
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        )
                    }
                    AssistChip(
                        onClick = { showAddPoiDialog = true },
                        label = { Icon(Icons.Default.Add, contentDescription = "Add") }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VenueAnchorPoiConfigsScreenPreview() {
    VenueAnchorPoiConfigsScreen(
        item = VenueAnchorPoiConfigs,
        onBack = {}
    )
}
