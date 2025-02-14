package novalogics.android.bitemap.app.navigation

import novalogics.android.bitemap.dashboard.presentation.navigation.DashboardApi
import novalogics.android.bitemap.location.presentation.navigation.LocationFeatureApi

data class NavigationProvider (
    val dashboardApi: DashboardApi,
    val locationFeatureApi: LocationFeatureApi
)
