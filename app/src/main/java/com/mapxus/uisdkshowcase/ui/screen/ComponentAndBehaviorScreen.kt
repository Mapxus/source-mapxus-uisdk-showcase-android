package com.mapxus.uisdkshowcase.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import com.mapxus.dropin.uicore.api.ShareDisplayMode
import com.mapxus.dropin.uicore.api.model.MapLabelsConfig
import com.mapxus.dropin.uicore.api.model.PagesVisibilityOverride
import com.mapxus.dropin.uicore.api.model.PinVisibilityConfig
import com.mapxus.uisdkshowcase.ConfigHolder
import com.mapxus.uisdkshowcase.ui.theme.UISDKShowcaseTheme

private enum class VisibilityOverrideOption(val value: Boolean?, val label: String) {
    DEFAULT(null, "Default (null)"),
    VISIBLE(true, "Visible (true)"),
    HIDDEN(false, "Hidden (false)");

    companion object {
        fun from(value: Boolean?) = entries.first { it.value == value }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ComponentAndBehaviorScreen(modifier: Modifier = Modifier) {
    var isShoplusButtonVisible by remember { mutableStateOf(ConfigHolder.isShoplusButtonVisible) }
    var isVenueBackButtonVisible by remember { mutableStateOf(ConfigHolder.isVenueBackButtonVisible) }
    var shareDisplayMode by remember { mutableStateOf(ConfigHolder.shareDisplayMode) }

    // Map Labels Config state
    val currentMapLabelsConfig = ConfigHolder.mapLabelsConfig
    var fallbackVisibility by remember {
        mutableStateOf(currentMapLabelsConfig?.buildingPins?.fallbackVisibility ?: true)
    }
    var navigationPageOverride by remember {
        mutableStateOf(
            VisibilityOverrideOption.from(
                currentMapLabelsConfig?.buildingPins?.pagesVisibilityOverride?.navigationPage
            )
        )
    }

    LaunchedEffect(isShoplusButtonVisible) {
        ConfigHolder.isShoplusButtonVisible = isShoplusButtonVisible
    }

    LaunchedEffect(isVenueBackButtonVisible) {
        ConfigHolder.isVenueBackButtonVisible = isVenueBackButtonVisible
    }

    LaunchedEffect(shareDisplayMode) {
        ConfigHolder.shareDisplayMode = shareDisplayMode
    }

    LaunchedEffect(fallbackVisibility, navigationPageOverride) {
        ConfigHolder.mapLabelsConfig = MapLabelsConfig(
            buildingPins = PinVisibilityConfig(
                fallbackVisibility = fallbackVisibility,
                pagesVisibilityOverride = PagesVisibilityOverride(
                    navigationPage = navigationPageOverride.value
                )
            )
        )
    }


    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isShoplusButtonVisible,
                onCheckedChange = { isShoplusButtonVisible = it }
            )
            Text(text = "Is Shoplus Button Visible")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isVenueBackButtonVisible,
                onCheckedChange = { isVenueBackButtonVisible = it }
            )
            Text(text = "Is Venue Back Button Visible")
        }

        Text(
            text = "Share Display Mode",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            maxItemsInEachRow = 3
        ) {
            ShareDisplayMode.entries.forEach { mode ->
                FilterChip(
                    selected = shareDisplayMode == mode,
                    onClick = { shareDisplayMode = mode },
                    label = { Text(mode.name) },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }

        // Map Labels Config
        Text(
            text = "Map Labels Config",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )

        Text(
            text = "Building Pins Fallback Visibility",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp, start = 16.dp)
        )

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            maxItemsInEachRow = 2
        ) {
            FilterChip(
                selected = fallbackVisibility,
                onClick = { fallbackVisibility = true },
                label = { Text("Visible") },
                modifier = Modifier.padding(end = 8.dp)
            )
            FilterChip(
                selected = !fallbackVisibility,
                onClick = { fallbackVisibility = false },
                label = { Text("Hidden") },
                modifier = Modifier.padding(end = 8.dp)
            )
        }

        Text(
            text = "Navigation Page Override",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp, start = 16.dp)
        )

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            maxItemsInEachRow = 3
        ) {
            VisibilityOverrideOption.entries.forEach { option ->
                FilterChip(
                    selected = navigationPageOverride == option,
                    onClick = { navigationPageOverride = option },
                    label = { Text(option.label) },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ComponentAndBehaviorScreenPreview() {
    UISDKShowcaseTheme {
        ComponentAndBehaviorScreen()
    }
}
