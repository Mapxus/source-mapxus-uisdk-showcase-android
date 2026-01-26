package com.mapxus.uisdkshowcase.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.mapxus.dropin.uicore.api.theme.DIColors
import com.mapxus.uisdkshowcase.ConfigHolder
import com.mapxus.uisdkshowcase.model.item.Colors
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.ui.component.ItemDetailFramework
import kotlinx.coroutines.launch

/**
 * 封装颜色状态的类
 */
class ColorState(initialColorInt: Int?) {
    var colorInt by mutableStateOf(initialColorInt)
}

@Composable
fun ColorsScreen(item: Item, modifier: Modifier = Modifier, onBack: () -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // 获取初始配置
    val initialColors = remember { ConfigHolder.colors ?: DIColors() }

    // 为每一个颜色项声明对应的 ColorState
    val accentState = remember { ColorState(initialColors.accentColor?.toInt()) }
    val brandPrimaryState = remember { ColorState(initialColors.brandPrimaryColor?.toInt()) }
    val brandSecondaryState = remember { ColorState(initialColors.brandSecondaryColor?.toInt()) }
    val primaryContentState = remember { ColorState(initialColors.primaryContentColor?.toInt()) }
    val secondaryContentState =
        remember { ColorState(initialColors.secondaryContentColor?.toInt()) }

    ItemDetailFramework(
        item = item,
        modifier = modifier,
        onBack = onBack,
        snackbarHostState = snackbarHostState,
        onSaveClicked = {
            // 保存时将各 State 中的值重新封装进 DIColors
            val updatedColors = DIColors(
                accentColor = accentState.colorInt?.toString(),
                brandPrimaryColor = brandPrimaryState.colorInt?.toString(),
                brandSecondaryColor = brandSecondaryState.colorInt?.toString(),
                primaryContentColor = primaryContentState.colorInt?.toString(),
                secondaryContentColor = secondaryContentState.colorInt?.toString(),
            )
            ConfigHolder.colors = updatedColors
            scope.launch {
                snackbarHostState.showSnackbar("Colors saved!")
            }
        }
    ) {
        ColorRow(label = "Accent", state = accentState)
        ColorRow(label = "Brand Primary", state = brandPrimaryState)
        ColorRow(label = "Brand Secondary", state = brandSecondaryState)
        ColorRow(label = "Primary Content", state = primaryContentState)
        ColorRow(label = "Secondary Content", state = secondaryContentState)
    }
}

@Composable
fun ColorRow(
    label: String,
    state: ColorState
) {
    var expanded by remember { mutableStateOf(false) }

    // 使用 state 中的当前颜色进行显示
    val currentColorInt = state.colorInt
    val color = currentColorInt?.let { Color(it) } ?: Color.Gray
    val colorText = if (currentColorInt != null) {
        "#${Integer.toHexString(color.toArgb()).uppercase()}"
    } else {
        "undefined"
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (currentColorInt != null) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(color)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Undefined color",
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colorScheme.error
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = colorText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline
            )
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.Edit,
                contentDescription = if (expanded) "Collapse" else "Edit",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { expanded = !expanded },
                tint = MaterialTheme.colorScheme.primary
            )
        }

        AnimatedVisibility(visible = expanded) {
            val controller = rememberColorPickerController()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                HsvColorPicker(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    controller = controller,
                    initialColor = color,
                    onColorChanged = { colorEnvelope ->
                        if (colorEnvelope.fromUser) {
                            // 颜色选择器的结果直接应用到 state 中
                            state.colorInt = colorEnvelope.color.toArgb()
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ColorsScreenPreview() {
    ColorsScreen(Colors) {}
}
