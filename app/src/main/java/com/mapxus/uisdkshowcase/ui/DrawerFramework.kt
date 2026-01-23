package com.mapxus.uisdkshowcase.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.model.module.MapBasicModule
import com.mapxus.uisdkshowcase.model.module.Module
import com.mapxus.uisdkshowcase.ui.component.ItemViewPager
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModuleDrawerFramework(
    modifier: Modifier = Modifier,
    modules: List<Module>,
    onItemClicked: (Item) -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    var currentModuleIndex by remember { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        modifier = modifier,
        drawerContent = {
            ModalDrawerSheet {
                Text("Drawer title", modifier = Modifier.padding(16.dp))
                HorizontalDivider()
                Spacer(Modifier.height(10.dp))
                modules.forEachIndexed { index, module ->
                    NavigationDrawerItem(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        label = { Text(module.title) },
                        selected = currentModuleIndex == index,
                        onClick = {
                            currentModuleIndex = index
                            scope.launch { drawerState.close() }
                        }
                    )
                }
            }
        },
        content = {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { Text(modules[currentModuleIndex].title) },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(imageVector = Icons.Default.Menu, contentDescription = null)
                            }
                        }
                    )
                },
                content = { innerPadding ->
                    ItemViewPager(
                        items = modules[currentModuleIndex].items,
                        modifier = Modifier.padding(innerPadding),
                        onItemClicked = onItemClicked
                    )
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun ModuleDrawerPreview() {
    val modules = listOf(MapBasicModule)
    ModuleDrawerFramework(modules = modules) {}
}