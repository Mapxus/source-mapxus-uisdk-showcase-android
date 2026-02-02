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
import com.mapxus.uisdkshowcase.ConfigHolder
import com.mapxus.uisdkshowcase.ui.theme.UISDKShowcaseTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ComponentAndBehaviorScreen(modifier: Modifier = Modifier) {
    var isShoplusButtonVisible by remember { mutableStateOf(ConfigHolder.isShoplusButtonVisible) }
    var isVenueBackButtonVisible by remember { mutableStateOf(ConfigHolder.isVenueBackButtonVisible) }
    var shareDisplayMode by remember { mutableStateOf(ConfigHolder.shareDisplayMode) }

    LaunchedEffect(isShoplusButtonVisible) {
        ConfigHolder.isShoplusButtonVisible = isShoplusButtonVisible
    }

    LaunchedEffect(isVenueBackButtonVisible) {
        ConfigHolder.isVenueBackButtonVisible = isVenueBackButtonVisible
    }

    LaunchedEffect(shareDisplayMode) {
        ConfigHolder.shareDisplayMode = shareDisplayMode
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
    }
}

@Preview(showBackground = true)
@Composable
fun ComponentAndBehaviorScreenPreview() {
    UISDKShowcaseTheme {
        ComponentAndBehaviorScreen()
    }
}
