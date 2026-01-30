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
import com.mapxus.uisdkshowcase.model.item.EventOverviewTitle
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.ui.component.EditText
import com.mapxus.uisdkshowcase.ui.component.ItemDetailFramework
import com.mapxus.uisdkshowcase.ui.component.TextCheckBox
import kotlinx.coroutines.launch

@Composable
fun EventOverviewTitleScreen(item: Item, onBack: () -> Unit) {
    var enable by remember { mutableStateOf(ConfigHolder.eventOverviewTitle != null) }

    val defaultTextState =
        rememberTextFieldState(ConfigHolder.eventOverviewTitle?.default ?: "")
    val enTextState = rememberTextFieldState(ConfigHolder.eventOverviewTitle?.en ?: "")
    val zhHansTextState =
        rememberTextFieldState(ConfigHolder.eventOverviewTitle?.zhHans ?: "")
    val zhHantTextState =
        rememberTextFieldState(ConfigHolder.eventOverviewTitle?.zhHant ?: "")
    val zhHanTWTextState =
        rememberTextFieldState(ConfigHolder.eventOverviewTitle?.zhHantTW ?: "")
    val jaTextState = rememberTextFieldState(ConfigHolder.eventOverviewTitle?.ja ?: "")
    val koTextState = rememberTextFieldState(ConfigHolder.eventOverviewTitle?.ko ?: "")
    val arTextState = rememberTextFieldState(ConfigHolder.eventOverviewTitle?.ar ?: "")

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    ItemDetailFramework(
        item = item,
        onBack = onBack,
        snackbarHostState = snackbarHostState,
        onSaveClicked = {
            ConfigHolder.eventOverviewTitle = if (enable) {
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
                snackbarHostState.showSnackbar("Event Overview Title saved")
            }
        }
    ) {
        val fillMaxWidth = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)

        TextCheckBox(
            text = "Modify Event Overview Title",
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
private fun EventOverviewTitleScreenPreview() {
    EventOverviewTitleScreen(
        item = EventOverviewTitle,
        onBack = {}
    )
}
