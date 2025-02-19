package novalogics.android.bitemap.dashboard.presentation.screens.home

import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import novalogics.android.bitemap.core.base.BaseViewModel
import novalogics.android.bitemap.core.navigation.events.UiEvent
import novalogics.android.bitemap.location.domain.model.PlaceDetails
import novalogics.android.bitemap.location.domain.usecase.GetAllPlacesFromDbUseCase
import novalogics.android.bitemap.dashboard.domain.usecase.GetNearByPlacesUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllPlacesFromDbUseCase: GetAllPlacesFromDbUseCase,
    private val getNearByPlacesUseCase: GetNearByPlacesUseCase
) : BaseViewModel<HomeContract.Intent, HomeContract.HomeUiState, HomeContract.Effect>(
    HomeContract.HomeUiState()
) {
    override fun handleIntent(intent: HomeContract.Intent) {
        when (intent) {
            is HomeContract.Intent.LoadNearbyRestaurants -> {
                loadNearbyRestaurants()
            }

            is HomeContract.Intent.LoadVisitedRestaurants -> {
                loadVisitedRestaurants()
            }

            is HomeContract.Intent.OnItemClick -> {
                handleItemClick(intent.placeDetails)
            }
        }
    }

    private fun loadVisitedRestaurants() {
        viewModelScope.launch {
            try {
                getAllPlacesFromDbUseCase().collect { restaurants ->
                    updateState { copy(visitedRestaurants = restaurants) }
                }
            } catch (exception: Exception) {
                handleException(exception)
            }
        }
    }

    private fun loadNearbyRestaurants() {
        updateState { copy(isLoading = true) }
        viewModelScope.launch {
            try {
                getNearByPlacesUseCase(1000).collectLatest {
                    when (it) {
                        is UiEvent.Loading -> {}
                        is UiEvent.Error -> {
                            updateState { copy(isLoading = false, error = it.message ?: "") }
                        }

                        is UiEvent.Success -> {
                            updateState {
                                copy(
                                    isLoading = false,
                                    nearbyRestaurants = it.data?.results ?: emptyList(),
                                    currentLocation = LatLng(
                                        it.data?.currentLocationMap?.latitude ?: 0.0,
                                        it.data?.currentLocationMap?.longitude ?: 0.0,
                                    )
                                )
                            }
                        }
                    }
                }
            } catch (exception: Exception) {
                handleException(exception)
            }
        }
    }

    private fun handleItemClick(placeDetails: PlaceDetails) {
        viewModelScope.launch {
            sendEffect { HomeContract.Effect.NavigateToMaps(placeDetails) }
        }
    }

    private fun handleException(exception: Exception?) {
        val errorMessage = exception?.message ?: "Unexpected Error"
        updateState { copy(isLoading = false, error = errorMessage) }
    }
}
