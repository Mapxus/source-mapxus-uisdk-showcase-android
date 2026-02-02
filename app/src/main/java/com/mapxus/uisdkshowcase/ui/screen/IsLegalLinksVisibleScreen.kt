package com.mapxus.uisdkshowcase.ui.screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
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
import com.mapxus.uisdkshowcase.ConfigHolder
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.ui.component.ItemDetailFramework
import com.mapxus.uisdkshowcase.ui.theme.UISDKShowcaseTheme
import kotlinx.coroutines.launch

@Composable
fun IsLegalLinksVisibleScreen(item: Item, onBack: () -> Unit) {
    var isVisible by remember { mutableStateOf(ConfigHolder.isLegalLinksVisible) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    ItemDetailFramework(
        item = item,
        onBack = onBack,
        snackbarHostState = snackbarHostState,
        onSaveClicked = {
            ConfigHolder.isLegalLinksVisible = isVisible
            scope.launch {
                snackbarHostState.showSnackbar("Is Legal Links Visible saved")
            }
        }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isVisible,
                onCheckedChange = { isVisible = it }
            )
            Text(text = "Is Legal Links Visible")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IsLegalLinksVisibleScreenPreview() {
    UISDKShowcaseTheme {
        IsLegalLinksVisibleScreen(
            item = com.mapxus.uisdkshowcase.model.item.IsLegalLinksVisible,
            onBack = {}
        )
    }
}
