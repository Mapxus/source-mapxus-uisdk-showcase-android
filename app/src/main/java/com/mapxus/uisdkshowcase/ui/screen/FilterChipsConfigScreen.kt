package com.mapxus.uisdkshowcase.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mapxus.dropin.uicore.api.DefaultKeys
import com.mapxus.dropin.uicore.api.MenuConfig
import com.mapxus.dropin.uicore.api.Region
import com.mapxus.dropin.uicore.api.SearchEventType
import com.mapxus.dropin.uicore.api.SearchRange
import com.mapxus.uisdkshowcase.ConfigHolder
import com.mapxus.uisdkshowcase.model.item.FilterChipsConfig
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.ui.component.EditText
import com.mapxus.uisdkshowcase.ui.component.ItemDetailFramework
import com.mapxus.uisdkshowcase.ui.component.TextCheckBox
import com.mapxus.uisdkshowcase.ui.theme.UISDKShowcaseTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

data class FilterChipsConfigState(
    var isDisabled: Boolean = false,
    var regions: List<Region>? = null,
    var globalEventCardKeys: DefaultKeys? = null,
    var venueEventCardKeys: DefaultKeys? = null,
    var flatMenuConfig: MenuConfig? = null,
    var hierarchicalMenuConfig: MenuConfig? = null,
)

