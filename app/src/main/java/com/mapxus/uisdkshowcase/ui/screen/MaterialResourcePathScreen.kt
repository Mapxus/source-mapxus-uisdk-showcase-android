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
import com.mapxus.uisdkshowcase.ConfigHolder
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.model.item.MaterialResourcePath
import com.mapxus.uisdkshowcase.ui.component.EditText
import com.mapxus.uisdkshowcase.ui.component.ItemDetailFramework
import kotlinx.coroutines.launch

@Composable
fun MaterialResourcePathScreen(item: Item, modifier: Modifier = Modifier, onBack: () -> Unit) {
    val pathTextState = rememberTextFieldState(ConfigHolder.materialResourcePath)

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    ItemDetailFramework(
        item = item,
        modifier = modifier,
        onBack = onBack,
        snackbarHostState = snackbarHostState,
        onSaveClicked = {
            ConfigHolder.materialResourcePath = pathTextState.text.toString()
            scope.launch {
                snackbarHostState.showSnackbar("saved!")
            }
        }
    ) {
        EditText(
            pathTextState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp, start = 10.dp, end = 10.dp),
            prefixText = "Resource Path"
        )
    }
}

@Preview
@Composable
private fun MaterialResourcePathScreenPreview() {
    MaterialResourcePathScreen(MaterialResourcePath) {}
}
