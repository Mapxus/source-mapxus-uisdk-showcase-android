package com.mapxus.uisdkshowcase.ui.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mapxus.dropin.uicore.navi.AppRoute
import com.mapxus.dropin.uicore.navi.BuildingDetailRoute
import com.mapxus.dropin.uicore.navi.EventDetailRoute
import com.mapxus.dropin.uicore.navi.LandingPageRoute
import com.mapxus.dropin.uicore.navi.LocationRoute
import com.mapxus.dropin.uicore.navi.NavigationRoute
import com.mapxus.dropin.uicore.model.CurrentLocationStartPoint
import com.mapxus.dropin.uicore.model.DestinationPoint
import com.mapxus.dropin.uicore.model.GeoDestinationPoint
import com.mapxus.dropin.uicore.model.GeoStartPoint
import com.mapxus.dropin.uicore.model.IndoorLocation
import com.mapxus.dropin.uicore.model.OutdoorLocation
import com.mapxus.dropin.uicore.model.PoiDestinationPoint
import com.mapxus.dropin.uicore.model.StartPoint
import com.mapxus.dropin.uicore.navi.PoiDetailRoute
import com.mapxus.dropin.uicore.navi.VenueDetailRoute
import com.mapxus.uisdkshowcase.ConfigHolder
import com.mapxus.uisdkshowcase.ui.theme.UISDKShowcaseTheme

private const val LANDING_PAGE = "Landing Page"
private const val VENUE_PAGE = "Venue Page"
private const val BUILDING_PAGE = "Building Page"
private const val POI_PAGE = "POI Page"
private const val EVENT_PAGE = "Event Page"
private const val NAVIGATION_PAGE = "Navigation Page"
private const val LOCATION_ROUTE = "Location Route"
private const val START_POINT_NONE = "None"
private const val START_POINT_CURRENT_LOCATION = "Current Location"
private const val START_POINT_GEO = "Geo Start Point"
private const val DESTINATION_POINT_NONE = "None"
private const val DESTINATION_POINT_GEO = "Geo Destination Point"
private const val DESTINATION_POINT_POI = "POI Destination Point"
private val DECIMAL_INPUT_REGEX = Regex("^-?\\d*(\\.\\d*)?$")

private enum class RouteOption(val title: String) {
    LANDING(LANDING_PAGE),
    VENUE(VENUE_PAGE),
    BUILDING(BUILDING_PAGE),
    POI(POI_PAGE),
    EVENT(EVENT_PAGE),
    NAVIGATION(NAVIGATION_PAGE),
    LOCATION(LOCATION_ROUTE),
}

private sealed interface RouteEditorConfig {
    data object None : RouteEditorConfig

    data class IdField(
        val label: String,
        val read: () -> String,
        val write: (String) -> Unit,
    ) : RouteEditorConfig

    data object Navigation : RouteEditorConfig

    data object Location : RouteEditorConfig
}

private data class RouteOptionSpec(
    val option: RouteOption,
    val editor: RouteEditorConfig,
    val buildRoute: () -> AppRoute,
)

private fun routeOption(route: AppRoute): RouteOption = when (route) {
    is VenueDetailRoute -> RouteOption.VENUE
    is BuildingDetailRoute -> RouteOption.BUILDING
    is PoiDetailRoute -> RouteOption.POI
    is EventDetailRoute -> RouteOption.EVENT
    is NavigationRoute -> RouteOption.NAVIGATION
    is LocationRoute -> RouteOption.LOCATION
    else -> RouteOption.LANDING
}

