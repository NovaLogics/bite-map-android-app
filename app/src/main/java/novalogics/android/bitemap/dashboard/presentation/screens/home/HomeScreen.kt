package novalogics.android.bitemap.dashboard.presentation.screens.home


import android.Manifest
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.LatLng
import novalogics.android.bitemap.R
import novalogics.android.bitemap.app.ui.theme.BiteMapTheme
import novalogics.android.bitemap.core.navigation.DashboardRoute
import novalogics.android.bitemap.core.navigation.LocationRoute
import novalogics.android.bitemap.dashboard.data.model.Geometry
import novalogics.android.bitemap.dashboard.data.model.Location
import novalogics.android.bitemap.dashboard.data.model.Place
import novalogics.android.bitemap.dashboard.presentation.screens.home.component.LocationListItem
import novalogics.android.bitemap.dashboard.presentation.screens.home.component.RestaurantItem
import novalogics.android.bitemap.location.domain.model.PlaceDetails

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>(),
    onNavigateToMaps: (PlaceDetails) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    PermissionHandler(viewModel = viewModel, navController = navHostController)

    HandleSideEffects(viewModel = viewModel, onNavigateToMaps = onNavigateToMaps)

    ScreenUiContent(
        uiState = uiState,
        onSearch = {
            navHostController.navigate(LocationRoute.PLACES_SEARCH.route)
        },
        onRestaurantItemClick = { restaurant ->
            viewModel.handleIntent(HomeContract.Intent.OnItemClick(restaurant))
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenUiContent(
    uiState: HomeContract.HomeUiState,
    onRestaurantItemClick: (PlaceDetails) -> Unit,
    onSearch: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.displayMedium
                    )
                },
                actions = {
                    IconButton(onClick = onSearch) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(id = R.string.search_icon_description)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        when {
            // Show loading indicator
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                ) {
                    CircularProgressIndicator()
                }
            }

            // Show error message
            uiState.error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                ) {
                    Card(
                        modifier = Modifier.padding(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Text(
                            text = uiState.error,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }

            // Show content
            else -> {
                Column(
                    modifier = Modifier.fillMaxSize().padding(innerPadding)
                ) {
                    if (uiState.visitedRestaurants.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))

                        SectionTitle(text = stringResource(id = R.string.recently_visited_restaurants))

                        LazyColumn(modifier = Modifier.heightIn(min = 100.dp, max = 300.dp)) {
                            items(uiState.visitedRestaurants) { restaurant ->
                                LocationListItem(restaurant)
                            }
                        }
                    }

                    SectionTitle(text = stringResource(id = R.string.nearby_restaurants))
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(uiState.nearbyRestaurants) { restaurant ->
                            RestaurantItem(
                                restaurant,
                                onClick = { place ->
                                    onRestaurantItemClick(
                                        PlaceDetails(
                                            "",
                                            place.name,
                                            LatLng(
                                                place.geometry?.location?.lat ?: 0.0,
                                                place.geometry?.location?.lng ?: 0.0
                                            ),
                                            LatLng(
                                                uiState.currentLocation?.lat ?: 0.0,
                                                uiState.currentLocation?.lng ?: 0.0
                                            ),
                                            false,
                                            place.rating.toFloat()
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.displayMedium.copy(
            fontSize = 18.sp
        ),
        fontWeight = FontWeight.W400,
        modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
    )
    Spacer(modifier = Modifier.height(4.dp))
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionHandler(
    viewModel: HomeViewModel,
    navController: NavHostController,
    permissionList: List<String> = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
) {
    val permissionState = rememberMultiplePermissionsState(permissions = permissionList)

    LaunchedEffect(Unit) {
        if (permissionState.allPermissionsGranted) {
            viewModel.handleIntent(HomeContract.Intent.LoadVisitedRestaurants)
            viewModel.handleIntent(HomeContract.Intent.LoadNearbyRestaurants)
        } else {
            navController.navigate(DashboardRoute.PERMISSION_SCREEN.route)
        }
    }
}

@Composable
fun HandleSideEffects(
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

@Preview(
    name = "Light Mode",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun HomeScreenPreview() {

    val uiState = HomeContract.HomeUiState(
        isLoading = false,
        currentLocation = Location(lat = 0.0, lng = 0.0),
        error = null,
        nearbyRestaurants = listOf(
            Place(
                name= "Place Name",
                vicinity= "Address 123",
                rating = 3.4,
                geometry= Geometry(Location(lat = 0.0, lng = 0.0)),
            )
        ),
        visitedRestaurants = listOf(
            PlaceDetails(
                "",
                "Place Name",
                LatLng(0.0, 0.0),
                LatLng(0.0, 0.0),
                true,
                4.5F
            )
        )
    )
    BiteMapTheme {
        ScreenUiContent(
            uiState = uiState,
            onSearch = {},
            onRestaurantItemClick = {}
        )
    }
}
