package com.mapxus.uisdkshowcase.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mapxus.dropin.uicore.navi.BuildingDetailRoute
import com.mapxus.dropin.uicore.navi.EventDetailRoute
import com.mapxus.dropin.uicore.navi.LandingPageRoute
import com.mapxus.dropin.uicore.navi.NavigationRoute
import com.mapxus.dropin.uicore.navi.PoiDetailRoute
import com.mapxus.dropin.uicore.navi.VenueDetailRoute
import com.mapxus.uisdkshowcase.ConfigHolder
import com.mapxus.uisdkshowcase.ui.theme.UISDKShowcaseTheme

private val optionList = buildMap {
    put("Landing Page", LandingPageRoute())
    put("Venue Page", VenueDetailRoute(""))
    put("Building Page", BuildingDetailRoute(""))
    put("POI Page", PoiDetailRoute(""))
    put("Event Page", EventDetailRoute(""))
    put("Navigation Page", NavigationRoute())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutePathsScreen(modifier: Modifier = Modifier) {
    val options = optionList.keys.toList()
    var expanded by remember { mutableStateOf(false) }
    val textFieldState =
        rememberTextFieldState(
            optionList.entries.associate { it.value to it.key }[ConfigHolder.appRoute] ?: options[0]
        )

    LaunchedEffect(textFieldState.text) {
        optionList[textFieldState.text]?.let {
            ConfigHolder.appRoute = it
        }
    }

    Column(modifier = modifier) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
                state = textFieldState,
                readOnly = true,
                lineLimits = TextFieldLineLimits.SingleLine,
                label = { Text("Route Paths") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option, style = MaterialTheme.typography.bodyLarge) },
                        onClick = {
                            textFieldState.setTextAndPlaceCursorAtEnd(option)
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun RoutePathsScreenPreview() {
    UISDKShowcaseTheme {
        RoutePathsScreen()
    }
}