private fun routeOptionSpec(option: RouteOption): RouteOptionSpec = when (option) {
    RouteOption.LANDING -> RouteOptionSpec(
        option = option,
        editor = RouteEditorConfig.None,
        buildRoute = { LandingPageRoute() }
    )

    RouteOption.VENUE -> RouteOptionSpec(
        option = option,
        editor = RouteEditorConfig.IdField(
            label = "Venue Id",
            read = { ConfigHolder.routeVenueId },
            write = { ConfigHolder.routeVenueId = it }
        ),
        buildRoute = { VenueDetailRoute(ConfigHolder.routeVenueId) }
    )

    RouteOption.BUILDING -> RouteOptionSpec(
        option = option,
        editor = RouteEditorConfig.IdField(
            label = "Building Id",
            read = { ConfigHolder.routeBuildingId },
            write = { ConfigHolder.routeBuildingId = it }
        ),
        buildRoute = { BuildingDetailRoute(ConfigHolder.routeBuildingId) }
    )

    RouteOption.POI -> RouteOptionSpec(
        option = option,
        editor = RouteEditorConfig.IdField(
            label = "POI Id",
            read = { ConfigHolder.routePoiId },
            write = { ConfigHolder.routePoiId = it }
        ),
        buildRoute = { PoiDetailRoute(ConfigHolder.routePoiId) }
    )

    RouteOption.EVENT -> RouteOptionSpec(
        option = option,
        editor = RouteEditorConfig.IdField(
            label = "Event Id",
            read = { ConfigHolder.routeEventId },
            write = { ConfigHolder.routeEventId = it }
        ),
        buildRoute = { EventDetailRoute(ConfigHolder.routeEventId) }
    )

    RouteOption.NAVIGATION -> RouteOptionSpec(
        option = option,
        editor = RouteEditorConfig.Navigation,
        buildRoute = { buildNavigationRoute() }
    )

    RouteOption.LOCATION -> RouteOptionSpec(
        option = option,
        editor = RouteEditorConfig.Location,
        buildRoute = { buildLocationRoute() }
    )
}

private fun routeInputValue(option: RouteOption): String {
    val editor = routeOptionSpec(option).editor
    return if (editor is RouteEditorConfig.IdField) editor.read() else ""
}

private fun isDecimalInput(value: String): Boolean = value.isEmpty() || DECIMAL_INPUT_REGEX.matches(value)

private fun startPointTypeLabel(type: ConfigHolder.RouteStartPointType): String = when (type) {
    ConfigHolder.RouteStartPointType.NONE -> START_POINT_NONE
    ConfigHolder.RouteStartPointType.CURRENT_LOCATION -> START_POINT_CURRENT_LOCATION
    ConfigHolder.RouteStartPointType.GEO -> START_POINT_GEO
}

private fun destinationPointTypeLabel(type: ConfigHolder.RouteDestinationPointType): String =
    when (type) {
        ConfigHolder.RouteDestinationPointType.NONE -> DESTINATION_POINT_NONE
        ConfigHolder.RouteDestinationPointType.GEO -> DESTINATION_POINT_GEO
        ConfigHolder.RouteDestinationPointType.POI -> DESTINATION_POINT_POI
    }

private fun buildNavigationStartPoint(): StartPoint? =
    when (ConfigHolder.routeNavigationStartPointType) {
        ConfigHolder.RouteStartPointType.NONE -> null
        ConfigHolder.RouteStartPointType.CURRENT_LOCATION -> CurrentLocationStartPoint()
        ConfigHolder.RouteStartPointType.GEO -> {
            val latitude = ConfigHolder.routeNavigationStartLatitude.toDoubleOrNull()
            val longitude = ConfigHolder.routeNavigationStartLongitude.toDoubleOrNull()
            if (latitude == null || longitude == null) {
                null
            } else {
                if (ConfigHolder.routeNavigationStartFloorId.isNotEmpty())
                    GeoStartPoint.createWithFloor(
                        latitude = latitude,
                        longitude = longitude,
                        floorId = ConfigHolder.routeNavigationStartFloorId
                    ) else if (ConfigHolder.routeNavigationStartSharedFloorId.isNotEmpty())
                    GeoStartPoint.createWithSharedFloor(
                        latitude = latitude,
                        longitude = longitude,
                        sharedFloorId = ConfigHolder.routeNavigationStartSharedFloorId
                    )
                else
                    GeoStartPoint.createWith(latitude = latitude, longitude = longitude)
            }
        }
    }

