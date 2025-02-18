package novalogics.android.bitemap.location.presentation.screens.places

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import novalogics.android.bitemap.core.navigation.events.PlacesResult
import novalogics.android.bitemap.location.domain.model.PlaceDetails
import novalogics.android.bitemap.location.domain.usecase.FetchRestaurantDetailUseCase
import novalogics.android.bitemap.location.domain.usecase.SearchRestaurantUseCase
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class PlacesSearchViewModel @Inject constructor(
    private val searchRestaurantUseCase: SearchRestaurantUseCase,
    private val fetchRestaurantDetailUseCase: FetchRestaurantDetailUseCase,
): ViewModel() {

    private val _search: MutableState<PlacesResult> = mutableStateOf<PlacesResult>(
        PlacesResult.Idle()
    )
    val search: State<PlacesResult> get() = _search

    private val _query: MutableStateFlow<String> = MutableStateFlow("")
    val query: StateFlow<String> get() = _query

    init {
        viewModelScope.launch{
            _query.debounce(1000).collectLatest {query->
                searchRestaurants(query)
            }
        }
    }

    fun updateQuery(query: String){
        _query.value = query
    }

    fun searchRestaurants(query: String) = viewModelScope.launch {
        searchRestaurantUseCase(query = query).collectLatest { places->
            _search.value = places
        }
    }

    fun fetchDetails(placeId:String, result: (PlaceDetails)-> Unit) = viewModelScope.launch {
        fetchRestaurantDetailUseCase(placeId = placeId).collectLatest {
            result.invoke(it)
        }
    }
}
