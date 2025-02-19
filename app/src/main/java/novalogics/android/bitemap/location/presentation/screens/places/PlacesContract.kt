package novalogics.android.bitemap.location.presentation.screens.places

import novalogics.android.bitemap.core.base.state.ViewIntent
import novalogics.android.bitemap.core.base.state.ViewSideEffect
import novalogics.android.bitemap.core.base.state.ViewState
import novalogics.android.bitemap.core.navigation.events.PlacesResult
import novalogics.android.bitemap.location.domain.model.PlaceDetails

class PlacesContract {

    data class PlacesUiState(
        val isLoading: Boolean = false,
        val searchResult: PlacesResult = PlacesResult.Idle(),
        val searchQuery: String = "",
        val error: String? = null,
    ) : ViewState

    sealed class Intent : ViewIntent {
        data class UpdateQuery(val query: String) : Intent()
        data class SearchRestaurants(val query: String) : Intent()
        data class FetchDetails(val placeId: String, val result: (PlaceDetails) -> Unit) : Intent()
    }

    sealed class Effect : ViewSideEffect {
        data class NavigateToMaps(val placeDetails: PlaceDetails) : Effect()
    }
}
