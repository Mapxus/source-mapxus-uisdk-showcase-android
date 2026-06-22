package com.mapxus.uisdkshowcase.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.mapxus.dropin.uicore.DISdk
import com.mapxus.dropin.uicore.api.event.BuildingEventListener
import com.mapxus.dropin.uicore.api.event.CategorySearchEventListener
import com.mapxus.dropin.uicore.api.event.EventListener
import com.mapxus.dropin.uicore.api.event.KeywordSearchEventListener
import com.mapxus.dropin.uicore.api.event.LandingPageEventListener
import com.mapxus.dropin.uicore.api.event.MapEventListener
import com.mapxus.dropin.uicore.api.event.MenuEventListener
import com.mapxus.dropin.uicore.api.event.NavigationEventListener
import com.mapxus.dropin.uicore.api.event.PoiEventListener
import com.mapxus.dropin.uicore.api.event.RoutePlanningEventListener
import com.mapxus.dropin.uicore.api.event.ShareEventListener
import com.mapxus.dropin.uicore.api.event.ToolTipsListener
import com.mapxus.dropin.uicore.api.event.VenueEventListener
import com.mapxus.dropin.uicore.api.metrics.DataTrackingListener
import com.mapxus.dropin.uicore.api.metrics.EntityClickData
import com.mapxus.dropin.uicore.api.model.BuildingInfo
import com.mapxus.dropin.uicore.api.model.EventInfo
import com.mapxus.dropin.uicore.api.model.LocationInfo
import com.mapxus.dropin.uicore.api.model.PoiInfo
import com.mapxus.dropin.uicore.api.model.VenueInfo
import com.mapxus.dropin.uicore.model.NavigationPoint
import com.mapxus.uisdkshowcase.ConfigHolder
import com.mapxus.uisdkshowcase.ui.theme.UISDKShowcaseTheme

class MapActivity : ComponentActivity() {
    private var diSdk: DISdk? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        diSdk = DISdk(ConfigHolder.getConfig()).apply { configListener() }
        setContent {
            UISDKShowcaseTheme {
                var diView by remember { mutableStateOf<View?>(null) }
                var hasLocationPermission by remember {
                    mutableStateOf(
                        ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    )
                }

                val permissionLauncher = rememberLauncherForActivityResult(
                    ActivityResultContracts.RequestMultiplePermissions()
                ) { permissions ->
                    hasLocationPermission =
                        permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
                }

                LaunchedEffect(Unit) {
                    if (!hasLocationPermission) {
                        permissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .navigationBarsPadding()
                ) {
                    if (hasLocationPermission) {
                        AndroidView(
                            modifier = Modifier.fillMaxSize(),
                            factory = {
                                if (diView == null) {
                                    diView = diSdk?.getView(it)
                                }
                                diSdk?.start(ConfigHolder.appRoute)
                                diView!!
                            }
                        )
                    }
                }
            }
        }
    }

