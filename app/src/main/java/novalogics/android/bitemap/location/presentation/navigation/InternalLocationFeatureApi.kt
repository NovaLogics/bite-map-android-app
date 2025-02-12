package novalogics.android.bitemap.location.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import novalogics.android.bitemap.common.navigation.FeatureApi
import novalogics.android.bitemap.common.navigation.LocationRoutes
import novalogics.android.bitemap.common.navigation.NavigationRoutes
import novalogics.android.bitemap.location.presentation.screens.googlemaps.GoogleMapScreen
import novalogics.android.bitemap.location.presentation.screens.places.RestaurantFinderScreen

object InternalLocationFeatureApi : novalogics.android.bitemap.common.navigation.FeatureApi {
    override fun registerGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.navigation(
            startDestination = novalogics.android.bitemap.common.navigation.LocationRoutes.PLACES_SEARCH.route,
            route = novalogics.android.bitemap.common.navigation.NavigationRoutes.LOCATION.route
        ){

            composable(route = novalogics.android.bitemap.common.navigation.LocationRoutes.PLACES_SEARCH.route) {
                RestaurantFinderScreen()
            }
            composable(route = novalogics.android.bitemap.common.navigation.LocationRoutes.GOOGLE_MAPS.route) {
                GoogleMapScreen()
            }
        }
    }
}
