package com.mapxus.uisdkshowcase.ui.screen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.delete
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.mapxus.dropin.uicore.api.SharedFloorsUnifiedName
import com.mapxus.dropin.uicore.api.model.StringsWithLanguage
import com.mapxus.uisdkshowcase.ConfigHolder
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.ui.component.EditText
import com.mapxus.uisdkshowcase.ui.component.ItemDetailFramework
import kotlinx.coroutines.launch

private class UnifiedNameState(initialName: SharedFloorsUnifiedName) {
    val venueId = initialName.venueId
    val defaultTextState = TextFieldState(initialName.unifiedName.default ?: "")
    val enTextState = TextFieldState(initialName.unifiedName.en ?: "")
    val zhHansTextState = TextFieldState(initialName.unifiedName.zhHans ?: "")
    val zhHantTextState = TextFieldState(initialName.unifiedName.zhHant ?: "")
    val zhHanTWTextState = TextFieldState(initialName.unifiedName.zhHantTW ?: "")
    val jaTextState = TextFieldState(initialName.unifiedName.ja ?: "")
    val koTextState = TextFieldState(initialName.unifiedName.ko ?: "")
    val arTextState = TextFieldState(initialName.unifiedName.ar ?: "")

    var isExpanded by mutableStateOf(false)

    fun toSharedFloorsUnifiedName() = SharedFloorsUnifiedName(
        venueId = venueId,
        unifiedName = StringsWithLanguage(
            default = defaultTextState.text.toString().ifEmpty { null },
            en = enTextState.text.toString().ifEmpty { null },
            zhHans = zhHansTextState.text.toString().ifEmpty { null },
            zhHant = zhHantTextState.text.toString().ifEmpty { null },
            zhHantTW = zhHanTWTextState.text.toString().ifEmpty { null },
            ja = jaTextState.text.toString().ifEmpty { null },
            ko = koTextState.text.toString().ifEmpty { null },
            ar = arTextState.text.toString().ifEmpty { null }
        )
    )
}

@Composable
fun SharedFloorsUnifiedNamesScreen(item: Item, onBack: () -> Unit) {
    val states = remember {
        mutableStateListOf<UnifiedNameState>().apply {
            addAll(ConfigHolder.sharedFloorsUnifiedNames?.map { UnifiedNameState(it) }
                ?: emptyList())
        }
    }

    var showDialog by remember { mutableStateOf(false) }
    val newVenueIdState = rememberTextFieldState("")

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Add Unified Name") },
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
                            UnifiedNameState(
                                SharedFloorsUnifiedName(
                                    venueId = newVenueIdState.text.toString(),
                                    unifiedName = StringsWithLanguage(default = "")
                                )
                            )
                        )
                        newVenueIdState.edit { delete(0, length) }
                        showDialog = false
                    }
                }) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
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
            ConfigHolder.sharedFloorsUnifiedNames = states.map { it.toSharedFloorsUnifiedName() }
            scope.launch {
                snackbarHostState.showSnackbar("Shared Floors Unified Names saved")
            }
        }
    ) {
        states.forEach { state ->
            UnifiedNameRow(
                unifiedNameState = state,
                onDelete = {
                    states.remove(state)
                }
            )
        }
        TextButton(
            onClick = { showDialog = true },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Add Unified Name")
        }
    }
}

@Composable
private fun UnifiedNameRow(
    unifiedNameState: UnifiedNameState,
    onDelete: () -> Unit
) {
    val elevation by animateDpAsState(
        targetValue = if (unifiedNameState.isExpanded) 8.dp else 2.dp,
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
                Text(text = unifiedNameState.venueId)
                Row {
                    IconButton(onClick = {
                        unifiedNameState.isExpanded = !unifiedNameState.isExpanded
                    }) {
                        Icon(
                            imageVector = if (unifiedNameState.isExpanded) Icons.Default.Check else Icons.Default.Edit,
                            contentDescription = if (unifiedNameState.isExpanded) "Collapse" else "Edit"
                        )
                    }
                    IconButton(onClick = onDelete) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            }
            if (unifiedNameState.isExpanded) {
                val fillMaxWidth = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)

                EditText(
                    unifiedNameState.defaultTextState,
                    modifier = fillMaxWidth,
                    prefixText = "default"
                )

                EditText(unifiedNameState.enTextState, modifier = fillMaxWidth, prefixText = "en")

                EditText(
                    unifiedNameState.zhHansTextState,
                    modifier = fillMaxWidth,
                    prefixText = "zhHans"
                )

                EditText(
                    unifiedNameState.zhHantTextState,
                    modifier = fillMaxWidth,
                    prefixText = "zhHant"
                )

                EditText(
                    unifiedNameState.zhHanTWTextState,
                    modifier = fillMaxWidth,
                    prefixText = "zhHanTW"
                )

                EditText(unifiedNameState.jaTextState, modifier = fillMaxWidth, prefixText = "ja")

                EditText(unifiedNameState.koTextState, modifier = fillMaxWidth, prefixText = "ko")

                EditText(unifiedNameState.arTextState, modifier = fillMaxWidth, prefixText = "ar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UnifiedNameRowPreview() {
    val dummyName = SharedFloorsUnifiedName(
        venueId = "venue_123",
        unifiedName = StringsWithLanguage(default = "Default Name")
    )
    val state = remember { UnifiedNameState(dummyName).apply { isExpanded = true } }
    UnifiedNameRow(unifiedNameState = state, onDelete = {})
}
