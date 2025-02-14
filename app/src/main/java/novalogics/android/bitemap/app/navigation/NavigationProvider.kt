package novalogics.android.bitemap.app.navigation

import novalogics.android.bitemap.dashboard.presentation.navigation.DashboardNavigationApi
import novalogics.android.bitemap.location.presentation.navigation.LocationNavigationApi

data class NavigationProvider (
    val dashboardApi: DashboardNavigationApi,
    val locationFeatureApi: LocationNavigationApi
)
