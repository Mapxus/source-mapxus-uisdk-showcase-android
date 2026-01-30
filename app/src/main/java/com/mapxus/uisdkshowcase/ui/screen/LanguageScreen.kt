package com.mapxus.uisdkshowcase.ui.screen

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mapxus.dropin.uicore.api.Language
import com.mapxus.uisdkshowcase.ConfigHolder
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.ui.component.ItemDetailFramework
import kotlinx.coroutines.launch
import com.mapxus.uisdkshowcase.model.item.Language as LanguageItem

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LanguageScreen(item: Item, modifier: Modifier = Modifier, onBack: () -> Unit) {
    var selectedLanguage by remember { mutableStateOf(ConfigHolder.language) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    ItemDetailFramework(
        item = item,
        modifier = modifier,
        onBack = onBack,
        snackbarHostState = snackbarHostState,
        onSaveClicked = {
            ConfigHolder.language = selectedLanguage
            scope.launch {
                snackbarHostState.showSnackbar("Language saved")
            }
        }
    ) {
        FlowRow(modifier = Modifier.padding(20.dp)) {
            Language.entries.forEach {
                FilterChip(
                    modifier = Modifier.padding(end = 5.dp),
                    selected = selectedLanguage == it,
                    onClick = {
                        selectedLanguage = it
                    },
                    label = { Text(it.name) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LanguageScreenPreview() {
    LanguageScreen(item = LanguageItem, onBack = {})
}
