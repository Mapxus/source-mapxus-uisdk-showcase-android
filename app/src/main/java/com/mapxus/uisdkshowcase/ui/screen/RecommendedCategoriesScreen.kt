package com.mapxus.uisdkshowcase.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.delete
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mapxus.uisdkshowcase.ConfigHolder
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.model.item.RecommendedCategories
import com.mapxus.uisdkshowcase.ui.component.EditText
import com.mapxus.uisdkshowcase.ui.component.ItemDetailFramework
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RecommendedCategoriesScreen(item: Item, onBack: () -> Unit) {
    val currentCategories = remember {
        mutableStateListOf(*(ConfigHolder.recommendedCategories?.toTypedArray() ?: emptyArray()))
    }
    var showDialog by remember { mutableStateOf(false) }
    val newCategoryState = rememberTextFieldState("")

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Add Category") },
            text = {
                EditText(textState = newCategoryState, modifier = Modifier.fillMaxWidth())
            },
            confirmButton = {
                TextButton(onClick = {
                    if (newCategoryState.text.isNotBlank()) {
                        currentCategories.add(newCategoryState.text.toString())
                        newCategoryState.edit { delete(0, length) }
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
            ConfigHolder.recommendedCategories = currentCategories.toList()
            scope.launch {
                snackbarHostState.showSnackbar("Recommended Categories saved")
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
            currentCategories.forEachIndexed { index, category ->
                InputChip(
                    selected = false,
                    onClick = { },
                    label = { Text(category) },
                    trailingIcon = {
                        IconButton(
                            onClick = { currentCategories.removeAt(index) },
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
private fun RecommendedCategoriesScreenPreview() {
    RecommendedCategoriesScreen(
        item = RecommendedCategories,
        onBack = {}
    )
}
