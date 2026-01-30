package com.mapxus.uisdkshowcase.ui.screen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.mapxus.dropin.uicore.api.ToolTipsConfig
import com.mapxus.dropin.uicore.api.model.StringsWithLanguage
import com.mapxus.uisdkshowcase.ConfigHolder
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.ui.component.EditText
import com.mapxus.uisdkshowcase.ui.component.ItemDetailFramework
import com.mapxus.uisdkshowcase.ui.component.TextCheckBox
import kotlinx.coroutines.launch
import com.mapxus.uisdkshowcase.model.item.ToolTipsConfig as ToolTipsConfigItem

private class ToolTipsStringsState(initialStrings: StringsWithLanguage?) {
    val defaultTextState = TextFieldState(initialStrings?.default ?: "")
    val enTextState = TextFieldState(initialStrings?.en ?: "")
    val zhHansTextState = TextFieldState(initialStrings?.zhHans ?: "")
    val zhHantTextState = TextFieldState(initialStrings?.zhHant ?: "")
    val zhHanTWTextState = TextFieldState(initialStrings?.zhHantTW ?: "")
    val jaTextState = TextFieldState(initialStrings?.ja ?: "")
    val koTextState = TextFieldState(initialStrings?.ko ?: "")
    val arTextState = TextFieldState(initialStrings?.ar ?: "")

    var isExpanded by mutableStateOf(false)

    fun toStringsWithLanguage() = StringsWithLanguage(
        default = defaultTextState.text.toString().ifEmpty { null },
        en = enTextState.text.toString().ifEmpty { null },
        zhHans = zhHansTextState.text.toString().ifEmpty { null },
        zhHant = zhHantTextState.text.toString().ifEmpty { null },
        zhHantTW = zhHanTWTextState.text.toString().ifEmpty { null },
        ja = jaTextState.text.toString().ifEmpty { null },
        ko = koTextState.text.toString().ifEmpty { null },
        ar = arTextState.text.toString().ifEmpty { null }
    )
}

@Composable
fun ToolTipsConfigScreen(item: Item, onBack: () -> Unit) {
    var isEnabled by remember { mutableStateOf(ConfigHolder.toolTipsConfig?.isEnabled ?: false) }

    val titleState = remember { ToolTipsStringsState(ConfigHolder.toolTipsConfig?.title) }
    val contentState = remember { ToolTipsStringsState(ConfigHolder.toolTipsConfig?.content) }
    val htmlContentState =
        remember { ToolTipsStringsState(ConfigHolder.toolTipsConfig?.htmlContent) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    ItemDetailFramework(
        item = item,
        onBack = onBack,
        snackbarHostState = snackbarHostState,
        onSaveClicked = {
            ConfigHolder.toolTipsConfig = ToolTipsConfig(
                isEnabled = isEnabled,
                title = titleState.toStringsWithLanguage(),
                content = contentState.toStringsWithLanguage(),
                htmlContent = htmlContentState.toStringsWithLanguage()
            )
            scope.launch {
                snackbarHostState.showSnackbar("Tool Tips Config saved")
            }
        }
    ) {
        TextCheckBox(
            text = "Enable Tool Tips",
            checked = isEnabled,
            onCheckedChange = { isEnabled = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )

        ToolTipsStringsRow(
            label = "Title",
            state = titleState,
            enabled = isEnabled
        )

        ToolTipsStringsRow(
            label = "Content",
            state = contentState,
            enabled = isEnabled
        )

        ToolTipsStringsRow(
            label = "HTML Content",
            state = htmlContentState,
            enabled = isEnabled
        )
    }
}

@Composable
private fun ToolTipsStringsRow(
    label: String,
    state: ToolTipsStringsState,
    enabled: Boolean
) {
    val elevation by animateDpAsState(
        targetValue = if (state.isExpanded) 8.dp else 2.dp,
        label = "elevation"
    )

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = elevation),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = label)
                IconButton(
                    onClick = { state.isExpanded = !state.isExpanded },
                    enabled = enabled
                ) {
                    Icon(
                        imageVector = if (state.isExpanded) Icons.Default.Check else Icons.Default.Edit,
                        contentDescription = if (state.isExpanded) "Collapse" else "Edit"
                    )
                }
            }
            if (state.isExpanded && enabled) {
                val fillMaxWidth = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)

                EditText(state.defaultTextState, modifier = fillMaxWidth, prefixText = "default")
                EditText(state.enTextState, modifier = fillMaxWidth, prefixText = "en")
                EditText(state.zhHansTextState, modifier = fillMaxWidth, prefixText = "zhHans")
                EditText(state.zhHantTextState, modifier = fillMaxWidth, prefixText = "zhHant")
                EditText(state.zhHanTWTextState, modifier = fillMaxWidth, prefixText = "zhHanTW")
                EditText(state.jaTextState, modifier = fillMaxWidth, prefixText = "ja")
                EditText(state.koTextState, modifier = fillMaxWidth, prefixText = "ko")
                EditText(state.arTextState, modifier = fillMaxWidth, prefixText = "ar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ToolTipsConfigScreenPreview() {
    ToolTipsConfigScreen(
        item = ToolTipsConfigItem,
        onBack = {}
    )
}
