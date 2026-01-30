package com.mapxus.uisdkshowcase.ui.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mapxus.dropin.uicore.api.model.StringsWithLanguage
import com.mapxus.uisdkshowcase.ConfigHolder
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.model.item.NoRouteAvailableTitle
import com.mapxus.uisdkshowcase.ui.component.EditText
import com.mapxus.uisdkshowcase.ui.component.ItemDetailFramework
import com.mapxus.uisdkshowcase.ui.component.TextCheckBox
import kotlinx.coroutines.launch

@Composable
fun NoRouteAvailableTitleScreen(item: Item, onBack: () -> Unit) {
    var enable by remember { mutableStateOf(ConfigHolder.noRouteAvailableTitle != null) }

    val defaultTextState =
        rememberTextFieldState(ConfigHolder.noRouteAvailableTitle?.default ?: "")
    val enTextState = rememberTextFieldState(ConfigHolder.noRouteAvailableTitle?.en ?: "")
    val zhHansTextState =
        rememberTextFieldState(ConfigHolder.noRouteAvailableTitle?.zhHans ?: "")
    val zhHantTextState =
        rememberTextFieldState(ConfigHolder.noRouteAvailableTitle?.zhHant ?: "")
    val zhHanTWTextState =
        rememberTextFieldState(ConfigHolder.noRouteAvailableTitle?.zhHantTW ?: "")
    val jaTextState = rememberTextFieldState(ConfigHolder.noRouteAvailableTitle?.ja ?: "")
    val koTextState = rememberTextFieldState(ConfigHolder.noRouteAvailableTitle?.ko ?: "")
    val arTextState = rememberTextFieldState(ConfigHolder.noRouteAvailableTitle?.ar ?: "")

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    ItemDetailFramework(
        item = item,
        onBack = onBack,
        snackbarHostState = snackbarHostState,
        onSaveClicked = {
            ConfigHolder.noRouteAvailableTitle = if (enable) {
                StringsWithLanguage(
                    default = defaultTextState.text.toString().ifEmpty { null },
                    en = enTextState.text.toString().ifEmpty { null },
                    zhHans = zhHansTextState.text.toString().ifEmpty { null },
                    zhHant = zhHantTextState.text.toString().ifEmpty { null },
                    zhHantTW = zhHanTWTextState.text.toString().ifEmpty { null },
                    ja = jaTextState.text.toString().ifEmpty { null },
                    ko = koTextState.text.toString().ifEmpty { null },
                    ar = arTextState.text.toString().ifEmpty { null },
                )
            } else null
            scope.launch {
                snackbarHostState.showSnackbar("No Route Available Title saved")
            }
        }
    ) {
        val fillMaxWidth = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)

        TextCheckBox(
            text = "Modify No Route Available Title",
            modifier = fillMaxWidth,
            checked = enable,
            onCheckedChange = { enable = it })

        EditText(
            defaultTextState,
            modifier = fillMaxWidth,
            prefixText = "default",
            enabled = enable,
        )

        EditText(
            enTextState,
            modifier = fillMaxWidth,
            prefixText = "en",
            enabled = enable,
        )

        EditText(
            zhHansTextState,
            modifier = fillMaxWidth,
            prefixText = "zhHans",
            enabled = enable,
        )

        EditText(
            zhHantTextState,
            modifier = fillMaxWidth,
            prefixText = "zhHant",
            enabled = enable,
        )

        EditText(
            zhHanTWTextState,
            modifier = fillMaxWidth,
            prefixText = "zhHanTW",
            enabled = enable,
        )

        EditText(
            jaTextState,
            modifier = fillMaxWidth,
            prefixText = "ja",
            enabled = enable,
        )

        EditText(
            koTextState,
            modifier = fillMaxWidth,
            prefixText = "ko",
            enabled = enable,
        )

        EditText(
            arTextState,
            modifier = fillMaxWidth,
            prefixText = "ar",
            enabled = enable,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NoRouteAvailableTitleScreenPreview() {
    NoRouteAvailableTitleScreen(
        item = NoRouteAvailableTitle,
        onBack = {}
    )
}
