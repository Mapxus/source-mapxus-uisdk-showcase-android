package com.mapxus.uisdkshowcase.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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

private sealed interface DialogState {
    data object Idle : DialogState
    data object Loading : DialogState
    data class Success(val message: String) : DialogState
    data class Error(val message: String) : DialogState
}

private const val searchErrorText = "search error!"

@Composable
private fun ResultDialog(state: DialogState, onDismiss: () -> Unit) {
    when (state) {
        is DialogState.Idle -> {}
        is DialogState.Loading -> {
            AlertDialog(
                onDismissRequest = {},
                confirmButton = {},
                title = { Text("Searching…") },
                text = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            )
        }

        is DialogState.Success -> {
            AlertDialog(
                onDismissRequest = onDismiss,
                confirmButton = {
                    TextButton(onClick = onDismiss) { Text("OK") }
                },
                icon = {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(36.dp)
                    )
                },
                title = { Text("Success") },
                text = {
                    Text(
                        state.message,
                        modifier = Modifier.verticalScroll(rememberScrollState())
                    )
                }
            )
        }

        is DialogState.Error -> {
            AlertDialog(
                onDismissRequest = onDismiss,
                confirmButton = {
                    TextButton(onClick = onDismiss) { Text("OK") }
                },
                icon = {
                    Icon(
                        Icons.Default.Warning,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(36.dp)
                    )
                },
                title = { Text("Error") },
                text = { Text(state.message) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DataSearchScreen(
    searcher: IDataSearcher,
    modifier: Modifier = Modifier,
    onClose: () -> Unit
) {
    val scope = rememberCoroutineScope()

    var dialogState by remember { mutableStateOf<DialogState>(DialogState.Idle) }

    ResultDialog(state = dialogState, onDismiss = { dialogState = DialogState.Idle })

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
                        dialogState = DialogState.Loading
                        scope.launch(Dispatchers.IO) {
                            val result = searcher.searchVenuesByName("k11")
                            dialogState = if (result.isEmpty()) DialogState.Error(searchErrorText)
                            else DialogState.Success(result.joinToString("\n"))
                        }
                    }
                ) {
                    Text("Get venue by name synchronously")
                }

                Button(
                    onClick = {
                        dialogState = DialogState.Loading
                        searcher.searchVenuesByName("k11") {
                            dialogState = if (it.isEmpty()) DialogState.Error(searchErrorText)
                            else DialogState.Success(it.joinToString("\n"))
                        }
                    }
                ) {
                    Text("Get venue by name asynchronously")
                }

                Button(
                    onClick = {
                        dialogState = DialogState.Loading
                        scope.launch(Dispatchers.IO) {
                            val result = searcher.searchVenuesByIds(venueIds)
                            dialogState = if (result.isEmpty()) DialogState.Error(searchErrorText)
                            else DialogState.Success(result.joinToString { venue -> venue.venueName + "\n" })
                        }
                    }
                ) {
                    Text("Get venue by ids synchronously")
                }

                Button(
                    onClick = {
                        dialogState = DialogState.Loading
                        searcher.searchVenuesByIds(venueIds) {
                            dialogState = if (it.isEmpty()) DialogState.Error(searchErrorText)
                            else DialogState.Success(it.joinToString { venue -> venue.venueName + "\n" })
                        }
                    }
                ) {
                    Text("Get venue by ids asynchronously")
                }

                Button(
                    onClick = {
                        dialogState = DialogState.Loading
                        scope.launch(Dispatchers.IO) {
                            val result = searcher.searchPoiById("12735072")
                            dialogState = if (result == null) DialogState.Error(searchErrorText)
                            else DialogState.Success(result.toString())
                        }
                    }
                ) {
                    Text("Get POI by id synchronously")
                }

                Button(
                    onClick = {
                        dialogState = DialogState.Loading
                        searcher.searchPoiById("12735072") {
                            dialogState = if (it == null) DialogState.Error(searchErrorText)
                            else DialogState.Success(it.toString())
                        }
                    }
                ) {
                    Text("Get POI by id asynchronously")
                }

                Button(
                    onClick = {
                        dialogState = DialogState.Loading
                        scope.launch(Dispatchers.IO) {
                            val result = searcher.searchVenuesNearby(
                                latitude = 22.294484301562978,
                                longitude = 114.17415976524353,
                                radius = 10.0
                            )
                            dialogState = DialogState.Success("venue nearby count: ${result.size}")
                        }
                    }
                ) {
                    Text("Get venue nearby synchronously")
                }

                Button(
                    onClick = {
                        dialogState = DialogState.Loading
                        searcher.searchVenuesNearby(
                            latitude = 22.294484301562978,
                            longitude = 114.17415976524353,
                            radius = 10.0
                        ) {
                            dialogState = DialogState.Success("venue nearby count: ${it.size}")
                        }
                    }
                ) {
                    Text("Get venue nearby asynchronously")
                }

                Button(
                    onClick = {
                        dialogState = DialogState.Loading
                        scope.launch(Dispatchers.IO) {
                            val result = searcher.searchPoiByBounds(
                                Bounds(
                                    maxLat = 22.294484301562978,
                                    maxLon = 114.17415976524353,
                                    minLat = 22.194484301562978,
                                    minLon = 114.07415976524353,
                                ),
                                ""
                            )
                            dialogState = if (result == null) DialogState.Error(searchErrorText)
                            else DialogState.Success(result.toString())
                        }
                    }
                ) {
                    Text("Get POI in Bounds synchronously")
                }

                Button(
                    onClick = {
                        dialogState = DialogState.Loading
                        searcher.searchPoiByBounds(
                            Bounds(
                                maxLat = 22.294484301562978,
                                maxLon = 114.17415976524353,
                                minLat = 22.194484301562978,
                                minLon = 114.07415976524353,
                            ),
                            ""
                        ) {
                            dialogState = if (it == null) DialogState.Error(searchErrorText)
                            else DialogState.Success(it.toString())
                        }
                    }
                ) {
                    Text("Get POI in Bounds asynchronously")
                }
            }
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
