package com.mapxus.uisdkshowcase.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mapxus.dropin.uicore.DISdk
import com.mapxus.dropin.uicore.api.DIConfig
import com.mapxus.dropin.uicore.api.model.Bounds
import com.mapxus.dropin.uicore.api.search.IDataSearcher
import com.mapxus.uisdkshowcase.ConfigHolder
import com.mapxus.uisdkshowcase.ui.theme.UISDKShowcaseTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataSearchActivity : ComponentActivity() {
    private val searcher: IDataSearcher by lazy { DISdk(ConfigHolder.getConfig()).getDataSearcher() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UISDKShowcaseTheme {
                DataSearchScreen(searcher, modifier = Modifier.fillMaxSize(), onClose = ::finish)
            }
        }
    }
}

private const val leeTungAvenue = "00752cce7b3d43eb96a72a566fe2f4f3"
private const val mPlusMuseum = "06d7e41af5154838a8d6ddda7d10cb0a"
private const val k11Musea = "3cd9b17d-f3f4-4afd-ba34-5e3fad2518a6"
private const val k11ArtMall = "caab5a38-79e1-11e8-8453-951df499024d"
private const val taiKwun = "e679b6fc0818456aa1867aa021a3e84a"
private val venueIds = listOf(leeTungAvenue, mPlusMuseum, k11Musea, k11ArtMall, taiKwun)

private const val searchErrorText = "search error!"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DataSearchScreen(
    searcher: IDataSearcher,
    modifier: Modifier = Modifier,
    onClose: () -> Unit
) {
    val scope = rememberCoroutineScope()

    var resultText by remember { mutableStateOf("") }
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Data Search") },
                navigationIcon = {
                    IconButton(onClose) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            FlowRow {
                Button(
                    onClick = {
                        scope.launch(Dispatchers.IO) {
                            resultText = searcher.searchVenuesByName("k11").let {
                                if (it.isEmpty()) searchErrorText
                                else it.joinToString("\n")
                            }
                        }
                    }
                ) {
                    Text("Get venue by name synchronously")
                }

                Button(
                    onClick = {
                        searcher.searchVenuesByName("k11") {
                            resultText = if (it.isEmpty()) searchErrorText
                            else it.joinToString("\n")
                        }
                    }
                ) {
                    Text("Get venue by name asynchronously")
                }

                Button(
                    onClick = {
                        scope.launch(Dispatchers.IO) {
                            resultText = searcher.searchVenuesByIds(venueIds).let {
                                if (it.isEmpty()) searchErrorText
                                else it.joinToString { venue -> venue.venueName + "\n" }
                            }
                        }
                    }
                ) {
                    Text("Get venue by ids synchronously")
                }

                Button(
                    onClick = {
                        searcher.searchVenuesByIds(venueIds) {
                            resultText = if (it.isEmpty()) searchErrorText
                            else it.joinToString { venue -> venue.venueName + "\n" }
                        }
                    }
                ) {
                    Text("Get venue by ids asynchronously")
                }

                Button(
                    onClick = {
                        scope.launch(Dispatchers.IO) {
                            resultText = searcher.searchPoiById("12735072").let {
                                it?.toString() ?: searchErrorText
                            }
                        }
                    }
                ) {
                    Text("Get POI by id synchronously")
                }

                Button(
                    onClick = {
                        searcher.searchPoiById("12735072") {
                            resultText = it?.toString() ?: searchErrorText
                        }
                    }
                ) {
                    Text("Get POI by id asynchronously")
                }

                Button(
                    onClick = {
                        scope.launch(Dispatchers.IO) {
                            resultText = searcher.searchVenuesNearby(
                                latitude = 22.294484301562978,
                                longitude = 114.17415976524353,
                                radius = 10.0
                            ).let {
                                "venue nearby count: ${it.size}"
                            }
                        }
                    }
                ) {
                    Text("Get venue nearby synchronously")
                }

                Button(
                    onClick = {
                        searcher.searchVenuesNearby(
                            latitude = 22.294484301562978,
                            longitude = 114.17415976524353,
                            radius = 10.0
                        ) {
                            resultText = "venue nearby count: ${it.size}"
                        }
                    }
                ) {
                    Text("Get venue nearby asynchronously")
                }

                Button(
                    onClick = {
                        scope.launch(Dispatchers.IO) {
                            resultText = searcher.searchPoiByBounds(
                                Bounds(
                                    maxLat = 22.294484301562978,
                                    maxLon = 114.17415976524353,
                                    minLat = 22.194484301562978,
                                    minLon = 114.07415976524353,
                                ),
                                ""
                            ).let {
                                it?.toString() ?: searchErrorText
                            }
                        }
                    }
                ) {
                    Text("Get POI in Bounds synchronously")
                }

                Button(
                    onClick = {
                        searcher.searchPoiByBounds(
                            Bounds(
                                maxLat = 22.294484301562978,
                                maxLon = 114.17415976524353,
                                minLat = 22.194484301562978,
                                minLon = 114.07415976524353,
                            ),
                            ""
                        ) {
                            resultText = it?.toString() ?: searchErrorText
                        }
                    }
                ) {
                    Text("Get POI in Bounds asynchronously")
                }
            }

            Text(resultText)
        }
    }
}

@Preview
@Composable
private fun DataSearchScreenPreview() {
    UISDKShowcaseTheme {
        DataSearchScreen(
            searcher = DISdk(DIConfig()).getDataSearcher(),
            onClose = {}
        )
    }
}
