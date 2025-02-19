package novalogics.android.bitemap.location.presentation.screens.places

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import novalogics.android.bitemap.core.base.BaseViewModel
import novalogics.android.bitemap.location.domain.model.PlaceDetails
import novalogics.android.bitemap.location.domain.usecase.FetchRestaurantDetailUseCase
import novalogics.android.bitemap.location.domain.usecase.SearchRestaurantUseCase
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class PlacesSearchViewModel @Inject constructor(
    private val searchRestaurantUseCase: SearchRestaurantUseCase,
    private val fetchRestaurantDetailUseCase: FetchRestaurantDetailUseCase,
) : BaseViewModel<PlacesContract.Intent, PlacesContract.PlacesUiState, PlacesContract.Effect>(
    PlacesContract.PlacesUiState()
) {

    override fun handleIntent(intent: PlacesContract.Intent) {
        when (intent) {
            is PlacesContract.Intent.UpdateQuery -> updateQuery(intent.query)
            is PlacesContract.Intent.SearchRestaurants -> searchRestaurants(intent.query)
            is PlacesContract.Intent.FetchDetails -> fetchDetails(intent.placeId, intent.result)
        }
    }

    init {
        viewModelScope.launch {
            uiState
                .map { it.searchQuery }
                .distinctUntilChanged()
                .debounce(1000)
                .collectLatest { query ->
                    if (query.isNotEmpty()) {
                        searchRestaurants(query)
                    }
                }
        }
    }

    private fun updateQuery(query: String) {
        updateState { copy(searchQuery = query) }
    }

    private fun searchRestaurants(query: String) = viewModelScope.launch {
        searchRestaurantUseCase(query = query).collectLatest { places ->
            updateState { copy(searchResult = places) }
        }
    }

    private fun fetchDetails(placeId: String, result: (PlaceDetails) -> Unit) =
        viewModelScope.launch {
            fetchRestaurantDetailUseCase(placeId = placeId).collectLatest {
                result.invoke(it)
            }
        }
}
