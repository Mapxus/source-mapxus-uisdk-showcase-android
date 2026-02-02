package com.mapxus.uisdkshowcase.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mapxus.dropin.uicore.api.CopyrightConfig
import com.mapxus.uisdkshowcase.ConfigHolder
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.ui.component.EditText
import com.mapxus.uisdkshowcase.ui.component.ItemDetailFramework
import com.mapxus.uisdkshowcase.ui.theme.UISDKShowcaseTheme
import kotlinx.coroutines.launch

@Composable
fun CopyrightConfigScreen(item: Item, onBack: () -> Unit) {
    val currentConfig = ConfigHolder.copyrightConfig
    var isEnabled by remember { mutableStateOf(currentConfig != null) }
    var alpha by remember { mutableFloatStateOf(currentConfig?.alpha ?: 1f) }
    val imageUrlState = rememberTextFieldState(currentConfig?.imageUrl ?: "")
    val imageWidthState = rememberTextFieldState(currentConfig?.imageWidth?.toString() ?: "")
    val imageHeightState = rememberTextFieldState(currentConfig?.imageHeight?.toString() ?: "")

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    ItemDetailFramework(
        item = item,
        onBack = onBack,
        snackbarHostState = snackbarHostState,
        onSaveClicked = {
            ConfigHolder.copyrightConfig = if (isEnabled) {
                CopyrightConfig(
                    alpha = alpha,
                    imageUrl = imageUrlState.text.toString().ifBlank { null },
                    imageWidth = imageWidthState.text.toString().toIntOrNull() ?: 0,
                    imageHeight = imageHeightState.text.toString().toIntOrNull() ?: 0
                )
            } else {
                null
            }
            scope.launch {
                snackbarHostState.showSnackbar("Copyright Config saved")
            }
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = isEnabled, onCheckedChange = { isEnabled = it })
                Text(text = "Customize Copyright Config")
            }

            Text(
                text = "Alpha: ${"%.2f".format(alpha)}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp),
                color = if (isEnabled) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.38f
                )
            )
            Slider(
                value = alpha,
                onValueChange = { alpha = it },
                valueRange = 0f..1f,
                modifier = Modifier.fillMaxWidth(),
                enabled = isEnabled
            )

            Text(
                text = "Image URL",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                color = if (isEnabled) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.38f
                )
            )
            EditText(
                textState = imageUrlState,
                modifier = Modifier.fillMaxWidth(),
                prefixText = "URL",
                enabled = isEnabled
            )

            Text(
                text = "Image Width",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                color = if (isEnabled) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.38f
                )
            )
            EditText(
                textState = imageWidthState,
                modifier = Modifier.fillMaxWidth(),
                prefixText = "Width",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                enabled = isEnabled
            )

            Text(
                text = "Image Height",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                color = if (isEnabled) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.38f
                )
            )
            EditText(
                textState = imageHeightState,
                modifier = Modifier.fillMaxWidth(),
                prefixText = "Height",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                enabled = isEnabled
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CopyrightConfigScreenPreview() {
    UISDKShowcaseTheme {
        CopyrightConfigScreen(
            item = com.mapxus.uisdkshowcase.model.item.CopyrightConfig,
            onBack = {}
        )
    }
}