private fun buildNavigationDestinationPoint(): DestinationPoint? =
    when (ConfigHolder.routeNavigationDestinationPointType) {
        ConfigHolder.RouteDestinationPointType.NONE -> null
        ConfigHolder.RouteDestinationPointType.GEO -> {
            val latitude = ConfigHolder.routeNavigationDestinationLatitude.toDoubleOrNull()
            val longitude = ConfigHolder.routeNavigationDestinationLongitude.toDoubleOrNull()
            if (latitude == null || longitude == null) {
                null
            } else {
                if (ConfigHolder.routeNavigationDestinationFloorId.isNotEmpty())
                    GeoDestinationPoint.createWithFloor(
                        latitude = latitude,
                        longitude = longitude,
                        floorId = ConfigHolder.routeNavigationDestinationFloorId
                    ) else if (ConfigHolder.routeNavigationDestinationSharedFloorId.isNotEmpty())
                    GeoDestinationPoint.createWithSharedFloor(
                        latitude = latitude,
                        longitude = longitude,
                        sharedFloorId = ConfigHolder.routeNavigationDestinationSharedFloorId
                    )
                else
                    GeoDestinationPoint.createWith(latitude = latitude, longitude = longitude)
            }
        }

        ConfigHolder.RouteDestinationPointType.POI -> {
            val poiId = ConfigHolder.routeNavigationDestinationPoiId
            if (poiId.isBlank()) null else PoiDestinationPoint.createWith(poiId = poiId)
        }
    }

private fun buildNavigationRoute(): NavigationRoute = NavigationRoute(
    buildNavigationStartPoint(),
    buildNavigationDestinationPoint()
)

private fun buildLocationRoute(): LocationRoute {
    val latitude = ConfigHolder.routeLocationLatitude.toDoubleOrNull() ?: 0.0
    val longitude = ConfigHolder.routeLocationLongitude.toDoubleOrNull() ?: 0.0
    val floorId = ConfigHolder.routeLocationFloorId.takeIf { it.isNotBlank() }
    val sharedFloorId = ConfigHolder.routeLocationSharedFloorId.takeIf { it.isNotBlank() }
    val location = when {
        floorId != null -> IndoorLocation.createWithFloor(latitude, longitude, floorId)
        sharedFloorId != null -> IndoorLocation.createWithSharedFloor(latitude, longitude, sharedFloorId)
        else -> OutdoorLocation.createWith(latitude, longitude)
    }
    return LocationRoute(location)
}

private data class NavigationFormState(
    val startPointType: ConfigHolder.RouteStartPointType,
    val destinationPointType: ConfigHolder.RouteDestinationPointType,
    val startLatitude: String,
    val startLongitude: String,
    val startFloorId: String,
    val startSharedFloorId: String,
    val destinationLatitude: String,
    val destinationLongitude: String,
    val destinationFloorId: String,
    val destinationSharedFloorId: String,
    val destinationPoiId: String,
)

private data class LocationFormState(
    val latitude: String,
    val longitude: String,
    val floorId: String,
    val sharedFloorId: String,
)

private fun navigationFormStateFromConfig(): NavigationFormState = NavigationFormState(
    startPointType = ConfigHolder.routeNavigationStartPointType,
    destinationPointType = ConfigHolder.routeNavigationDestinationPointType,
    startLatitude = ConfigHolder.routeNavigationStartLatitude,
    startLongitude = ConfigHolder.routeNavigationStartLongitude,
    startFloorId = ConfigHolder.routeNavigationStartFloorId,
    startSharedFloorId = ConfigHolder.routeNavigationStartSharedFloorId,
    destinationLatitude = ConfigHolder.routeNavigationDestinationLatitude,
    destinationLongitude = ConfigHolder.routeNavigationDestinationLongitude,
    destinationFloorId = ConfigHolder.routeNavigationDestinationFloorId,
    destinationSharedFloorId = ConfigHolder.routeNavigationDestinationSharedFloorId,
    destinationPoiId = ConfigHolder.routeNavigationDestinationPoiId
)

