package com.mapxus.uisdkshowcase.activity

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.mapxus.dropin.uicore.DISdk
import com.mapxus.dropin.uicore.navi.LandingPageRoute
import com.mapxus.uisdkshowcase.ConfigHolder
import com.mapxus.uisdkshowcase.ui.theme.UISDKShowcaseTheme

class MapActivity : ComponentActivity() {
    private var diSdk: DISdk? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        diSdk = DISdk(ConfigHolder.getConfig())
        setContent {
            UISDKShowcaseTheme {
                var diView by remember { mutableStateOf<View?>(null) }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                ) {
                    AndroidView(
                        modifier = Modifier.fillMaxSize(),
                        factory = {
                            if (diView == null) {
                                diView = diSdk?.getView(it)
                            }
                            diSdk?.start(LandingPageRoute())
                            diView!!
                        }
                    )
                }
            }
        }
    }
}
