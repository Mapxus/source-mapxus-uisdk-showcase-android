package com.mapxus.uisdkshowcase.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
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
import com.mapxus.uisdkshowcase.ConfigHolder
import com.mapxus.uisdkshowcase.model.item.EnableBearingCalibration
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.ui.component.ItemDetailFramework
import kotlinx.coroutines.launch

@Composable
fun EnableBearingCalibrationScreen(item: Item, onBack: () -> Unit) {
    var isEnabled by remember { mutableStateOf(ConfigHolder.enableBearingCalibration) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    ItemDetailFramework(
        item = item,
        onBack = onBack,
        snackbarHostState = snackbarHostState,
        onSaveClicked = {
            ConfigHolder.enableBearingCalibration = isEnabled
            scope.launch {
                snackbarHostState.showSnackbar("Enable Bearing Calibration saved")
            }
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Enabled")
            Switch(
                checked = isEnabled,
                onCheckedChange = { isEnabled = it }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EnableBearingCalibrationScreenPreview() {
    EnableBearingCalibrationScreen(item = EnableBearingCalibration, onBack = {})
}

