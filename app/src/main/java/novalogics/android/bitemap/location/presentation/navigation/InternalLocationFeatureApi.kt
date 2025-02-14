package novalogics.android.bitemap.location.presentation.navigation

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.google.gson.Gson
import novalogics.android.bitemap.common.navigation.FeatureApi
import novalogics.android.bitemap.common.navigation.LocationRoute
import novalogics.android.bitemap.common.navigation.NavigationRoute
import novalogics.android.bitemap.location.domain.model.PlaceDetails
import novalogics.android.bitemap.location.presentation.screens.googlemaps.GoogleMapScreen
import novalogics.android.bitemap.location.presentation.screens.places.RestaurantFinderScreen

object InternalLocationFeatureApi : FeatureApi {
    override fun registerGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        var placeData:PlaceDetails? = null
        navGraphBuilder.navigation(
            startDestination = LocationRoute.PLACES_SEARCH.route,
            route = NavigationRoute.LOCATION.route
        ){

            composable(route = LocationRoute.PLACES_SEARCH.route) {
                RestaurantFinderScreen(navHostController=navHostController){
                    Log.e("LOCE", Gson().toJson(it))
                  //  navHostController?.currentBackStackEntry?.savedStateHandle?.set("place", it)
                    navHostController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("place", it)
                    placeData = it
                }
            }
            composable(route = LocationRoute.GOOGLE_MAPS.route) {
               // val place = navHostController?.currentBackStackEntry?.savedStateHandle?.get<PlaceDetails>("place")
                var place = navHostController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.get<PlaceDetails>("place")
                Log.e("LOCE 1", Gson().toJson(place))
                place=  placeData
                Log.e("LOCE 11", Gson().toJson(place))
                place?.let {
                    GoogleMapScreen(navHostController = navHostController, place = place)
                }
            }
        }
    }
}
