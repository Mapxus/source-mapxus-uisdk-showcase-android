package com.mapxus.uisdkshowcase.ui.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mapxus.dropin.uicore.api.model.Bounds
import com.mapxus.uisdkshowcase.ConfigHolder
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.model.item.MapBoundsRestriction
import com.mapxus.uisdkshowcase.ui.component.EditText
import com.mapxus.uisdkshowcase.ui.component.ItemDetailFramework
import com.mapxus.uisdkshowcase.ui.component.TextCheckBox
import kotlinx.coroutines.launch

@Composable
fun MapBoundsRestrictionScreen(item: Item, modifier: Modifier = Modifier, onBack: () -> Unit) {
    var enable by remember { mutableStateOf(ConfigHolder.mapBoundsRestriction != null) }

    val minLatTextState = rememberTextFieldState()
    val maxLatTextState = rememberTextFieldState()
    val minLonTextState = rememberTextFieldState()
    val maxLonTextState = rememberTextFieldState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    ItemDetailFramework(
        item = item,
        modifier = modifier,
        onBack = onBack,
        snackbarHostState = snackbarHostState,
        onSaveClicked = {
            try {
                ConfigHolder.mapBoundsRestriction = if (enable) {
                    Bounds(
                        minLat = minLatTextState.text.toString().toDoubleOrNull() ?: 0.0,
                        maxLat = maxLatTextState.text.toString().toDoubleOrNull() ?: 0.0,
                        minLon = minLonTextState.text.toString().toDoubleOrNull() ?: 0.0,
                        maxLon = maxLonTextState.text.toString().toDoubleOrNull() ?: 0.0
                    )
                } else null
                scope.launch {
                    snackbarHostState.showSnackbar("saved!")
                }
            } catch (e: Exception) {
                scope.launch {
                    snackbarHostState.showSnackbar("Invalid bounds: ${e.message}")
                }
            }
        }
    ) {
        val fillMaxWidth = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)

        TextCheckBox(
            text = "Enable map bounds restriction",
            modifier = fillMaxWidth,
            checked = enable,
            onCheckedChange = { enable = it })

        EditText(
            minLatTextState,
            modifier = fillMaxWidth,
            prefixText = "minLat",
            enabled = enable,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal)
        )

        EditText(
            maxLatTextState,
            modifier = fillMaxWidth,
            prefixText = "maxLat",
            enabled = enable,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal)
        )

        EditText(
            minLonTextState,
            modifier = fillMaxWidth,
            prefixText = "minLon",
            enabled = enable,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal)
        )

        EditText(
            maxLonTextState,
            modifier = fillMaxWidth,
            prefixText = "maxLon",
            enabled
            = enable,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal)
        )
    }
}

@Preview
@Composable
private fun MapBoundsRestrictionScreenPreview() {
    MapBoundsRestrictionScreen(MapBoundsRestriction) {}
}