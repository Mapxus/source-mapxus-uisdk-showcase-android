package com.mapxus.uisdkshowcase.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mapxus.dropin.uicore.api.AttributionConfig
import com.mapxus.uisdkshowcase.ConfigHolder
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.ui.component.EditText
import com.mapxus.uisdkshowcase.ui.component.ItemDetailFramework
import com.mapxus.uisdkshowcase.ui.theme.UISDKShowcaseTheme
import kotlinx.coroutines.launch

@Composable
fun AttributionConfigScreen(item: Item, onBack: () -> Unit) {
    val currentConfig = ConfigHolder.attributionConfig
    var isEnabled by remember { mutableStateOf(currentConfig != null) }
    val attributionTextState = rememberTextFieldState(currentConfig?.text ?: "")
    val attributionUrlState = rememberTextFieldState(currentConfig?.url ?: "")

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    ItemDetailFramework(
        item = item,
        onBack = onBack,
        snackbarHostState = snackbarHostState,
        onSaveClicked = {
            ConfigHolder.attributionConfig = if (isEnabled) {
                AttributionConfig(
                    text = attributionTextState.text.toString().ifBlank { null },
                    url = attributionUrlState.text.toString().ifBlank { null }
                )
            } else {
                null
            }
            scope.launch {
                snackbarHostState.showSnackbar("Attribution Config saved")
            }
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = isEnabled, onCheckedChange = { isEnabled = it })
                Text(text = "Customize Attribution Config")
            }

            Text(
                text = "Attribution Text",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                color = if (isEnabled) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.38f
                )
            )
            EditText(
                textState = attributionTextState,
                modifier = Modifier.fillMaxWidth(),
                prefixText = "Text",
                enabled = isEnabled
            )

            Text(
                text = "Attribution URL",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                color = if (isEnabled) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.38f
                )
            )
            EditText(
                textState = attributionUrlState,
                modifier = Modifier.fillMaxWidth(),
                prefixText = "URL",
                enabled = isEnabled
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AttributionConfigScreenPreview() {
    UISDKShowcaseTheme {
        AttributionConfigScreen(
            item = com.mapxus.uisdkshowcase.model.item.AttributionConfig,
            onBack = {}
        )
    }
}
