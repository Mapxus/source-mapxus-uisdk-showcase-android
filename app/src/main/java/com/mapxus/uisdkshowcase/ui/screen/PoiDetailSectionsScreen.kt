package com.mapxus.uisdkshowcase.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.RadioButton
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
import com.mapxus.dropin.uicore.api.PoiDetailSection
import com.mapxus.uisdkshowcase.ConfigHolder
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.model.item.PoiDetailSections
import com.mapxus.uisdkshowcase.ui.component.ItemDetailFramework
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PoiDetailSectionsScreen(item: Item, onBack: () -> Unit) {
    val currentPoiDetailSections =
        remember { mutableStateListOf(*ConfigHolder.poiDetailSections.toTypedArray()) }
    var showDialog by remember { mutableStateOf(false) }
    var selectedSectionInDialog by remember { mutableStateOf<PoiDetailSection?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Add POI Detail Section") },
            text = {
                val allSections = PoiDetailSection.entries
                LazyColumn {
                    items(allSections) { section ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedSectionInDialog = section }
                                .padding(vertical = 4.dp)
                        ) {
                            RadioButton(
                                selected = selectedSectionInDialog == section,
                                onClick = { selectedSectionInDialog = section }
                            )
                            Text(text = section.name, modifier = Modifier.padding(start = 8.dp))
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    selectedSectionInDialog?.let {
                        if (!currentPoiDetailSections.contains(it)) {
                            currentPoiDetailSections.add(it)
                        }
                        showDialog = false
                        selectedSectionInDialog = null
                    }
                }) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                    selectedSectionInDialog = null
                }) {
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
            ConfigHolder.poiDetailSections = currentPoiDetailSections.toList()
            scope.launch {
                snackbarHostState.showSnackbar("POI Detail Sections saved")
            }
        }
    ) {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            currentPoiDetailSections.forEachIndexed { index, section ->
                InputChip(
                    selected = false,
                    onClick = { },
                    label = { Text(section.name) },
                    trailingIcon = {
                        IconButton(
                            onClick = { currentPoiDetailSections.removeAt(index) },
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
                onClick = { showDialog = true },
                label = { Icon(Icons.Default.Add, contentDescription = "Add") }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PoiDetailSectionsScreenPreview() {
    PoiDetailSectionsScreen(
        item = PoiDetailSections,
        onBack = {}
    )
}
