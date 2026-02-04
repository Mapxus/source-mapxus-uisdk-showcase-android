package com.mapxus.uisdkshowcase.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mapxus.uisdkshowcase.ConfigHolder
import com.mapxus.uisdkshowcase.ui.theme.UISDKShowcaseTheme

@Composable
fun ListenerScreen(modifier: Modifier = Modifier) {
    var isCheckToolTipsListener by remember { mutableStateOf(ConfigHolder.isSetToolTipsListener) }
    var isCheckLandingPageEventListener by remember { mutableStateOf(ConfigHolder.isSetLandingPageEventListener) }
    var isCheckVenueEventListener by remember { mutableStateOf(ConfigHolder.isSetVenueEventListener) }
    var isCheckBuildingEventListener by remember { mutableStateOf(ConfigHolder.isSetBuildingEventListener) }
    var isCheckPoiEventListener by remember { mutableStateOf(ConfigHolder.isSetPoiEventListener) }
    var isCheckCategorySearchEventListener by remember { mutableStateOf(ConfigHolder.isSetCategorySearchEventListener) }
    var isCheckEventListener by remember { mutableStateOf(ConfigHolder.isSetEventListener) }
    var isCheckKeywordSearchEventListener by remember { mutableStateOf(ConfigHolder.isSetKeywordSearchEventListener) }
    var isCheckMapEventListener by remember { mutableStateOf(ConfigHolder.isSetMapEventListener) }
    var isCheckNavigationEventListener by remember { mutableStateOf(ConfigHolder.isSetNavigationEventListener) }
    var isCheckRoutePlanningEventListener by remember { mutableStateOf(ConfigHolder.isSetRoutePlanningEventListener) }
    var isCheckShareEventListener by remember { mutableStateOf(ConfigHolder.isSetShareEventListener) }
    var isCheckMenuEventListener by remember { mutableStateOf(ConfigHolder.isSetMenuEventListener) }
    var isCheckDataTrackingListener by remember { mutableStateOf(ConfigHolder.isSetDataTrackingListener) }

    LaunchedEffect(isCheckToolTipsListener) {
        ConfigHolder.isSetToolTipsListener = isCheckToolTipsListener
    }

    LaunchedEffect(isCheckLandingPageEventListener) {
        ConfigHolder.isSetLandingPageEventListener = isCheckLandingPageEventListener
    }

    LaunchedEffect(isCheckVenueEventListener) {
        ConfigHolder.isSetVenueEventListener = isCheckVenueEventListener
    }

    LaunchedEffect(isCheckBuildingEventListener) {
        ConfigHolder.isSetBuildingEventListener = isCheckBuildingEventListener
    }

    LaunchedEffect(isCheckPoiEventListener) {
        ConfigHolder.isSetPoiEventListener = isCheckPoiEventListener
    }

    LaunchedEffect(isCheckCategorySearchEventListener) {
        ConfigHolder.isSetCategorySearchEventListener = isCheckCategorySearchEventListener
    }

    LaunchedEffect(isCheckEventListener) {
        ConfigHolder.isSetEventListener = isCheckEventListener
    }

    LaunchedEffect(isCheckKeywordSearchEventListener) {
        ConfigHolder.isSetKeywordSearchEventListener = isCheckKeywordSearchEventListener
    }

    LaunchedEffect(isCheckMapEventListener) {
        ConfigHolder.isSetMapEventListener = isCheckMapEventListener
    }

    LaunchedEffect(isCheckNavigationEventListener) {
        ConfigHolder.isSetNavigationEventListener = isCheckNavigationEventListener
    }

    LaunchedEffect(isCheckRoutePlanningEventListener) {
        ConfigHolder.isSetRoutePlanningEventListener = isCheckRoutePlanningEventListener
    }

    LaunchedEffect(isCheckShareEventListener) {
        ConfigHolder.isSetShareEventListener = isCheckShareEventListener
    }

    LaunchedEffect(isCheckMenuEventListener) {
        ConfigHolder.isSetMenuEventListener = isCheckMenuEventListener
    }

    LaunchedEffect(isCheckDataTrackingListener) {
        ConfigHolder.isSetDataTrackingListener = isCheckDataTrackingListener
    }

    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isCheckToolTipsListener,
                onCheckedChange = { isCheckToolTipsListener = true }
            )
            Text(text = "Intercept ToolTips")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isCheckLandingPageEventListener,
                onCheckedChange = { isCheckLandingPageEventListener = true }
            )
            Text(text = "Intercept Landing Page")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isCheckVenueEventListener,
                onCheckedChange = { isCheckVenueEventListener = true }
            )
            Text(text = "Intercept Venue")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isCheckBuildingEventListener,
                onCheckedChange = { isCheckBuildingEventListener = true }
            )
            Text(text = "Intercept Building")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isCheckPoiEventListener,
                onCheckedChange = { isCheckPoiEventListener = true }
            )
            Text(text = "Intercept POI")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isCheckCategorySearchEventListener,
                onCheckedChange = { isCheckCategorySearchEventListener = true }
            )
            Text(text = "Intercept Category Search")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isCheckEventListener,
                onCheckedChange = { isCheckEventListener = true }
            )
            Text(text = "Intercept Event")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isCheckKeywordSearchEventListener,
                onCheckedChange = { isCheckKeywordSearchEventListener = true }
            )
            Text(text = "Intercept Keyword Search")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isCheckMapEventListener,
                onCheckedChange = { isCheckMapEventListener = true }
            )
            Text(text = "Intercept Map")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isCheckNavigationEventListener,
                onCheckedChange = { isCheckNavigationEventListener = true }
            )
            Text(text = "Intercept Navigation")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isCheckRoutePlanningEventListener,
                onCheckedChange = { isCheckRoutePlanningEventListener = true }
            )
            Text(text = "Intercept Route Planning")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isCheckShareEventListener,
                onCheckedChange = { isCheckShareEventListener = true }
            )
            Text(text = "Intercept Share")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isCheckMenuEventListener,
                onCheckedChange = { isCheckMenuEventListener = true }
            )
            Text(text = "Intercept Menu")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isCheckDataTrackingListener,
                onCheckedChange = { isCheckDataTrackingListener = true }
            )
            Text(text = "Intercept Data Tracking")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListenerScreenPreview() {
    UISDKShowcaseTheme {
        ListenerScreen()
    }
}