    private fun DISdk.configListener() {
        with(ConfigHolder) {
            if (isSetToolTipsListener) {
                setToolTipsListener(object : ToolTipsListener {
                    override fun onToolTipsSequenceCompleted() {
                        super.onToolTipsSequenceCompleted()
                        Toast.makeText(
                            this@MapActivity,
                            "onToolTipsSequenceCompleted",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }

            if (isSetLandingPageEventListener) {
                setLandingPageEventListener(object : LandingPageEventListener {
                    override fun useCustomBuildingPageForSearchResult(): Boolean {
                        return true
                    }

                    override fun useCustomCategoryPageForSearchResult(): Boolean {
                        return true
                    }

                    override fun useCustomEventPageForSearchResult(): Boolean {
                        return true
                    }

                    override fun useCustomPoiPageForSearchResult(): Boolean {
                        return true
                    }

                    override fun useCustomVenuePageForCardSelected(): Boolean {
                        return true
                    }

                    override fun useCustomVenuePageForSearchResult(): Boolean {
                        return true
                    }

                    override fun onVenueCardSelected(venueId: String) {
                        Toast.makeText(
                            this@MapActivity,
                            "onVenueCardSelected: $venueId",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onSearchResultVenueSelected(venueId: String) {
                        Toast.makeText(
                            this@MapActivity,
                            "onSearchResultVenueSelected: $venueId",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onSearchResultBuildingSelected(buildingId: String) {
                        Toast.makeText(
                            this@MapActivity,
                            "onSearchResultBuildingSelected: $buildingId",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onSearchResultPoiSelected(poiId: String) {
                        Toast.makeText(
                            this@MapActivity,
                            "onSearchResultPoiSelected: $poiId",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onSearchResultEventSelected(eventId: String) {
                        Toast.makeText(
                            this@MapActivity,
                            "onSearchResultEventSelected: $eventId",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onSearchResultCategorySelected(
                        categoryKey: String,
                        categoryName: String,
                        venueId: String?,
                        buildingId: String?
                    ) {
                        Toast.makeText(
                            this@MapActivity,
                            "onSearchResultCategorySelected: $categoryName",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }

            if (isSetVenueEventListener) {
                setVenueEventListener(object : VenueEventListener {
                    override fun useCustomBuildingPage(): Boolean {
                        return true
                    }

                    override fun useCustomCategoryPage(): Boolean {
                        return true
                    }

                    override fun useCustomCloseAction(): Boolean {
                        return true
                    }

                    override fun useCustomKeywordSearch(): Boolean {
                        return true
                    }

                    override fun useCustomPoiPage(): Boolean {
                        return true
                    }

                    override fun onClose() {
                        Toast.makeText(this@MapActivity, "onClose", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onBuildingSelected(buildingId: String) {
                        Toast.makeText(
                            this@MapActivity,
                            "onBuildingSelected: $buildingId",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onPoiSelected(poiId: String) {
                        Toast.makeText(
                            this@MapActivity,
                            "onPoiSelected: $poiId",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onCategorySelected(
                        categoryKey: String,
                        categoryName: String,
                        venueId: String?,
                        buildingId: String?
                    ) {
                        Toast.makeText(
                            this@MapActivity,
                            "onCategorySelected: $categoryName",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onKeywordSearchRequested(venueId: String) {
                        Toast.makeText(
                            this@MapActivity,
                            "onKeywordSearchRequested: $venueId",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }

            if (isSetBuildingEventListener) {
                setBuildingEventListener(object : BuildingEventListener {
                    override fun useCustomCategoryPage(): Boolean {
                        return true
                    }

                    override fun useCustomKeywordSearch(): Boolean {
                        return true
                    }

                    override fun useCustomPoiPage(): Boolean {
                        return true
                    }

                    override fun onPoiSelected(poiId: String) {
                        Toast.makeText(
                            this@MapActivity,
                            "onPoiSelected: $poiId",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onCategorySelected(
                        categoryKey: String,
                        categoryName: String,
                        venueId: String?,
                        buildingId: String?
                    ) {
                        Toast.makeText(
                            this@MapActivity,
                            "onCategorySelected: $categoryName",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onKeywordSearchRequested(venueId: String) {
                        Toast.makeText(
                            this@MapActivity,
                            "onKeywordSearchRequested: $venueId",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onClose() {
                        Toast.makeText(this@MapActivity, "onClose", Toast.LENGTH_SHORT).show()
                    }

                    override fun onDirectionRequested(destination: NavigationPoint) {
                        Toast.makeText(
                            this@MapActivity,
                            "onDirectionRequested: ${destination.name}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }

            if (isSetPoiEventListener) {
                setPoiEventListener(object : PoiEventListener {
                    override fun onClose() {
                        Toast.makeText(this@MapActivity, "onClose", Toast.LENGTH_SHORT).show()
                    }

                    override fun onEventSelected(eventId: String) {
                        Toast.makeText(
                            this@MapActivity,
                            "onEventSelected: $eventId",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onDirectionRequested(destination: NavigationPoint) {
                        Toast.makeText(
                            this@MapActivity,
                            "onDirectionRequested: ${destination.name}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }

            if (isSetCategorySearchEventListener) {
                setCategorySearchEventListener(object : CategorySearchEventListener {
                    override fun useCustomPoiPage(): Boolean {
                        return true
                    }

                    override fun onPoiSelected(poiId: String) {
                        Toast.makeText(
                            this@MapActivity,
                            "onPoiSelected: $poiId",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onClose() {
                        Toast.makeText(this@MapActivity, "onClose", Toast.LENGTH_SHORT).show()
                    }
                })
            }

            if (isSetEventListener) {
                setEventListener(object : EventListener {
                    override fun onClose() {
                        Toast.makeText(this@MapActivity, "onClose", Toast.LENGTH_SHORT).show()
                    }

                    override fun onPoiSelected(poiId: String) {
                        Toast.makeText(
                            this@MapActivity,
                            "onPoiSelected: $poiId",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onDirectionRequested(destination: NavigationPoint) {
                        Toast.makeText(
                            this@MapActivity,
                            "onDirectionRequested: ${destination.name}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }

            if (isSetKeywordSearchEventListener) {
                setKeywordSearchEventListener(object : KeywordSearchEventListener {
                    override fun onSearchResultPoiSelected(poiId: String) {
                        Toast.makeText(
                            this@MapActivity,
                            "onSearchResultPoiSelected: $poiId",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onSearchResultEventSelected(eventId: String) {
                        Toast.makeText(
                            this@MapActivity,
                            "onSearchResultEventSelected: $eventId",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onSearchResultCategorySelected(
                        categoryKey: String,
                        categoryName: String,
                        venueId: String?,
                        buildingId: String?
                    ) {
                        Toast.makeText(
                            this@MapActivity,
                            "onSearchResultCategorySelected: $categoryName",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }

            if (isSetMapEventListener) {
                setMapEventListener(object : MapEventListener {
                    override fun useCustomBuildingPage(): Boolean {
                        return true
                    }

                    override fun useCustomPoiPage(): Boolean {
                        return true
                    }

                    override fun onBuildingSelected(buildingId: String) {
                        Toast.makeText(
                            this@MapActivity,
                            "onBuildingSelected: $buildingId",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onPoiSelected(poiId: String) {
                        Toast.makeText(
                            this@MapActivity,
                            "onPoiSelected: $poiId",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onVenueSelected(venueId: String) {
                        Toast.makeText(
                            this@MapActivity,
                            "onVenueSelected: $venueId",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onEventSelected(eventId: String) {
                        Toast.makeText(
                            this@MapActivity,
                            "onEventSelected: $eventId",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }

            if (isSetNavigationEventListener) {
                setNavigationEventListener(object : NavigationEventListener {
                    override fun onClose() {
                        Toast.makeText(this@MapActivity, "onClose", Toast.LENGTH_SHORT).show()
                    }
                })
            }

            if (isSetRoutePlanningEventListener) {
                setRoutePlanningEventListener(object : RoutePlanningEventListener {
                    override fun onClose() {
                        Toast.makeText(this@MapActivity, "onClose", Toast.LENGTH_SHORT).show()
                    }
                })
            }

            if (isSetShareEventListener) {
                setShareEventListener(object : ShareEventListener {
                    override fun onShareVenue(
                        venueInfo: VenueInfo?,
                        link: String?
                    ) {
                        Toast.makeText(
                            this@MapActivity,
                            "onShareVenue: ${venueInfo?.venueName}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onShareBuilding(
                        buildingInfo: BuildingInfo?,
                        link: String?
                    ) {
                        Toast.makeText(
                            this@MapActivity,
                            "onShareBuilding: ${buildingInfo?.buildingName}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onShareEvent(
                        eventInfo: EventInfo?,
                        link: String?
                    ) {
                        Toast.makeText(
                            this@MapActivity,
                            "onShareEvent: ${eventInfo?.eventName}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onSharePoi(
                        poiInfo: PoiInfo?,
                        link: String?
                    ) {
                        Toast.makeText(
                            this@MapActivity,
                            "onSharePoi: ${poiInfo?.poiName}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onShareLocation(
                        locationInfo: LocationInfo?,
                        link: String?
                    ) {
                        Toast.makeText(this@MapActivity, "onShareLocation", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
            }

            if (isSetMenuEventListener) {
                setMenuEventListener(object : MenuEventListener {
                    override fun onClose() {
                        Toast.makeText(this@MapActivity, "onClose", Toast.LENGTH_SHORT).show()
                    }
                })
            }

            if (isSetDataTrackingListener) {
                setDataTrackingListener(object : DataTrackingListener {
                    override fun onDirectionButtonClicked(data: EntityClickData) {
                        Toast.makeText(
                            this@MapActivity,
                            "onDirectionButtonClicked",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onEndOfRouteCardShown() {
                        Toast.makeText(
                            this@MapActivity,
                            "onEndOfRouteCardShown",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                    override fun onStartNavigationClicked(data: EntityClickData) {
                        Toast.makeText(
                            this@MapActivity,
                            "onStartNavigationClicked",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }
        }
    }
}
