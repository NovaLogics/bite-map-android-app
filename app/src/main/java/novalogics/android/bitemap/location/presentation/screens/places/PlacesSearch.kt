package novalogics.android.bitemap.location.presentation.screens.places

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.LatLng
import novalogics.android.bitemap.common.navigation.LocationRoute
import novalogics.android.bitemap.common.navigation.events.PlacesResult
import novalogics.android.bitemap.location.domain.model.PlaceDetails

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RestaurantFinderScreen(
    navHostController: NavHostController,
    viewModel: PlacesSearchViewModel = hiltViewModel<PlacesSearchViewModel>(),
    goToGoogleMap:(PlaceDetails) -> Unit
) {
    val permission = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )

    val placesResult = viewModel.search.value
    val query = viewModel.query.collectAsState()
    if (permission.allPermissionsGranted) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            val (searchCons, listCons) = createRefs()

            TextField(
                value = query.value,
                onValueChange = { viewModel.updateQuery(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(searchCons) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    })

            when (placesResult) {
                is PlacesResult.Idle -> {}
                is PlacesResult.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is PlacesResult.Success -> {
                    LazyColumn(modifier = Modifier.constrainAs(listCons) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(searchCons.bottom)
                        bottom.linkTo(parent.bottom)
                        height = Dimension.fillToConstraints
                    }) {
                        items(placesResult.list) {
                            Text(
                                text = it.getFullText(null).toString(),
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp)
                                    .clickable {
                                        viewModel.fetchDetails(it.placeId){
                                            it.origin = LatLng(
                                                placesResult.location?.latitude!!,
                                                placesResult.location?.longitude!!,
                                            )
                                            goToGoogleMap.invoke(it)
                                            navHostController.navigate(LocationRoute.GOOGLE_MAPS.route)
                                        }
                                    }
                            )
                        }
                    }
                }

                is PlacesResult.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = placesResult.message.toString(),
                            color = MaterialTheme.colorScheme.onError,
                            modifier = Modifier.padding(24.dp)
                        )
                    }
                }
            }
        }


    } else {
        LaunchedEffect(key1 = Unit, block = {
            permission.launchMultiplePermissionRequest()
        })
    }
}