@Composable
fun FilterChipsConfigScreen(item: Item, onBack: () -> Unit) {
    var filterChipsConfigState by remember {
        mutableStateOf(ConfigHolder.filterChipsConfig?.let {
            FilterChipsConfigState(
                isDisabled = it.isDisabled,
                regions = it.regions,
                globalEventCardKeys = it.globalEventCardKeys,
                venueEventCardKeys = it.venueEventCardKeys,
                flatMenuConfig = it.flatMenuConfig,
                hierarchicalMenuConfig = it.hierarchicalMenuConfig,
            )
        })
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    ItemDetailFramework(
        item = item,
        snackbarHostState = snackbarHostState,
        onBack = onBack,
        onSaveClicked = {
            ConfigHolder.filterChipsConfig = filterChipsConfigState?.let {
                com.mapxus.dropin.uicore.api.FilterChipsConfig(
                    isDisabled = it.isDisabled,
                    regions = it.regions,
                    globalEventCardKeys = it.globalEventCardKeys,
                    venueEventCardKeys = it.venueEventCardKeys,
                    flatMenuConfig = it.flatMenuConfig,
                    hierarchicalMenuConfig = it.hierarchicalMenuConfig,
                )
            }
            scope.launch {
                snackbarHostState.showSnackbar("Filter chips config saved")
            }
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            val enable by remember { derivedStateOf { filterChipsConfigState != null } }
            TextCheckBox(
                "Customize filter chips config",
                checked = filterChipsConfigState != null
            ) {
                if (!it) filterChipsConfigState = null
                else if (filterChipsConfigState == null) filterChipsConfigState =
                    FilterChipsConfigState()
            }

            AnimatedVisibility(enable) {
                Column {
                    TextCheckBox(
                        "Disable filter chips config",
                        checked = filterChipsConfigState?.isDisabled ?: false
                    ) {
                        filterChipsConfigState = filterChipsConfigState?.copy(isDisabled = it)
                    }

                    RegionsItem(filterChipsConfigState?.regions, scope, snackbarHostState) {
                        filterChipsConfigState = filterChipsConfigState?.copy(regions = it)
                    }

                    DefaultKeysItem(
                        label = "Global Event Card Keys",
                        keys = filterChipsConfigState?.globalEventCardKeys,
                        onCreatedDefaultKeys = {
                            filterChipsConfigState =
                                filterChipsConfigState?.copy(globalEventCardKeys = it)
                        }
                    )

                    DefaultKeysItem(
                        label = "Venue Event Card Keys",
                        keys = filterChipsConfigState?.venueEventCardKeys,
                        onCreatedDefaultKeys = {
                            filterChipsConfigState =
                                filterChipsConfigState?.copy(venueEventCardKeys = it)
                        }
                    )

                    MenuConfigItem(
                        label = "Flat Menu Config",
                        config = filterChipsConfigState?.flatMenuConfig,
                        onCreatedMenuConfig = {
                            filterChipsConfigState =
                                filterChipsConfigState?.copy(flatMenuConfig = it)
                        }
                    )

                    MenuConfigItem(
                        label = "Hierarchical Menu Config",
                        config = filterChipsConfigState?.hierarchicalMenuConfig,
                        onCreatedMenuConfig = {
                            filterChipsConfigState =
                                filterChipsConfigState?.copy(hierarchicalMenuConfig = it)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun MenuConfigItem(
    label: String,
    config: MenuConfig?,
    onCreatedMenuConfig: (MenuConfig) -> Unit = {},
) {
    var isExpanded by remember { mutableStateOf(false) }
    val elevation by animateDpAsState(
        targetValue = if (isExpanded) 8.dp else 2.dp,
        label = "elevation"
    )
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = elevation),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = label)
                IconButton(
                    onClick = { isExpanded = !isExpanded },
                ) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.Check else Icons.Default.Edit,
                        contentDescription = if (isExpanded) "Collapse" else "Edit"
                    )
                }
            }

            var isCreating by remember { mutableStateOf(false) }
            val rootList = remember { mutableStateListOf<SearchRange>() }
            val additionalList = remember { mutableStateListOf<SearchEventType>() }
            var showAddRootDialog by remember { mutableStateOf(false) }
            var showAddAdditionalDialog by remember { mutableStateOf(false) }

            LaunchedEffect(rootList.size, additionalList.size, isCreating) {
                if (isCreating) {
                    onCreatedMenuConfig(
                        MenuConfig.build(
                            rootList.toList(),
                            additionalList.toList()
                        )
                    )
                }
            }

            if (isExpanded) {
                if (isCreating) {
                    Text("Root")
                    rootList.forEach {
                        Text(it.toString())
                    }
                    TextButton(onClick = { showAddRootDialog = true }) {
                        Text("Add Root")
                    }
                    Text("Additional")
                    additionalList.forEach {
                        Text(it.toString())
                    }
                    TextButton(onClick = { showAddAdditionalDialog = true }) {
                        Text("Add Additional")
                    }
                } else {
                    Text(Json.encodeToString(config))
                    TextButton(onClick = { isCreating = true }) {
                        Text(if (config == null) "Create" else "Re-create")
                    }
                }

                if (showAddRootDialog)
                    AddRootDialog(
                        onAddRoot = { rootList.add(it) },
                        onDismiss = { showAddRootDialog = false }
                    )

                if (showAddAdditionalDialog)
                    AddAdditionalDialog(
                        onAddAdditional = { additionalList.add(it) },
                        onDismiss = { showAddAdditionalDialog = false }
                    )
            }
        }
    }
}

@Composable
private fun AddRootDialog(
    onAddRoot: (SearchRange) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedSearchRangeInDialog by remember { mutableStateOf<SearchRange?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Root") },
        text = {
            LazyColumn {
                items(SearchRange.entries) { searchRange ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedSearchRangeInDialog = searchRange }
                            .padding(vertical = 4.dp)
                    ) {
                        RadioButton(
                            selected = selectedSearchRangeInDialog == searchRange,
                            onClick = { selectedSearchRangeInDialog = searchRange }
                        )
                        Text(text = searchRange.name, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                selectedSearchRangeInDialog?.let { range ->
                    onAddRoot(range)
                    onDismiss()
                    selectedSearchRangeInDialog = null
                }
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss()
                selectedSearchRangeInDialog = null
            }) {
                Text("Cancel")
            }
        }
    )
}

@Composable
private fun AddAdditionalDialog(
    onAddAdditional: (SearchEventType) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedSearchEventTypeInDialog by remember { mutableStateOf<SearchEventType?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Root") },
        text = {
            LazyColumn {
                items(SearchEventType.entries) { searchRange ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedSearchEventTypeInDialog = searchRange }
                            .padding(vertical = 4.dp)
                    ) {
                        RadioButton(
                            selected = selectedSearchEventTypeInDialog == searchRange,
                            onClick = { selectedSearchEventTypeInDialog = searchRange }
                        )
                        Text(text = searchRange.name, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                selectedSearchEventTypeInDialog?.let { range ->
                    onAddAdditional(range)
                    onDismiss()
                    selectedSearchEventTypeInDialog = null
                }
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss()
                selectedSearchEventTypeInDialog = null
            }) {
                Text("Cancel")
            }
        }
    )
}

@Composable
private fun DefaultKeysItem(
    label: String,
    keys: DefaultKeys?,
    onCreatedDefaultKeys: (DefaultKeys) -> Unit = {},
) {
    var isExpanded by remember { mutableStateOf(false) }
    val elevation by animateDpAsState(
        targetValue = if (isExpanded) 8.dp else 2.dp,
        label = "elevation"
    )
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = elevation),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = label)
                IconButton(
                    onClick = { isExpanded = !isExpanded },
                ) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.Check else Icons.Default.Edit,
                        contentDescription = if (isExpanded) "Collapse" else "Edit"
                    )
                }
            }

            var createWay by remember { mutableStateOf<DefaultKeysCreateWay?>(null) }
            var showSelectionDialog by remember { mutableStateOf(false) }
            var searchRange by remember { mutableStateOf(SearchRange.entries.first()) }
            var region by remember { mutableStateOf(Region.entries.first()) }
            var searchEventType by remember { mutableStateOf(SearchEventType.entries.first()) }
            val venueId = rememberTextFieldState("")

            if (isExpanded) {
                if (keys == null && createWay == null) {
                    TextButton(onClick = { showSelectionDialog = true }) {
                        Text("Create")
                    }
                } else {
                    if (keys != null && createWay == null) {
                        Text(keys.toString())
                    }

                    when (createWay) {
                        DefaultKeysCreateWay.SEARCH_RANGE -> {
                            LaunchedEffect(searchRange) {
                                onCreatedDefaultKeys(DefaultKeys.build(searchRange))
                            }
                            Text(text = "Search Range")
                            FlowRow {
                                SearchRange.entries.forEach {
                                    FilterChip(
                                        modifier = Modifier.padding(end = 5.dp),
                                        selected = searchRange == it,
                                        onClick = { searchRange = it },
                                        label = { Text(it.toString().lowercase()) }
                                    )
                                }
                            }
                        }

                        DefaultKeysCreateWay.REGION_AND_TYPE -> {
                            LaunchedEffect(region, searchEventType) {
                                onCreatedDefaultKeys(DefaultKeys.build(region, searchEventType))
                            }
                            Text(text = "Region")
                            FlowRow {
                                Region.entries.forEach {
                                    FilterChip(
                                        modifier = Modifier.padding(end = 5.dp),
                                        selected = region == it,
                                        onClick = { region = it },
                                        label = { Text(it.toString().lowercase()) }
                                    )
                                }
                            }

                            Text(text = "Search Event Type")
                            FlowRow {
                                SearchEventType.entries.forEach {
                                    FilterChip(
                                        modifier = Modifier.padding(end = 5.dp),
                                        selected = searchEventType == it,
                                        onClick = { searchEventType = it },
                                        label = { Text(it.toString().lowercase()) }
                                    )
                                }
                            }
                        }

                        DefaultKeysCreateWay.VENUE_ID -> {

                            LaunchedEffect(venueId.text) {
                                onCreatedDefaultKeys(DefaultKeys.build(venueId.text.toString()))
                            }
                            EditText(
                                venueId,
                                modifier = Modifier.fillMaxWidth(),
                                prefixText = "Venue ID",
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Done
                                )
                            )
                        }

                        null -> {}
                    }
                    TextButton(
                        onClick = {
                            searchRange = SearchRange.entries.first()
                            region = Region.entries.first()
                            searchEventType = SearchEventType.entries.first()
                            showSelectionDialog = true
                        }
                    ) {
                        Text("Re-create")
                    }
                }
            }

            if (showSelectionDialog) {
                AlertDialog(
                    onDismissRequest = { showSelectionDialog = false },
                    title = { Text("Select Create Way") },
                    text = {
                        Column {
                            DefaultKeysCreateWay.entries.forEach { way ->
                                TextButton(
                                    onClick = {
                                        createWay = way
                                        showSelectionDialog = false
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(way.name)
                                }
                            }
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = { showSelectionDialog = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun RegionsItem(
    regions: List<Region>?,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    onRegionChanged: (List<Region>) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    val elevation by animateDpAsState(
        targetValue = if (isExpanded) 8.dp else 2.dp,
        label = "elevation"
    )

    val currentOptions = remember {
        mutableStateListOf(*(regions ?: emptyList()).toTypedArray())
    }
    LaunchedEffect(currentOptions.size) {
        onRegionChanged(currentOptions.toList())
    }

    var selectedRegionInDialog by remember { mutableStateOf<Region?>(null) }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = elevation),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Regions")
                IconButton(
                    onClick = { isExpanded = !isExpanded },
                ) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.Check else Icons.Default.Edit,
                        contentDescription = if (isExpanded) "Collapse" else "Edit"
                    )
                }
            }

            var showDialog by remember { mutableStateOf(false) }

            if (isExpanded) {
                currentOptions.forEach {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(it.toString())
                        IconButton(onClick = { currentOptions.remove(it) }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Delete"
                            )
                        }
                    }
                }

                TextButton(onClick = { showDialog = true }) {
                    Text("Add")
                }
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Add Language Option") },
                    text = {
                        LazyColumn {
                            items(Region.entries) { region ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { selectedRegionInDialog = region }
                                        .padding(vertical = 4.dp)
                                ) {
                                    RadioButton(
                                        selected = selectedRegionInDialog == region,
                                        onClick = { selectedRegionInDialog = region }
                                    )
                                    Text(
                                        text = region.name,
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                }
                            }
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            selectedRegionInDialog?.let { region ->
                                if (!currentOptions.contains(region)) {
                                    currentOptions.add(region)
                                } else {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("${region.name} has already been added.")
                                    }
                                }
                                showDialog = false
                                selectedRegionInDialog = null
                            }
                        }) {
                            Text("Add")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showDialog = false
                            selectedRegionInDialog = null
                        }) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}

private enum class DefaultKeysCreateWay {
    SEARCH_RANGE, REGION_AND_TYPE, VENUE_ID
}

@Preview(showBackground = true)
@Composable
fun FilterChipsConfigScreenPreview() {
    UISDKShowcaseTheme {
        FilterChipsConfigScreen(
            item = FilterChipsConfig,
            onBack = {}
        )
    }
}
