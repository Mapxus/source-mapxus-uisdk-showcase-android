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
import androidx.compose.material3.MaterialTheme
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
import com.mapxus.dropin.uicore.api.model.FacilityInfo
import com.mapxus.dropin.uicore.api.model.VenueLevelFacilityInfo
import com.mapxus.dropin.uicore.api.model.VenueLevelFacilityInfoConfig
import com.mapxus.uisdkshowcase.ConfigHolder
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.model.item.VenueLevelFacilityInfoConfigs
import com.mapxus.uisdkshowcase.ui.component.EditText
import com.mapxus.uisdkshowcase.ui.component.ItemDetailFramework
import kotlinx.coroutines.launch

private class FacilityInfoState(id: String, facilities: List<String>) {
    var id by mutableStateOf(id)
    val facilities = mutableStateListOf(*facilities.toTypedArray())

    fun toFacilityInfo() = FacilityInfo(id = id, facilities = facilities.toList())
}

private class VenueLevelFacilityInfoConfigState(config: VenueLevelFacilityInfoConfig) {
    val venueId = config.venueId
    val buildings = mutableStateListOf<FacilityInfoState>().apply {
        addAll(config.venueLevelFacilityInfo.buildings?.map {
            FacilityInfoState(
                it.id,
                it.facilities
            )
        } ?: emptyList())
    }
    val floors = mutableStateListOf<FacilityInfoState>().apply {
        addAll(config.venueLevelFacilityInfo.floors?.map { FacilityInfoState(it.id, it.facilities) }
            ?: emptyList())
    }
    val sharedFloors = mutableStateListOf<FacilityInfoState>().apply {
        addAll(config.venueLevelFacilityInfo.sharedFloors?.map {
            FacilityInfoState(
                it.id,
                it.facilities
            )
        } ?: emptyList())
    }
    var isExpanded by mutableStateOf(false)

    fun toConfig() = VenueLevelFacilityInfoConfig(
        venueId = venueId,
        venueLevelFacilityInfo = VenueLevelFacilityInfo(
            buildings = buildings.map { it.toFacilityInfo() },
            floors = floors.map { it.toFacilityInfo() },
            sharedFloors = sharedFloors.map { it.toFacilityInfo() }
        )
    )
}

@Composable
fun VenueLevelFacilityInfoConfigScreen(item: Item, onBack: () -> Unit) {
    val states = remember {
        mutableStateListOf<VenueLevelFacilityInfoConfigState>().apply {
            addAll(
                ConfigHolder.venueLevelFacilityInfoConfig?.map {
                    VenueLevelFacilityInfoConfigState(it)
                } ?: emptyList()
            )
        }
    }

    var showAddVenueDialog by remember { mutableStateOf(false) }
    val newVenueIdState = rememberTextFieldState("")

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    if (showAddVenueDialog) {
        AlertDialog(
            onDismissRequest = { showAddVenueDialog = false },
            title = { Text("Add Venue Level Facility Info Config") },
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
                            VenueLevelFacilityInfoConfigState(
                                VenueLevelFacilityInfoConfig(
                                    venueId = newVenueIdState.text.toString(),
                                    venueLevelFacilityInfo = VenueLevelFacilityInfo()
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
            ConfigHolder.venueLevelFacilityInfoConfig = states.map { it.toConfig() }
            scope.launch {
                snackbarHostState.showSnackbar("Venue Level Facility Info Config saved")
            }
        }
    ) {
        states.forEach { state ->
            VenueConfigCard(
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
private fun VenueConfigCard(
    state: VenueLevelFacilityInfoConfigState,
    onDelete: () -> Unit
) {
    val elevation by animateDpAsState(
        targetValue = if (state.isExpanded) 8.dp else 2.dp,
        label = "elevation"
    )

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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                ) {
                    FacilityInfoSection(
                        title = "Buildings",
                        facilityInfoStates = state.buildings
                    )
                    FacilityInfoSection(
                        title = "Floors",
                        facilityInfoStates = state.floors
                    )
                    FacilityInfoSection(
                        title = "Shared Floors",
                        facilityInfoStates = state.sharedFloors
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun FacilityInfoSection(
    title: String,
    facilityInfoStates: MutableList<FacilityInfoState>
) {
    var showAddFacilityDialog by remember { mutableStateOf(false) }
    val newFacilityIdState = rememberTextFieldState("")

    if (showAddFacilityDialog) {
        AlertDialog(
            onDismissRequest = { showAddFacilityDialog = false },
            title = { Text("Add $title Entry") },
            text = {
                EditText(
                    textState = newFacilityIdState,
                    modifier = Modifier.fillMaxWidth(),
                    prefixText = "ID"
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (newFacilityIdState.text.isNotBlank()) {
                        facilityInfoStates.add(
                            FacilityInfoState(
                                id = newFacilityIdState.text.toString(),
                                facilities = emptyList()
                            )
                        )
                        newFacilityIdState.edit { delete(0, length) }
                        showAddFacilityDialog = false
                    }
                }) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddFacilityDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
    )

    facilityInfoStates.forEachIndexed { index, facilityState ->
        FacilityInfoRow(
            state = facilityState,
            onDelete = { facilityInfoStates.removeAt(index) }
        )
    }

    TextButton(onClick = { showAddFacilityDialog = true }) {
        Icon(Icons.Default.Add, contentDescription = "Add", modifier = Modifier.size(16.dp))
        Text(text = " Add $title Entry", style = MaterialTheme.typography.bodySmall)
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun FacilityInfoRow(
    state: FacilityInfoState,
    onDelete: () -> Unit
) {
    var showAddFacilityItemDialog by remember { mutableStateOf(false) }
    val newFacilityItemState = rememberTextFieldState("")

    if (showAddFacilityItemDialog) {
        AlertDialog(
            onDismissRequest = { showAddFacilityItemDialog = false },
            title = { Text("Add Facility") },
            text = {
                EditText(
                    textState = newFacilityItemState,
                    modifier = Modifier.fillMaxWidth(),
                    prefixText = "Facility"
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (newFacilityItemState.text.isNotBlank()) {
                        state.facilities.add(newFacilityItemState.text.toString())
                        newFacilityItemState.edit { delete(0, length) }
                        showAddFacilityItemDialog = false
                    }
                }) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddFacilityItemDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "ID: ${state.id}",
                    style = MaterialTheme.typography.bodyMedium
                )
                IconButton(onClick = onDelete, modifier = Modifier.size(24.dp)) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                state.facilities.forEachIndexed { index, facility ->
                    InputChip(
                        selected = false,
                        onClick = { },
                        label = { Text(facility) },
                        trailingIcon = {
                            IconButton(
                                onClick = { state.facilities.removeAt(index) },
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
                    onClick = { showAddFacilityItemDialog = true },
                    label = { Icon(Icons.Default.Add, contentDescription = "Add") }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VenueLevelFacilityInfoConfigScreenPreview() {
    VenueLevelFacilityInfoConfigScreen(
        item = VenueLevelFacilityInfoConfigs,
        onBack = {}
    )
}


