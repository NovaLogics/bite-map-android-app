package novalogics.android.bitemap.location.presentation.screens.googlemaps

import com.google.android.gms.maps.model.LatLng
import novalogics.android.bitemap.core.base.state.ViewIntent
import novalogics.android.bitemap.core.base.state.ViewSideEffect
import novalogics.android.bitemap.core.base.state.ViewState
import novalogics.android.bitemap.core.navigation.events.LocationEvent
import novalogics.android.bitemap.location.domain.model.DirectionDetails
import novalogics.android.bitemap.location.domain.model.PlaceDetails

class GoogleMapContract {

    data class GoogleMapUiState(
        val isLoading: Boolean = false,
        val currentLocation: LocationEvent = LocationEvent.Idle(),
        val routePoints: DirectionDetails? = null,
        val error: String? = null,
    ) : ViewState

    sealed class Intent : ViewIntent {
        data class GetLocationUpdates(val destination: LatLng) : Intent()
        data class InsertPlaceDetails(val placeDetails: PlaceDetails): Intent()
        data class GetDirections(val start: LatLng, val destination: LatLng): Intent()
    }

    sealed class Effect : ViewSideEffect {}
}
