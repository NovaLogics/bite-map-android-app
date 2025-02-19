package novalogics.android.bitemap.dashboard.presentation.screens.home

import com.google.android.gms.maps.model.LatLng
import novalogics.android.bitemap.core.base.state.ViewIntent
import novalogics.android.bitemap.core.base.state.ViewSideEffect
import novalogics.android.bitemap.core.base.state.ViewState
import novalogics.android.bitemap.dashboard.data.model.LocationMap
import novalogics.android.bitemap.dashboard.data.model.PlaceMap
import novalogics.android.bitemap.location.domain.model.PlaceDetails

class HomeContract {

    data class HomeUiState(
        val isLoading: Boolean = false,
        val nearbyRestaurants: List<PlaceMap> = emptyList(),
        val visitedRestaurants: List<PlaceDetails> = emptyList(),
        var currentLocation: LatLng = LatLng(0.0, 0.0),
        val error: String? = null,
    ) : ViewState

    sealed class Intent : ViewIntent {
        data object LoadNearbyRestaurants : Intent()
        data object LoadVisitedRestaurants : Intent()
        data class OnItemClick(val placeDetails: PlaceDetails) : Intent()
    }

    sealed class Effect : ViewSideEffect {
        data class NavigateToMaps(val placeDetails: PlaceDetails) : Effect()
    }
}
