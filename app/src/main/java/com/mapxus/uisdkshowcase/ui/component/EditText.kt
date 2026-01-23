package com.mapxus.uisdkshowcase.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EditText(
    textState: TextFieldState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    prefixText: String? = null,
    suffixText: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(
        textState,
        modifier = modifier,
        enabled = enabled,
        keyboardOptions = keyboardOptions,
        suffix = {
            suffixText?.let { Text(it) }
        },
        prefix = {
            prefixText?.let { Text(it) }
        }
    )
}