private fun locationFormStateFromConfig(): LocationFormState = LocationFormState(
    latitude = ConfigHolder.routeLocationLatitude,
    longitude = ConfigHolder.routeLocationLongitude,
    floorId = ConfigHolder.routeLocationFloorId,
    sharedFloorId = ConfigHolder.routeLocationSharedFloorId,
)

private fun NavigationFormState.persistToConfig() {
    ConfigHolder.routeNavigationStartPointType = startPointType
    ConfigHolder.routeNavigationDestinationPointType = destinationPointType
    ConfigHolder.routeNavigationStartLatitude = startLatitude
    ConfigHolder.routeNavigationStartLongitude = startLongitude
    ConfigHolder.routeNavigationStartFloorId = startFloorId
    ConfigHolder.routeNavigationStartSharedFloorId = startSharedFloorId
    ConfigHolder.routeNavigationDestinationLatitude = destinationLatitude
    ConfigHolder.routeNavigationDestinationLongitude = destinationLongitude
    ConfigHolder.routeNavigationDestinationFloorId = destinationFloorId
    ConfigHolder.routeNavigationDestinationSharedFloorId = destinationSharedFloorId
    ConfigHolder.routeNavigationDestinationPoiId = destinationPoiId
}

private fun LocationFormState.persistToConfig() {
    ConfigHolder.routeLocationLatitude = latitude
    ConfigHolder.routeLocationLongitude = longitude
    ConfigHolder.routeLocationFloorId = floorId
    ConfigHolder.routeLocationSharedFloorId = sharedFloorId
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutePathsScreen(modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(routeOption(ConfigHolder.appRoute)) }
    var selectedRouteInput by remember { mutableStateOf(routeInputValue(selectedOption)) }
    var navigationFormState by remember { mutableStateOf(navigationFormStateFromConfig()) }
    var locationFormState by remember { mutableStateOf(locationFormStateFromConfig()) }

    fun updateSelectedRoute() {
        ConfigHolder.appRoute = routeOptionSpec(selectedOption).buildRoute()
    }

    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
                value = selectedOption.title,
                onValueChange = {},
                readOnly = true,
                label = { Text("Route Paths") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                RouteOption.entries.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option.title, style = MaterialTheme.typography.bodyLarge) },
                        onClick = {
                            selectedOption = option
                            selectedRouteInput = routeInputValue(option)
                            updateSelectedRoute()
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }

        val selectedSpec = routeOptionSpec(selectedOption)
        when (val editor = selectedSpec.editor) {
            is RouteEditorConfig.IdField -> {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    value = selectedRouteInput,
                    onValueChange = { newValue ->
                        selectedRouteInput = newValue
                        editor.write(newValue)
                        updateSelectedRoute()
                    },
                    singleLine = true,
                    label = { Text(editor.label) }
                )
            }

            RouteEditorConfig.Navigation -> {
                NavigationRouteEditor(
                    state = navigationFormState,
                    onStateChange = { updatedState ->
                        navigationFormState = updatedState
                        updatedState.persistToConfig()
                        updateSelectedRoute()
                    }
                )
            }

            RouteEditorConfig.Location -> {
                LocationRouteEditor(
                    state = locationFormState,
                    onStateChange = { updatedState ->
                        locationFormState = updatedState
                        updatedState.persistToConfig()
                        updateSelectedRoute()
                    }
                )
            }

            RouteEditorConfig.None -> Unit
        }
    }
}

