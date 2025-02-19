package novalogics.android.bitemap.location.presentation.screens.googlemaps

import android.content.Context
import android.location.Location
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import novalogics.android.bitemap.core.navigation.LocationRoute
import novalogics.android.bitemap.core.navigation.events.LocationEvent
import novalogics.android.bitemap.location.domain.model.PlaceDetails

@Composable
fun GoogleMapScreen(
    navHostController: NavHostController,
    viewModel: GoogleMapViewModel = hiltViewModel(),
    place: PlaceDetails,
) {
    val uiState by viewModel.uiState.collectAsState()
    val destination = place.destination
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    HandleBackPress(navHostController)
    viewModel.handleIntent(GoogleMapContract.Intent.InsertPlaceDetails(place))

    HandleLocationEvent(uiState, viewModel, destination, scope, context, navHostController)

    RequestLocationUpdates(viewModel, destination)
}

@Composable
fun HandleBackPress(navHostController: NavHostController) {
    BackHandler {
        navHostController.popBackStack()
    }
}

@Composable
fun HandleLocationEvent(
    uiState: GoogleMapContract.GoogleMapUiState,
    viewModel: GoogleMapViewModel,
    destination: LatLng,
    scope: CoroutineScope,
    context: Context,
    navHostController: NavHostController
) {
    when (val locationEvent = uiState.currentLocation) {
        is LocationEvent.Idle -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {
                CircularProgressIndicator()
            }
        }
        is LocationEvent.LocationInProgress -> {
            locationEvent.location?.let { location ->

                val cameraPosition = rememberCameraPositionState {
                    position = createCameraPosition(location)
                }

                AnimateCameraOnRouteUpdate(uiState, scope, cameraPosition, location)
                FetchDirectionsOnLocationUpdate(viewModel, location, destination)
                RenderGoogleMap(uiState, cameraPosition)
            }
        }
        is LocationEvent.ReachDestination -> {
            HandleDestinationReached(viewModel, context, navHostController)
        }
    }
}

private fun createCameraPosition(location: Location): CameraPosition {
    return CameraPosition.builder()
        .zoom(17F)
        .bearing(location.bearing)
        .tilt(45F)
        .target(LatLng(location.latitude, location.longitude))
        .build()
}

@Composable
fun AnimateCameraOnRouteUpdate(
    uiState: GoogleMapContract.GoogleMapUiState,
    scope: CoroutineScope,
    cameraPosition: CameraPositionState,
    location: Location
) {
    LaunchedEffect(key1 = uiState.routePoints) {
        scope.launch {
            cameraPosition.animate(
                update = CameraUpdateFactory.newCameraPosition(createCameraPosition(location)),
                durationMs = 1000
            )
        }
    }
}

@Composable
fun FetchDirectionsOnLocationUpdate(
    viewModel: GoogleMapViewModel,
    location: Location,
    destination: LatLng
) {
    LaunchedEffect(key1 = location) {
        viewModel.handleIntent(
            GoogleMapContract.Intent.GetDirections(
                start = LatLng(location.latitude, location.longitude),
                destination = destination,
            )
        )
    }
}

@Composable
fun RenderGoogleMap(uiState: GoogleMapContract.GoogleMapUiState, cameraPosition: CameraPositionState) {
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPosition
    ) {
        uiState.routePoints?.points?.takeIf { it.isNotEmpty() }?.let { points ->
            Polyline(points = points, color = Color.Blue, width = 20F)
        }
    }
}

@Composable
fun HandleDestinationReached(
    viewModel: GoogleMapViewModel,
    context: Context,
    navHostController: NavHostController
) {
    //viewModel.handleIntent(GoogleMapContract.Intent.InsertPlaceDetails(place))
    Toast.makeText(context, "Reached the destination", Toast.LENGTH_LONG).show()
    navHostController.popBackStack(LocationRoute.PLACES_SEARCH.route, inclusive = true)
}

@Composable
fun RequestLocationUpdates(viewModel: GoogleMapViewModel, destination: LatLng) {
    LaunchedEffect(key1 = Unit) {
        viewModel.handleIntent(GoogleMapContract.Intent.GetLocationUpdates(destination))
    }
}
