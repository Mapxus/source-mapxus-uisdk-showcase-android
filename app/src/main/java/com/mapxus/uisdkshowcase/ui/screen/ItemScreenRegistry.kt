package com.mapxus.uisdkshowcase.ui.screen

import androidx.compose.runtime.Composable
import com.mapxus.uisdkshowcase.model.item.AppearanceMode
import com.mapxus.uisdkshowcase.model.item.AttributionConfig
import com.mapxus.uisdkshowcase.model.item.BuildingSectionTitle
import com.mapxus.uisdkshowcase.model.item.CategoryListConfig
import com.mapxus.uisdkshowcase.model.item.CloseButtonTitle
import com.mapxus.uisdkshowcase.model.item.Colors
import com.mapxus.uisdkshowcase.model.item.CopyrightConfig
import com.mapxus.uisdkshowcase.model.item.EventModuleTitle
import com.mapxus.uisdkshowcase.model.item.EventOverviewTitle
import com.mapxus.uisdkshowcase.model.item.FilterChipsConfig
import com.mapxus.uisdkshowcase.model.item.FixedDisplayCategories
import com.mapxus.uisdkshowcase.model.item.FloorSelectorCategories
import com.mapxus.uisdkshowcase.model.item.GlobalFilterModes
import com.mapxus.uisdkshowcase.model.item.GlobalFilterTagIds
import com.mapxus.uisdkshowcase.model.item.GpsModeDisplayName
import com.mapxus.uisdkshowcase.model.item.InitialBounds
import com.mapxus.uisdkshowcase.model.item.InitialMapBearing
import com.mapxus.uisdkshowcase.model.item.IsBuildingListVisible
import com.mapxus.uisdkshowcase.model.item.IsLegalLinksVisible
import com.mapxus.uisdkshowcase.model.item.Item
import com.mapxus.uisdkshowcase.model.item.Language
import com.mapxus.uisdkshowcase.model.item.MapBoundsRestriction
import com.mapxus.uisdkshowcase.model.item.MapStyle
import com.mapxus.uisdkshowcase.model.item.MaterialResourcePath
import com.mapxus.uisdkshowcase.model.item.MaximumRoutePlanningDistance
import com.mapxus.uisdkshowcase.model.item.NavigationModes
import com.mapxus.uisdkshowcase.model.item.NavigationRoadSnapStrength
import com.mapxus.uisdkshowcase.model.item.NoRouteAvailableMessage
import com.mapxus.uisdkshowcase.model.item.NoRouteAvailableTitle
import com.mapxus.uisdkshowcase.model.item.PoiDetailSections
import com.mapxus.uisdkshowcase.model.item.PoiSorting
import com.mapxus.uisdkshowcase.model.item.PublicHolidayDisplayName
import com.mapxus.uisdkshowcase.model.item.PublicTransportModes
import com.mapxus.uisdkshowcase.model.item.RecommendedCategories
import com.mapxus.uisdkshowcase.model.item.RecommendedPoiIds
import com.mapxus.uisdkshowcase.model.item.SendLogButtonTitle
import com.mapxus.uisdkshowcase.model.item.SettingsLanguageOptions
import com.mapxus.uisdkshowcase.model.item.Shapes
import com.mapxus.uisdkshowcase.model.item.SharedFloorsUnifiedNames
import com.mapxus.uisdkshowcase.model.item.ToolTipsConfig
import com.mapxus.uisdkshowcase.model.item.VenueAnchorPoiConfigs
import com.mapxus.uisdkshowcase.model.item.VenueDefaultSharedFloorIds
import com.mapxus.uisdkshowcase.model.item.VenueHighlightedShopTitle

