package novalogics.android.bitemap.location.presentation.screens.googlemaps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import novalogics.android.bitemap.core.navigation.events.LocationEvent
import novalogics.android.bitemap.core.navigation.events.UiEvent
import novalogics.android.bitemap.location.domain.model.DirectionDetails
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
) : ViewModel() {

    private val _currentLocation : MutableStateFlow<LocationEvent> = MutableStateFlow(LocationEvent.Idle())
    val currentLocation: StateFlow<LocationEvent> get() = _currentLocation

    private val _routePoints : MutableStateFlow<DirectionDetails> = MutableStateFlow(DirectionDetails())
    val routePoints: StateFlow<DirectionDetails> get() = _routePoints

    fun getDirections(start:LatLng,destination:LatLng,key:String){
        viewModelScope.launch {
            getDirectionUseCase(start,destination,key).collectLatest {
                when(it){
                   is UiEvent.Loading -> {}
                    is UiEvent.Error -> {

                    }
                    is UiEvent.Success -> {
                        _routePoints.value = it.data!!
                    }
                }

            }
        }
    }

    fun getLocationUpdates(destination:LatLng){
        viewModelScope.launch {
            getLocationUpdateUseCase(destination).collectLatest {
            _currentLocation.value = it
            }
        }
    }

    fun insertPlaceDetails(placeDetails: PlaceDetails) = viewModelScope.launch {
        insertPlacesToDbUseCase(placeDetails)
    }
}
