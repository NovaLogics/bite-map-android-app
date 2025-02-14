package novalogics.android.bitemap.location.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import novalogics.android.bitemap.common.navigation.FeatureNavigationApi
import novalogics.android.bitemap.common.navigation.LocationRoute
import novalogics.android.bitemap.common.navigation.NavigationRoute
import novalogics.android.bitemap.location.domain.model.PlaceDetails
import novalogics.android.bitemap.location.presentation.screens.googlemaps.GoogleMapScreen
import novalogics.android.bitemap.location.presentation.screens.places.RestaurantFinderScreen

object LocationNavigationGraph : FeatureNavigationApi {

    private const val KEY_PLACE = "place"

    override fun registerNavigationGraph(
        navController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.navigation(
            startDestination = LocationRoute.PLACES_SEARCH.route,
            route = NavigationRoute.LOCATION.route
        ) {
            composable(route = LocationRoute.PLACES_SEARCH.route) {
                val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
                RestaurantFinderScreen(navHostController = navController) { place ->
                    savedStateHandle?.set(KEY_PLACE, place)
                }
            }
            composable(route = LocationRoute.GOOGLE_MAPS.route) {
                val place = navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.get<PlaceDetails>(KEY_PLACE)

                if (place != null) {
                    GoogleMapScreen(navHostController = navController, place = place)
                } else {
                    navController.popBackStack()
                }
            }
        }
    }
}