@Composable
fun Item.Screen(onBack: () -> Unit) {
    when (this) {
        InitialBounds -> InitialBoundsScreen(this, onBack = onBack)
        MapBoundsRestriction -> MapBoundsRestrictionScreen(this, onBack = onBack)
        InitialMapBearing -> InitialMapBearingScreen(this, onBack = onBack)
        MapStyle -> MapStyleScreen(this, onBack = onBack)
        AppearanceMode -> AppearanceModeScreen(this, onBack = onBack)
        Colors -> ColorsScreen(this, onBack = onBack)
        Shapes -> ShapesScreen(this, onBack = onBack)
        MaterialResourcePath -> MaterialResourcePathScreen(this, onBack = onBack)

        // POI Configuration
        FixedDisplayCategories -> FixedDisplayCategoriesScreen(this, onBack = onBack)
        PoiDetailSections -> PoiDetailSectionsScreen(this, onBack = onBack)
        RecommendedCategories -> RecommendedCategoriesScreen(this, onBack = onBack)
        RecommendedPoiIds -> RecommendedPoiIdsScreen(this, onBack = onBack)
        PoiSorting -> PoiSortingScreen(this, onBack = onBack)
        CategoryListConfig -> CategoryListConfigScreen(this, onBack = onBack)

        // Venue Configuration
        IsBuildingListVisible -> IsBuildingListVisibleScreen(this, onBack = onBack)
        BuildingSectionTitle -> BuildingSectionTitleScreen(this, onBack = onBack)
        VenueHighlightedShopTitle -> VenueHighlightedShopTitleScreen(this, onBack = onBack)
        FloorSelectorCategories -> FloorSelectorCategoriesScreen(this, onBack = onBack)
        VenueDefaultSharedFloorIds -> VenueDefaultSharedFloorIdsScreen(this, onBack = onBack)
        SharedFloorsUnifiedNames -> SharedFloorsUnifiedNamesScreen(this, onBack = onBack)
        VenueAnchorPoiConfigs -> VenueAnchorPoiConfigsScreen(this, onBack = onBack)

        // Navigation Configuration
        NavigationModes -> NavigationModesScreen(this, onBack = onBack)
        PublicTransportModes -> PublicTransportModesScreen(this, onBack = onBack)
        MaximumRoutePlanningDistance -> MaximumRoutePlanningDistanceScreen(this, onBack = onBack)
        NavigationRoadSnapStrength -> NavigationRoadSnapStrengthScreen(this, onBack = onBack)
        NoRouteAvailableTitle -> NoRouteAvailableTitleScreen(this, onBack = onBack)
        NoRouteAvailableMessage -> NoRouteAvailableMessageScreen(this, onBack = onBack)

        // Language Configuration
        Language -> LanguageScreen(this, onBack = onBack)
        SettingsLanguageOptions -> SettingsLanguageOptionsScreen(this, onBack = onBack)
        PublicHolidayDisplayName -> PublicHolidayDisplayNameScreen(this, onBack = onBack)
        GpsModeDisplayName -> GpsModeDisplayNameScreen(this, onBack = onBack)
        CloseButtonTitle -> CloseButtonTitleScreen(this, onBack = onBack)
        SendLogButtonTitle -> SendLogButtonTitleScreen(this, onBack = onBack)
        EventModuleTitle -> EventModuleTitleScreen(this, onBack = onBack)
        EventOverviewTitle -> EventOverviewTitleScreen(this, onBack = onBack)
        ToolTipsConfig -> ToolTipsConfigScreen(this, onBack = onBack)

        // Search and Filtering Configuration
        GlobalFilterModes -> GlobalFilterModesScreen(this, onBack = onBack)
        GlobalFilterTagIds -> GlobalFilterTagIdsScreen(this, onBack = onBack)
        FilterChipsConfig -> FilterChipsConfigScreen(this, onBack = onBack)

        // Legal and Attribution Configuration
        CopyrightConfig -> CopyrightConfigScreen(this, onBack = onBack)
        AttributionConfig -> AttributionConfigScreen(this, onBack = onBack)
        IsLegalLinksVisible -> IsLegalLinksVisibleScreen(this, onBack = onBack)
    }
}
