package com.mapxus.uisdkshowcase.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mapxus.dropin.uicore.DISdk
import com.mapxus.dropin.uicore.api.DIConfig
import com.mapxus.dropin.uicore.api.model.Bounds
import com.mapxus.dropin.uicore.api.model.VenueInfo
import com.mapxus.dropin.uicore.api.search.IDataSearcher
import com.mapxus.uisdkshowcase.ConfigHolder
import com.mapxus.uisdkshowcase.ui.theme.UISDKShowcaseTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

private enum class SearchMode { SYNCHRONOUS, ASYNCHRONOUS }

private enum class SearchType { VENUE_NAME, VENUE_IDS, POI_ID, VENUE_NEARBY, POI_BOUNDS }

private data class SearchItem(
    val title: String,
    val type: SearchType,
    val mode: SearchMode,
    val fields: List<SearchField>
)

private data class SearchField(
    val key: String,
    val label: String,
    val keyboardType: KeyboardType = KeyboardType.Text
)

private sealed interface SearchState {
    data object Idle : SearchState
    data object Loading : SearchState
    data class Success(val message: String) : SearchState
    data class Error(val message: String) : SearchState
}

private val venueNameField = SearchField("name", "Venue name")
private val venueIdsField = SearchField("ids", "Venue IDs (comma or newline separated)")
private val poiIdField = SearchField("id", "POI ID")
private val latitudeField = SearchField("latitude", "Latitude", KeyboardType.Decimal)
private val longitudeField = SearchField("longitude", "Longitude", KeyboardType.Decimal)
private val radiusField = SearchField("radius", "Radius", KeyboardType.Decimal)
private val maxLatField = SearchField("maxLat", "Maximum latitude", KeyboardType.Decimal)
private val maxLonField = SearchField("maxLon", "Maximum longitude", KeyboardType.Decimal)
private val minLatField = SearchField("minLat", "Minimum latitude", KeyboardType.Decimal)
private val minLonField = SearchField("minLon", "Minimum longitude", KeyboardType.Decimal)
private val keywordField = SearchField("keyword", "Keyword (optional)")

private val searchItems = listOf(
    SearchItem("Get venue by name synchronously", SearchType.VENUE_NAME, SearchMode.SYNCHRONOUS, listOf(venueNameField)),
    SearchItem("Get venue by name asynchronously", SearchType.VENUE_NAME, SearchMode.ASYNCHRONOUS, listOf(venueNameField)),
    SearchItem("Get venue by IDs synchronously", SearchType.VENUE_IDS, SearchMode.SYNCHRONOUS, listOf(venueIdsField)),
    SearchItem("Get venue by IDs asynchronously", SearchType.VENUE_IDS, SearchMode.ASYNCHRONOUS, listOf(venueIdsField)),
    SearchItem("Get POI by ID synchronously", SearchType.POI_ID, SearchMode.SYNCHRONOUS, listOf(poiIdField)),
    SearchItem("Get POI by ID asynchronously", SearchType.POI_ID, SearchMode.ASYNCHRONOUS, listOf(poiIdField)),
    SearchItem("Get venue nearby synchronously", SearchType.VENUE_NEARBY, SearchMode.SYNCHRONOUS, listOf(latitudeField, longitudeField, radiusField)),
    SearchItem("Get venue nearby asynchronously", SearchType.VENUE_NEARBY, SearchMode.ASYNCHRONOUS, listOf(latitudeField, longitudeField, radiusField)),
    SearchItem("Get POI in bounds synchronously", SearchType.POI_BOUNDS, SearchMode.SYNCHRONOUS, listOf(maxLatField, maxLonField, minLatField, minLonField, keywordField)),
    SearchItem("Get POI in bounds asynchronously", SearchType.POI_BOUNDS, SearchMode.ASYNCHRONOUS, listOf(maxLatField, maxLonField, minLatField, minLonField, keywordField))
)

private const val searchErrorText = "No result found."

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DataSearchScreen(
    searcher: IDataSearcher,
    modifier: Modifier = Modifier,
    onClose: () -> Unit
) {
    var selectedItem by remember { mutableStateOf<SearchItem?>(null) }

    BackHandler(enabled = selectedItem != null) { selectedItem = null }

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(selectedItem?.title ?: "Data Search") },
                navigationIcon = {
                    IconButton(onClick = { if (selectedItem == null) onClose() else selectedItem = null }) {
                        Icon(
                            if (selectedItem == null) Icons.Default.Close else Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = if (selectedItem == null) "Close" else "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (selectedItem == null) {
            SearchItemList(
                modifier = Modifier.padding(innerPadding),
                onItemClick = { selectedItem = it }
            )
        } else {
            SearchDetailScreen(
                item = selectedItem!!,
                searcher = searcher,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
private fun SearchItemList(modifier: Modifier = Modifier, onItemClick: (SearchItem) -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        searchItems.forEach { item ->
            Button(
                onClick = { onItemClick(item) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(item.title)
            }
        }
    }
}

@Composable
private fun SearchDetailScreen(
    item: SearchItem,
    searcher: IDataSearcher,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val values = remember(item) { mutableStateMapOf<String, String>() }
    var state by remember(item) { mutableStateOf<SearchState>(SearchState.Idle) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item.fields.forEach { field ->
            OutlinedTextField(
                value = values[field.key].orEmpty(),
                onValueChange = { values[field.key] = it },
                label = { Text(field.label) },
                keyboardOptions = KeyboardOptions(keyboardType = field.keyboardType),
                modifier = Modifier.fillMaxWidth(),
                minLines = if (field == venueIdsField) 3 else 1
            )
        }

        Button(
            onClick = {
                val error = validateInput(item, values)
                if (error != null) {
                    state = SearchState.Error(error)
                } else {
                    state = SearchState.Loading
                    executeSearch(item, values, searcher, scope) { state = it }
                }
            },
            enabled = state !is SearchState.Loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search")
        }

        when (val currentState = state) {
            SearchState.Idle -> Unit
            SearchState.Loading -> CircularProgressIndicator()
            is SearchState.Success -> ResultCard("Result", currentState.message, false)
            is SearchState.Error -> ResultCard("Error", currentState.message, true)
        }
    }
}

@Composable
private fun ResultCard(title: String, message: String, isError: Boolean) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            )
            Text(message)
        }
    }
}

