package com.mapxus.uisdkshowcase.ui.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mapxus.dropin.uicore.api.theme.BorderStyle
import com.mapxus.uisdkshowcase.ConfigHolder
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.model.item.SelectedBuildingBorderStyle
import com.mapxus.uisdkshowcase.ui.component.EditText
import com.mapxus.uisdkshowcase.ui.component.ItemDetailFramework
import kotlinx.coroutines.launch

@Composable
fun SelectedBuildingBorderStyleScreen(
    item: Item,
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    val lineOpacityTextState = rememberTextFieldState(
        ConfigHolder.selectedBuildingBorderStyle?.lineOpacity?.toString() ?: ""
    )

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    ItemDetailFramework(
        item = item,
        modifier = modifier,
        onBack = onBack,
        snackbarHostState = snackbarHostState,
        onSaveClicked = {
            try {
                val text = lineOpacityTextState.text.toString()
                val lineOpacity = text.ifBlank { null }?.toDouble()
                ConfigHolder.selectedBuildingBorderStyle = BorderStyle(lineOpacity)
                scope.launch {
                    snackbarHostState.showSnackbar("saved!")
                }
            } catch (e: Exception) {
                scope.launch {
                    snackbarHostState.showSnackbar("Invalid line opacity: ${e.message}")
                }
            }
        }
    ) {
        EditText(
            lineOpacityTextState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp, start = 10.dp, end = 10.dp),
            prefixText = "Line Opacity (empty for null)",
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal)
        )
    }
}

@Preview
@Composable
private fun SelectedBuildingBorderStyleScreenPreview() {
    SelectedBuildingBorderStyleScreen(SelectedBuildingBorderStyle) {}
}

