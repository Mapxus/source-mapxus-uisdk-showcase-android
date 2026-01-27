package com.mapxus.uisdkshowcase.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.mapxus.uisdkshowcase.model.module.AppearanceModule
import com.mapxus.uisdkshowcase.model.module.ComponentAndBehaviorModule
import com.mapxus.uisdkshowcase.model.module.LanguageModule
import com.mapxus.uisdkshowcase.model.module.LegalAndAttributionModule
import com.mapxus.uisdkshowcase.model.module.MapBasicModule
import com.mapxus.uisdkshowcase.model.module.Module
import com.mapxus.uisdkshowcase.model.module.NavigationModule
import com.mapxus.uisdkshowcase.model.module.PoiConfigModule
import com.mapxus.uisdkshowcase.model.module.SearchAndFilteringModule
import com.mapxus.uisdkshowcase.model.module.VenueConfigModule
import com.mapxus.uisdkshowcase.ui.ModuleDrawerFramework
import com.mapxus.uisdkshowcase.ui.nav.key.ItemDetail
import com.mapxus.uisdkshowcase.ui.nav.key.ModuleSelecting
import com.mapxus.uisdkshowcase.ui.screen.Screen
import com.mapxus.uisdkshowcase.ui.theme.UISDKShowcaseTheme

class MainActivity : ComponentActivity() {
    private val modules = listOf(
        MapBasicModule,
        AppearanceModule,
        PoiConfigModule,
        VenueConfigModule,
        NavigationModule,
        LanguageModule,
        SearchAndFilteringModule,
        LegalAndAttributionModule,
        ComponentAndBehaviorModule
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainContent(modules = modules) {
                val intent = Intent(this@MainActivity, MapActivity::class.java)
                startActivity(intent)
            }
        }
    }
}

@Composable
private fun MainContent(modules: List<Module>, onOpenMap: () -> Unit) {
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
                        modules = modules,
                        onOpenMap = onOpenMap
                    ) { item ->
                        backStack.add(ItemDetail(item))
                    }
                }
                entry<ItemDetail> { detail ->
                    detail.item.Screen(onBack = { backStack.remove(detail) })
                }
            }
        )
    }
}