private fun validateInput(item: SearchItem, values: Map<String, String>): String? {
    val requiredFields = if (item.type == SearchType.POI_BOUNDS) item.fields - keywordField else item.fields
    if (requiredFields.any { values[it.key].isNullOrBlank() }) return "Please complete all required fields."
    val numericFields = item.fields.filter { it.keyboardType == KeyboardType.Decimal }
    if (numericFields.any { values[it.key]?.toDoubleOrNull() == null }) return "Please enter valid numbers."
    if (item.type == SearchType.VENUE_IDS && parseVenueIds(values.getValue("ids")).isEmpty()) {
        return "Please enter at least one venue ID."
    }
    return null
}

private fun parseVenueIds(value: String): List<String> =
    value.split(',', '\n').map(String::trim).filter(String::isNotEmpty)

private fun executeSearch(
    item: SearchItem,
    values: Map<String, String>,
    searcher: IDataSearcher,
    scope: kotlinx.coroutines.CoroutineScope,
    updateState: (SearchState) -> Unit
) {
    if (item.mode == SearchMode.SYNCHRONOUS) {
        scope.launch {
            val state = runCatching {
                withContext(Dispatchers.IO) { executeSynchronousSearch(item.type, values, searcher) }
            }.getOrElse { SearchState.Error(it.message ?: searchErrorText) }
            updateState(state)
        }
        return
    }

    runCatching {
        when (item.type) {
            SearchType.VENUE_NAME -> searcher.searchVenuesByName(values.getValue("name")) {
                updateState(it.toVenueSearchState())
            }
            SearchType.VENUE_IDS -> searcher.searchVenuesByIds(parseVenueIds(values.getValue("ids"))) {
                updateState(it.toVenueNameSearchState())
            }
            SearchType.POI_ID -> searcher.searchPoiById(values.getValue("id")) {
                updateState(it?.let { poi -> SearchState.Success(poi.toString()) } ?: SearchState.Error(searchErrorText))
            }
            SearchType.VENUE_NEARBY -> searcher.searchVenuesNearby(
                values.getValue("latitude").toDouble(),
                values.getValue("longitude").toDouble(),
                values.getValue("radius").toDouble()
            ) { updateState(SearchState.Success("venue nearby count: ${it.size}")) }
            SearchType.POI_BOUNDS -> searcher.searchPoiByBounds(values.toBounds(), values["keyword"].orEmpty()) {
                updateState(it?.let { result -> SearchState.Success(result.toString()) } ?: SearchState.Error(searchErrorText))
            }
        }
    }.onFailure { updateState(SearchState.Error(it.message ?: searchErrorText)) }
}

private suspend fun executeSynchronousSearch(
    type: SearchType,
    values: Map<String, String>,
    searcher: IDataSearcher
): SearchState = when (type) {
    SearchType.VENUE_NAME -> searcher.searchVenuesByName(values.getValue("name")).toVenueSearchState()
    SearchType.VENUE_IDS -> searcher.searchVenuesByIds(parseVenueIds(values.getValue("ids"))).toVenueNameSearchState()
    SearchType.POI_ID -> searcher.searchPoiById(values.getValue("id"))
        ?.let { SearchState.Success(it.toString()) } ?: SearchState.Error(searchErrorText)
    SearchType.VENUE_NEARBY -> searcher.searchVenuesNearby(
        values.getValue("latitude").toDouble(),
        values.getValue("longitude").toDouble(),
        values.getValue("radius").toDouble()
    ).let { SearchState.Success("venue nearby count: ${it.size}") }
    SearchType.POI_BOUNDS -> searcher.searchPoiByBounds(values.toBounds(), values["keyword"].orEmpty())
        ?.let { SearchState.Success(it.toString()) } ?: SearchState.Error(searchErrorText)
}

private fun List<*>.toVenueSearchState(): SearchState =
    if (isEmpty()) SearchState.Error(searchErrorText) else SearchState.Success(joinToString("\n"))

private fun List<VenueInfo>.toVenueNameSearchState(): SearchState =
    if (isEmpty()) SearchState.Error(searchErrorText)
    else SearchState.Success(joinToString("\n") { it.venueName })

private fun Map<String, String>.toBounds() = Bounds(
    maxLat = getValue("maxLat").toDouble(),
    maxLon = getValue("maxLon").toDouble(),
    minLat = getValue("minLat").toDouble(),
    minLon = getValue("minLon").toDouble()
)

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
