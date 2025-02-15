package novalogics.android.bitemap.location.presentation.screens.googlemaps

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import novalogics.android.bitemap.core.navigation.LocationRoute
import novalogics.android.bitemap.core.navigation.events.LocationEvent
import novalogics.android.bitemap.core.util.PLACES_API_KEY
import novalogics.android.bitemap.location.domain.model.PlaceDetails

@Composable
fun GoogleMapScreen(
    navHostController: NavHostController,
    viewModel: GoogleMapViewModel = hiltViewModel(),
    place: PlaceDetails
) {
    val currentLocation = viewModel.currentLocation.collectAsState()
    val route = viewModel.routePoints.collectAsState()
    val destination = place.destination

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    when (val locationEvent = currentLocation.value) {
        is LocationEvent.Idle -> {}
        is LocationEvent.LocationInProgress -> {
            locationEvent.location?.let { location ->
                val cameraPosition = rememberCameraPositionState {
                    position = CameraPosition.builder()
                        .zoom(17F)
                        .bearing(location.bearing)
                        .tilt(45F)
                        .target(LatLng(location.latitude, location.longitude))
                        .build()
                }

                LaunchedEffect(key1 = route.value) {
                    scope.launch {
                        cameraPosition.animate(
                            update = CameraUpdateFactory.newCameraPosition(
                                CameraPosition.builder()
                                    .zoom(17F)
                                    .bearing(location.bearing)
                                    .tilt(45F)
                                    .target(LatLng(location.latitude, location.longitude))
                                    .build()
                            ), 1000
                        )
                    }
                }

                LaunchedEffect(key1 = locationEvent) {
                    viewModel.getDirections(
                        start = LatLng(location.latitude, location.longitude),
                        destination = destination,
                        key = PLACES_API_KEY
                    )
                }

                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPosition
                ) {
                    if (route.value.points.isNotEmpty()) {
                        Polyline(points = route.value.points, color = Color.Blue, width = 20F)
                    }
                }
            }
        }
        is LocationEvent.ReachDestination -> {
            viewModel.insertPlaceDetails(place)
            Toast.makeText(context, "Reached the destination", Toast.LENGTH_LONG).show()
            navHostController.popBackStack(LocationRoute.PLACES_SEARCH.route, inclusive = true)
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getLocationUpdates(destination)
    }
}
