package com.mapxus.uisdkshowcase

import com.mapxus.dropin.uicore.api.AppearanceMode
import com.mapxus.dropin.uicore.api.AttributionConfig
import com.mapxus.dropin.uicore.api.CopyrightConfig
import com.mapxus.dropin.uicore.api.DIConfigBuilder
import com.mapxus.dropin.uicore.api.FilterChipsConfig
import com.mapxus.dropin.uicore.api.GlobalFilterMode
import com.mapxus.dropin.uicore.api.Language
import com.mapxus.dropin.uicore.api.NavigationMode
import com.mapxus.dropin.uicore.api.PoiDetailSection
import com.mapxus.dropin.uicore.api.PoiSortingStrategy
import com.mapxus.dropin.uicore.api.PublicTransportMode
import com.mapxus.dropin.uicore.api.ShareDisplayMode
import com.mapxus.dropin.uicore.api.SharedFloorsUnifiedName
import com.mapxus.dropin.uicore.api.ToolTipsConfig
import com.mapxus.dropin.uicore.api.VenueAnchorPoiConfig
import com.mapxus.dropin.uicore.api.model.Bounds
import com.mapxus.dropin.uicore.api.model.StringsWithLanguage
import com.mapxus.dropin.uicore.api.theme.DIColors
import com.mapxus.dropin.uicore.api.theme.DIShapes

object ConfigHolder {
    private val configBuilder = DIConfigBuilder()

    fun getConfig() = configBuilder.build()

    var initialBounds: Bounds?
        get() = configBuilder.initialBounds
        set(value) {
            configBuilder.initialBounds = value
        }

    var mapBoundsRestriction: Bounds?
        get() = configBuilder.mapBoundsRestriction
        set(value) {
            configBuilder.mapBoundsRestriction = value
        }

    var initialMapBearing: Double
        get() = configBuilder.initialMapBearing
        set(value) {
            configBuilder.initialMapBearing = value
        }

    var mapStyle: String
        get() = configBuilder.mapStyle
        set(value) {
            configBuilder.mapStyle = value
        }

    var appearanceMode: AppearanceMode
        get() = configBuilder.appearanceMode
        set(value) {
            configBuilder.appearanceMode = value
        }

    var colors: DIColors?
        get() = configBuilder.colors
        set(value) {
            configBuilder.colors = value
        }

    var shapes: DIShapes
        get() = configBuilder.shapes
        set(value) {
            configBuilder.shapes = value
        }

    var materialResourcePath: String
        get() = configBuilder.materialResourcePath
        set(value) {
            configBuilder.materialResourcePath = value
        }

    var fixedDisplayCategories: List<String>?
        get() = configBuilder.fixedDisplayCategories
        set(value) {
            configBuilder.fixedDisplayCategories = value
        }

    var poiDetailSections: List<PoiDetailSection>
        get() = configBuilder.poiDetailSections
        set(value) {
            configBuilder.poiDetailSections = value
        }

    var recommendedCategories: List<String>?
        get() = configBuilder.recommendedCategories
        set(value) {
            configBuilder.recommendedCategories = value
        }

    var recommendedPoiIds: List<String>?
        get() = configBuilder.recommendedPoiIds
        set(value) {
            configBuilder.recommendedPoiIds = value
        }

    var poiSorting: PoiSortingStrategy
        get() = configBuilder.poiSorting
        set(value) {
            configBuilder.poiSorting = value
        }

    var categoryListConfig: List<String>?
        get() = configBuilder.categoryListConfig
        set(value) {
            configBuilder.categoryListConfig = value
        }

    var isBuildingListVisible: Boolean
        get() = configBuilder.isBuildingListVisible
        set(value) {
            configBuilder.isBuildingListVisible = value
        }

    var buildingSectionTitle: StringsWithLanguage?
        get() = configBuilder.buildingSectionTitle
        set(value) {
            configBuilder.buildingSectionTitle = value
        }

    var venueHighlightedShopTitle: StringsWithLanguage?
        get() = configBuilder.venueHighlightedShopTitle
        set(value) {
            configBuilder.venueHighlightedShopTitle = value
        }

    var floorSelectorCategories: List<String>?
        get() = configBuilder.floorSelectorCategories
        set(value) {
            configBuilder.floorSelectorCategories = value
        }

    var venueDefaultSharedFloorIds: List<String>?
        get() = configBuilder.venueDefaultSharedFloorIds
        set(value) {
            configBuilder.venueDefaultSharedFloorIds = value
        }

    var sharedFloorsUnifiedNames: List<SharedFloorsUnifiedName>?
        get() = configBuilder.sharedFloorsUnifiedNames
        set(value) {
            configBuilder.sharedFloorsUnifiedNames = value
        }