@Composable
private fun LocationRouteEditor(
    state: LocationFormState,
    onStateChange: (LocationFormState) -> Unit,
) {
    PointDataSection("Location Data") {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            value = state.latitude,
            onValueChange = {
                if (isDecimalInput(it)) {
                    onStateChange(state.copy(latitude = it))
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            label = { Text("Latitude") }
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            value = state.longitude,
            onValueChange = {
                if (isDecimalInput(it)) {
                    onStateChange(state.copy(longitude = it))
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            label = { Text("Longitude") }
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            value = state.floorId,
            onValueChange = { onStateChange(state.copy(floorId = it)) },
            singleLine = true,
            label = { Text("Floor Id (optional)") }
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            value = state.sharedFloorId,
            onValueChange = { onStateChange(state.copy(sharedFloorId = it)) },
            singleLine = true,
            label = { Text("Shared Floor Id (optional; used when Floor Id is empty)") }
        )
    }
}

@Composable
private fun NavigationRouteEditor(
    state: NavigationFormState,
    onStateChange: (NavigationFormState) -> Unit,
) {
    Column(modifier = Modifier.animateContentSize()) {
        Text(
            text = "Start Point",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )
        PrimaryTabRow(
            selectedTabIndex = ConfigHolder.RouteStartPointType.entries.indexOf(state.startPointType),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            ConfigHolder.RouteStartPointType.entries.forEach { type ->
                Tab(
                    selected = state.startPointType == type,
                    onClick = { onStateChange(state.copy(startPointType = type)) },
                    text = { Text(startPointTypeLabel(type)) }
                )
            }
        }

        if (state.startPointType == ConfigHolder.RouteStartPointType.GEO) {
            PointDataSection("Start Point Data") {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    value = state.startLatitude,
                    onValueChange = {
                        if (isDecimalInput(it)) {
                            onStateChange(state.copy(startLatitude = it))
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    label = { Text("Start Latitude") }
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    value = state.startLongitude,
                    onValueChange = {
                        if (isDecimalInput(it)) {
                            onStateChange(state.copy(startLongitude = it))
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    label = { Text("Start Longitude") }
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    value = state.startFloorId,
                    onValueChange = { onStateChange(state.copy(startFloorId = it)) },
                    singleLine = true,
                    label = { Text("Start Floor Id") }
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    value = state.startSharedFloorId,
                    onValueChange = { onStateChange(state.copy(startSharedFloorId = it)) },
                    singleLine = true,
                    label = { Text("Start Shared Floor Id (only when Floor ID is null)") }
                )
            }
        }

        Text(
            text = "Destination Point",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )
        PrimaryTabRow(
            selectedTabIndex = ConfigHolder.RouteDestinationPointType.entries.indexOf(state.destinationPointType),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            ConfigHolder.RouteDestinationPointType.entries.forEach { type ->
                Tab(
                    selected = state.destinationPointType == type,
                    onClick = { onStateChange(state.copy(destinationPointType = type)) },
                    text = { Text(destinationPointTypeLabel(type)) }
                )
            }
        }

        when (state.destinationPointType) {
            ConfigHolder.RouteDestinationPointType.GEO -> {
                PointDataSection("Destination Point Data") {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        value = state.destinationLatitude,
                        onValueChange = {
                            if (isDecimalInput(it)) {
                                onStateChange(state.copy(destinationLatitude = it))
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true,
                        label = { Text("Destination Latitude") }
                    )
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                        value = state.destinationLongitude,
                        onValueChange = {
                            if (isDecimalInput(it)) {
                                onStateChange(state.copy(destinationLongitude = it))
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true,
                        label = { Text("Destination Longitude") }
                    )
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                        value = state.destinationFloorId,
                        onValueChange = { onStateChange(state.copy(destinationFloorId = it)) },
                        singleLine = true,
                        label = { Text("Destination Floor Id") }
                    )
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                        value = state.destinationSharedFloorId,
                        onValueChange = { onStateChange(state.copy(destinationSharedFloorId = it)) },
                        singleLine = true,
                        label = { Text("Destination Shared Floor Id (only when Floor ID is null)") }
                    )
                }
            }

            ConfigHolder.RouteDestinationPointType.POI -> {
                PointDataSection("Destination Point Data") {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        value = state.destinationPoiId,
                        onValueChange = { onStateChange(state.copy(destinationPoiId = it)) },
                        singleLine = true,
                        label = { Text("Destination POI Id") }
                    )
                }
            }

            ConfigHolder.RouteDestinationPointType.NONE -> Unit
        }
    }
}

@Composable
private fun PointDataSection(
    title: String,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 8.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.labelMedium)
        content()
    }
}

@Preview
@Composable
private fun RoutePathsScreenPreview() {
    UISDKShowcaseTheme {
        RoutePathsScreen()
    }
}