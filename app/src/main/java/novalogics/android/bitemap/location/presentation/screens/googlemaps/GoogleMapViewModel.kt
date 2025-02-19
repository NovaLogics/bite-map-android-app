package novalogics.android.bitemap.location.presentation.screens.googlemaps

import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import novalogics.android.bitemap.core.base.BaseViewModel
import novalogics.android.bitemap.core.navigation.events.UiEvent
import novalogics.android.bitemap.location.domain.model.PlaceDetails
import novalogics.android.bitemap.location.domain.usecase.GetDirectionUseCase
import novalogics.android.bitemap.location.domain.usecase.GetLocationUpdateUseCase
import novalogics.android.bitemap.location.domain.usecase.InsertPlacesToDbUseCase
import javax.inject.Inject

@HiltViewModel
class GoogleMapViewModel @Inject constructor(
    private val getLocationUpdateUseCase: GetLocationUpdateUseCase,
    private val getDirectionUseCase: GetDirectionUseCase,
    private val insertPlacesToDbUseCase: InsertPlacesToDbUseCase,
) : BaseViewModel<GoogleMapContract.Intent, GoogleMapContract.GoogleMapUiState, GoogleMapContract.Effect>(
    GoogleMapContract.GoogleMapUiState()
) {

    override fun handleIntent(intent: GoogleMapContract.Intent) {
        when (intent) {
            is GoogleMapContract.Intent.GetLocationUpdates -> getLocationUpdates(intent.destination)
            is GoogleMapContract.Intent.InsertPlaceDetails -> insertPlaceDetails(intent.placeDetails)
            is GoogleMapContract.Intent.GetDirections-> getDirections(intent.start, intent.destination)
        }
    }

    private fun getDirections(start: LatLng, destination: LatLng) {
        viewModelScope.launch {
            getDirectionUseCase(start, destination).collectLatest {
                when (it) {
                    is UiEvent.Loading -> {
                        updateState { copy(isLoading= true) }
                    }
                    is UiEvent.Error -> {
                        updateState { copy(isLoading= false, error = it.message) }
                    }
                    is UiEvent.Success -> {
                        updateState { copy(isLoading= false, routePoints = it.data!!) }
                    }
                }
            }
        }
    }

    private fun getLocationUpdates(destination: LatLng) {
        updateState { copy(isLoading= true) }
        viewModelScope.launch {
            getLocationUpdateUseCase(destination).collectLatest {
                updateState { copy(isLoading= false, currentLocation = it) }
            }
        }
    }

    private fun insertPlaceDetails(placeDetails: PlaceDetails) = viewModelScope.launch {
        insertPlacesToDbUseCase(placeDetails)
    }
}
