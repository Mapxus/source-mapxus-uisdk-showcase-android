package com.mapxus.uisdkshowcase.ui.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mapxus.dropin.uicore.api.model.StringsWithLanguage
import com.mapxus.uisdkshowcase.ConfigHolder
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.model.item.PublicHolidayDisplayName
import com.mapxus.uisdkshowcase.ui.component.EditText
import com.mapxus.uisdkshowcase.ui.component.ItemDetailFramework
import kotlinx.coroutines.launch

@Composable
fun PublicHolidayDisplayNameScreen(item: Item, onBack: () -> Unit) {
    val defaultTextState =
        rememberTextFieldState(ConfigHolder.publicHolidayDisplayName.default ?: "")
    val enTextState = rememberTextFieldState(ConfigHolder.publicHolidayDisplayName.en ?: "")
    val zhHansTextState =
        rememberTextFieldState(ConfigHolder.publicHolidayDisplayName.zhHans ?: "")
    val zhHantTextState =
        rememberTextFieldState(ConfigHolder.publicHolidayDisplayName.zhHant ?: "")
    val zhHanTWTextState =
        rememberTextFieldState(ConfigHolder.publicHolidayDisplayName.zhHantTW ?: "")
    val jaTextState = rememberTextFieldState(ConfigHolder.publicHolidayDisplayName.ja ?: "")
    val koTextState = rememberTextFieldState(ConfigHolder.publicHolidayDisplayName.ko ?: "")
    val arTextState = rememberTextFieldState(ConfigHolder.publicHolidayDisplayName.ar ?: "")

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    ItemDetailFramework(
        item = item,
        onBack = onBack,
        snackbarHostState = snackbarHostState,
        onSaveClicked = {
            ConfigHolder.publicHolidayDisplayName = StringsWithLanguage(
                default = defaultTextState.text.toString().ifEmpty { null },
                en = enTextState.text.toString().ifEmpty { null },
                zhHans = zhHansTextState.text.toString().ifEmpty { null },
                zhHant = zhHantTextState.text.toString().ifEmpty { null },
                zhHantTW = zhHanTWTextState.text.toString().ifEmpty { null },
                ja = jaTextState.text.toString().ifEmpty { null },
                ko = koTextState.text.toString().ifEmpty { null },
                ar = arTextState.text.toString().ifEmpty { null },
            )
            scope.launch {
                snackbarHostState.showSnackbar("Public Holiday Display Name saved")
            }
        }
    ) {
        val fillMaxWidth = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)

        EditText(
            defaultTextState,
            modifier = fillMaxWidth,
            prefixText = "default",
        )

        EditText(
            enTextState,
            modifier = fillMaxWidth,
            prefixText = "en",
        )

        EditText(
            zhHansTextState,
            modifier = fillMaxWidth,
            prefixText = "zhHans",
        )

        EditText(
            zhHantTextState,
            modifier = fillMaxWidth,
            prefixText = "zhHant",
        )

        EditText(
            zhHanTWTextState,
            modifier = fillMaxWidth,
            prefixText = "zhHanTW",
        )

        EditText(
            jaTextState,
            modifier = fillMaxWidth,
            prefixText = "ja",
        )

        EditText(
            koTextState,
            modifier = fillMaxWidth,
            prefixText = "ko",
        )

        EditText(
            arTextState,
            modifier = fillMaxWidth,
            prefixText = "ar",
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PublicHolidayDisplayNameScreenPreview() {
    PublicHolidayDisplayNameScreen(
        item = PublicHolidayDisplayName,
        onBack = {}
    )
}
