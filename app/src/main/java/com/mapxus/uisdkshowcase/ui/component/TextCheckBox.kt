package com.mapxus.uisdkshowcase.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TextCheckBox(
    text: String,
    checked: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked, onCheckedChange)
        Text(text)
    }
}

@Preview(showBackground = true)
@Composable
private fun TextCheckBoxPreview() {
    TextCheckBox(
        text = "Text",
        checked = true,
        onCheckedChange = {},
        modifier = Modifier.fillMaxWidth()
    )
}