    var venueAnchorPoiConfigs: List<VenueAnchorPoiConfig>?
        get() = configBuilder.venueAnchorPoiConfigs
        set(value) {
            configBuilder.venueAnchorPoiConfigs = value
        }

    // Navigation Configuration
    var navigationModes: List<NavigationMode>
        get() = configBuilder.navigationModes
        set(value) {
            configBuilder.navigationModes = value
        }

    var publicTransportModes: List<PublicTransportMode>
        get() = configBuilder.publicTransportModes
        set(value) {
            configBuilder.publicTransportModes = value
        }

    var maximumRoutePlanningDistance: Double?
        get() = configBuilder.maximumRoutePlanningDistance
        set(value) {
            configBuilder.maximumRoutePlanningDistance = value
        }

    var navigationRoadSnapStrength: Double?
        get() = configBuilder.navigationRoadSnapStrength
        set(value) {
            configBuilder.navigationRoadSnapStrength = value
        }

    var noRouteAvailableTitle: StringsWithLanguage?
        get() = configBuilder.noRouteAvailableTitle
        set(value) {
            configBuilder.noRouteAvailableTitle = value
        }

    var noRouteAvailableMessage: StringsWithLanguage?
        get() = configBuilder.noRouteAvailableMessage
        set(value) {
            configBuilder.noRouteAvailableMessage = value
        }

    // Language Configuration
    var language: Language
        get() = configBuilder.language
        set(value) {
            configBuilder.language = value
        }

    var settingsLanguageOptions: List<Language>?
        get() = configBuilder.settingsLanguageOptions
        set(value) {
            configBuilder.settingsLanguageOptions = value
        }

    var publicHolidayDisplayName: StringsWithLanguage
        get() = configBuilder.publicHolidayDisplayName
        set(value) {
            configBuilder.publicHolidayDisplayName = value
        }

    var gpsModeDisplayName: StringsWithLanguage?
        get() = configBuilder.gpsModeDisplayName
        set(value) {
            configBuilder.gpsModeDisplayName = value
        }

    var closeButtonTitle: StringsWithLanguage?
        get() = configBuilder.closeButtonTitle
        set(value) {
            configBuilder.closeButtonTitle = value
        }

    var sendLogButtonTitle: StringsWithLanguage?
        get() = configBuilder.sendLogButtonTitle
        set(value) {
            configBuilder.sendLogButtonTitle = value
        }

    var eventModuleTitle: StringsWithLanguage?
        get() = configBuilder.eventModuleTitle
        set(value) {
            configBuilder.eventModuleTitle = value
        }

    var eventOverviewTitle: StringsWithLanguage?
        get() = configBuilder.eventOverviewTitle
        set(value) {
            configBuilder.eventOverviewTitle = value
        }

    var toolTipsConfig: ToolTipsConfig?
        get() = configBuilder.toolTipsConfig
        set(value) {
            configBuilder.toolTipsConfig = value
        }

    // Search and Filtering Configuration
    var globalFilterModes: List<GlobalFilterMode>?
        get() = configBuilder.globalFilterModes
        set(value) {
            configBuilder.globalFilterModes = value
        }

    var globalFilterTagIds: List<String>?
        get() = configBuilder.globalFilterTagIds
        set(value) {
            configBuilder.globalFilterTagIds = value
        }

    var filterChipsConfig: FilterChipsConfig?
        get() = configBuilder.filterChipsConfig
        set(value) {
            configBuilder.filterChipsConfig = value
        }

    // Legal and Attribution Configuration
    var copyrightConfig: CopyrightConfig?
        get() = configBuilder.copyrightConfig
        set(value) {
            configBuilder.copyrightConfig = value
        }

    var attributionConfig: AttributionConfig?
        get() = configBuilder.attributionConfig
        set(value) {
            configBuilder.attributionConfig = value
        }

    var isLegalLinksVisible: Boolean
        get() = configBuilder.isLegalLinksVisible
        set(value) {
            configBuilder.isLegalLinksVisible = value
        }

    // Component and Behavior Configuration
    var isShoplusButtonVisible: Boolean
        get() = configBuilder.isShoplusButtonVisible
        set(value) {
            configBuilder.isShoplusButtonVisible = value
        }

    var isVenueBackButtonVisible: Boolean
        get() = configBuilder.isVenueBackButtonVisible
        set(value) {
            configBuilder.isVenueBackButtonVisible = value
        }

    var shareDisplayMode: ShareDisplayMode
        get() = configBuilder.shareDisplayMode
        set(value) {
            configBuilder.shareDisplayMode = value
        }
}
