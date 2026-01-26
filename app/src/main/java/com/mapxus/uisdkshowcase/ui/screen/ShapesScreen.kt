package com.mapxus.uisdkshowcase.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
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
import com.mapxus.uisdkshowcase.model.item.Shapes
import com.mapxus.uisdkshowcase.ui.component.ItemDetailFramework
import kotlinx.coroutines.launch

/**
 * 封装形状状态的类
 */
class ShapeState(initialValue: Double?) {
    var value by mutableStateOf(initialValue)
}

@Composable
fun ShapesScreen(item: Item, modifier: Modifier = Modifier, onBack: () -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // 从 ConfigHolder 获取初始配置，如果为空则创建一个默认的
    val initialShapes = remember { ConfigHolder.shapes }

    // 为每一个形状项声明对应的 ShapeState
    val buttonState = remember { ShapeState(initialShapes.buttonShapeCornerSize) }
    val popupCardState = remember { ShapeState(initialShapes.popupCardShapeCornerSize) }
    val bottomSheetState = remember { ShapeState(initialShapes.bottomSheetShapeCornerSize) }
    val imageState = remember { ShapeState(initialShapes.imageShapeCornerSize) }
    val searchBarState = remember { ShapeState(initialShapes.searchBarShapeCornerSize) }

    ItemDetailFramework(
        item = item,
        modifier = modifier,
        onBack = onBack,
        snackbarHostState = snackbarHostState,
        onSaveClicked = {
            // 使用 copy 方法创建新的 Shapes 对象
            var updatedShapes = initialShapes

            buttonState.value?.let {
                updatedShapes = updatedShapes.copy(buttonShapeCornerSize = it)
            }
            popupCardState.value?.let {
                updatedShapes = updatedShapes.copy(popupCardShapeCornerSize = it)
            }
            bottomSheetState.value?.let {
                updatedShapes = updatedShapes.copy(bottomSheetShapeCornerSize = it)
            }
            imageState.value?.let { updatedShapes = updatedShapes.copy(imageShapeCornerSize = it) }
            searchBarState.value?.let {
                updatedShapes = updatedShapes.copy(searchBarShapeCornerSize = it)
            }

            ConfigHolder.shapes = updatedShapes
            scope.launch {
                snackbarHostState.showSnackbar("Shapes saved!")
            }
        }
    ) {
        ShapeRow(label = "Button Corner Radius", state = buttonState)
        ShapeRow(label = "Popup Card Corner Radius", state = popupCardState)
        ShapeRow(label = "Bottom Sheet Corner Radius", state = bottomSheetState)
        ShapeRow(label = "Image Corner Radius", state = imageState)
        ShapeRow(label = "Search Bar Corner Radius", state = searchBarState)
    }
}

@Composable
fun ShapeRow(
    label: String,
    state: ShapeState
) {
    var expanded by remember { mutableStateOf(false) }
    val currentValue = state.value

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = currentValue?.toInt()?.toString() ?: "undefined",
                style = MaterialTheme.typography.bodyMedium,
                color = if (currentValue != null) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.outline
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                val sliderValue = currentValue?.toFloat() ?: 0f
                Slider(
                    value = sliderValue,
                    onValueChange = { state.value = it.toDouble() },
                    valueRange = 0f..50f,
                    steps = 49
                )
                Text(
                    text = "Value: ${state.value?.toInt() ?: 0}",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.align(Alignment.End)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ShapesScreenPreview() {
    ShapesScreen(Shapes) {}
}
