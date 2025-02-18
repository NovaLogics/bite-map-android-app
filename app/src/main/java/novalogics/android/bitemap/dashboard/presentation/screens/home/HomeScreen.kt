package novalogics.android.bitemap.dashboard.presentation.screens.home

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import novalogics.android.bitemap.core.navigation.LocationRoute
import novalogics.android.bitemap.core.network.ApiConfig
import novalogics.android.bitemap.dashboard.data.model.Place
import novalogics.android.bitemap.location.domain.model.PlaceDetails

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>(),
    onNavigateToMaps: (PlaceDetails) -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()

    HandleSideEffects(navHostController,viewModel, onNavigateToMaps)

    val permission = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )

    LaunchedEffect(Unit) {

        if (permission.allPermissionsGranted) {
            viewModel.handleIntent(HomeContract.Intent.LoadNearbyRestaurants)
        } else {
            permission.launchMultiplePermissionRequest()
        }
    }



    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Home Screen") },
            actions = {
                IconButton(onClick = {
                    navHostController.navigate(LocationRoute.PLACES_SEARCH.route)
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "")
                }
            }
        )
    }) { innerPadding ->
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            items(uiState.nearbyRestaurants) { restaurant ->
                RestaurantItem(restaurant, onClick = { place ->

                    viewModel.handleIntent(
                        HomeContract.Intent.OnItemClick(
                            PlaceDetails(
                                "",
                                place.name,
                                LatLng(
                                    place.geometry?.location?.lat ?: 0.0,
                                    place.geometry?.location?.lng ?: 0.0
                                ),
                                LatLng(
                                    uiState.currentLocation?.lat ?: 0.0,
                                    uiState.currentLocation?.lng ?: 0.0,
                                ),
                                false,
                                (place.rating).toFloat(),
                            )
                        )
                    )
                })
            }
        }

//        if(list.value.isEmpty()){
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(MaterialTheme.colorScheme.background),
//                contentAlignment = Alignment.Center,
//            ){
//                Text(
//                    text = "No destination found",
//                    color = MaterialTheme.colorScheme.onPrimary,
//                    modifier = Modifier.padding(24.dp)
//                )
//            }
        //  }
//    else{
//
//            if (permission.allPermissionsGranted) {
//                LazyColumn(modifier = Modifier.fillMaxSize()) {
//                    items(restaurants) { restaurant ->
//                        RestaurantItem(restaurant)
//                    }
//                }
//            }
//            else{
//                LazyColumn {
//                    items(list.value){
//                        LocationListItem(it)
//                    }
//                }
//            }


        //     }

    }
}

//@SuppressLint("MissingPermission")
// fun getLocationOnce(
//    fusedLocationClient: FusedLocationProviderClient,
//    onLocationReceived: (Location) -> Unit
//) {
//    val cancellationTokenSource = CancellationTokenSource()
//
//    val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 100)
//        .setIntervalMillis(1000)
//        .setMaxUpdates(1)
//        .build()
//
//    val locationCallback = object : LocationCallback() {
//        override fun onLocationResult(locationResult: LocationResult) {
//            locationResult.locations[0]?.let { locationData ->
//                if (locationData != null) {
//                    onLocationReceived(locationData)
//                }
//            }
//        }
//    }
//    fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
//}

@Composable
fun HandleSideEffects(
    navHostController: NavHostController,
    viewModel: HomeViewModel,
    onNavigateToMaps: (PlaceDetails) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is HomeContract.Effect.NavigateToMaps -> {
                    onNavigateToMaps(effect.placeDetails)
                }
            }
        }
    }
}

@Composable
fun RestaurantItem(restaurant: Place, onClick: (Place) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(restaurant) },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Restaurant Icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(36.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = restaurant.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location Icon",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = restaurant.vicinity,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating Icon",
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${restaurant.rating}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}


@Composable
fun LocationListItem(it: PlaceDetails) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Text(
            text = "Start: ${it.origin.latitude} , ${it.origin.longitude}",
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Destination: ${it.destination.latitude} , ${it.destination.longitude}",
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Name: ${it.name}",
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Rating: ${it.rating}/5",
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (it.delivery) "Delivery is available" else "Delivery is Not available",
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Spacer(modifier = Modifier.height(8.dp))
    }

}
