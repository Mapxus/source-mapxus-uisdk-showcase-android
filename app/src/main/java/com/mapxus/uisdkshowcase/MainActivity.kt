package com.mapxus.uisdkshowcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.mapxus.uisdkshowcase.model.item.InitialBounds
import com.mapxus.uisdkshowcase.model.module.MapBasicModule
import com.mapxus.uisdkshowcase.model.module.Module
import com.mapxus.uisdkshowcase.ui.ModuleDrawerFramework
import com.mapxus.uisdkshowcase.ui.nav.key.ItemDetail
import com.mapxus.uisdkshowcase.ui.nav.key.ModuleSelecting
import com.mapxus.uisdkshowcase.ui.screen.InitialBoundsScreen
import com.mapxus.uisdkshowcase.ui.theme.UISDKShowcaseTheme

class MainActivity : ComponentActivity() {
    private val modules = listOf(MapBasicModule)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainContent(modules = modules)
        }
    }
}

@Composable
private fun MainContent(modules: List<Module>) {
    UISDKShowcaseTheme {
        val backStack = rememberNavBackStack(ModuleSelecting)
        NavDisplay(
            backStack = backStack,
            onBack = {
                if (backStack.isNotEmpty()) {
                    backStack.removeAt(backStack.size - 1)
                }
            },
            entryProvider = entryProvider {
                entry<ModuleSelecting> {
                    ModuleDrawerFramework(
                        modifier = Modifier,
                        modules = modules
                    ) { item ->
                        backStack.add(ItemDetail(item))
                    }
                }
                entry<ItemDetail> { detail ->
                    when (val item = detail.item) {
                        InitialBounds -> InitialBoundsScreen(item) { backStack.remove(detail) }
                    }
                }
            }
        )
    }
}