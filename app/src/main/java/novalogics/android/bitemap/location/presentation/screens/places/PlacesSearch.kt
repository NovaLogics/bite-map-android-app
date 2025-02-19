package novalogics.android.bitemap.location.presentation.screens.places

import android.Manifest
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.LatLng
import novalogics.android.bitemap.R
import novalogics.android.bitemap.app.ui.theme.BiteMapTheme
import novalogics.android.bitemap.core.navigation.DashboardRoute
import novalogics.android.bitemap.core.navigation.LocationRoute
import novalogics.android.bitemap.core.navigation.events.PlacesResult
import novalogics.android.bitemap.location.domain.model.PlaceDetails

@Composable
fun RestaurantFinderScreen(
    navHostController: NavHostController,
    viewModel: PlacesSearchViewModel = hiltViewModel<PlacesSearchViewModel>(),
    goToGoogleMap: (PlaceDetails) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    PermissionHandler(navController = navHostController)

    ScreenUiContent(
        uiState = uiState,
        navHostController = navHostController,
        updateQuery = { viewModel.handleIntent(PlacesContract.Intent.UpdateQuery(it)) },
        goToGoogleMap = goToGoogleMap,
        fetchDetails = { id, result ->
            viewModel.handleIntent(
                PlacesContract.Intent.FetchDetails(
                    placeId = id,
                    result = result
                )
            )
        }
    )
}

@Composable
fun ScreenUiContent(
    uiState: PlacesContract.PlacesUiState,
    navHostController: NavHostController,
    updateQuery: (String) -> Unit,
    goToGoogleMap: (PlaceDetails) -> Unit,
    fetchDetails: (placeId: String, result: (PlaceDetails) -> Unit) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val (searchCons, listCons) = createRefs()

        TextField(
            value = uiState.searchQuery,
            onValueChange = { updateQuery(it) },
            maxLines = 1,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5F),
                    contentDescription = stringResource(id = R.string.search_icon_description)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(searchCons) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                })

        when (uiState.searchResult) {
            is PlacesResult.Idle -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                        Text(
                            text = stringResource(id = R.string.search_restaurants),
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.displayMedium.copy(
                                fontWeight = FontWeight.Normal,
                                fontSize = 24.sp,
                            ),
                            textAlign = TextAlign.Center,
                            modifier =  Modifier.padding(12.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Image(
                            painter = painterResource(id = R.drawable.bitemap_logo_main),
                            contentDescription = "BiteMap Logo",
                            modifier = Modifier
                                .size(180.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
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
                    items(uiState.searchResult.list) { place->
                        Text(
                            text = place.getFullText(null).toString(),
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                                .clickable {
                                    fetchDetails(place.placeId) {
                                        it.origin = LatLng(
                                            uiState.searchResult.location?.latitude!!,
                                            uiState.searchResult.location.longitude,
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
                        text = uiState.searchResult.message.toString(),
                        color = MaterialTheme.colorScheme.onError,
                        modifier = Modifier.padding(24.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionHandler(
    navController: NavHostController,
    permissionList: List<String> = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
) {
    val permissionState = rememberMultiplePermissionsState(permissions = permissionList)

    LaunchedEffect(Unit) {
        if (!permissionState.allPermissionsGranted) {
            navController.navigate(DashboardRoute.PERMISSION_SCREEN.route)
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
fun PlaceSearchPreview() {

    val uiState = PlacesContract.PlacesUiState(
        isLoading = false,
        searchQuery = "",
        searchResult = PlacesResult.Idle(),
        error = null,
    )
    val context = LocalContext.current
    BiteMapTheme {
        ScreenUiContent(
            uiState = uiState,
            navHostController = NavHostController(context = context),
            fetchDetails = { _, _ ->},
            updateQuery = {},
            goToGoogleMap = {}
        )
    }
}
