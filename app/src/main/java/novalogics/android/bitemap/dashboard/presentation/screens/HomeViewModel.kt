package novalogics.android.bitemap.dashboard.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import novalogics.android.bitemap.core.navigation.events.UiEvent
import novalogics.android.bitemap.core.network.MapsApiService
import novalogics.android.bitemap.dashboard.data.model.Place
import novalogics.android.bitemap.location.domain.usecase.GetAllPlacesFromDbUseCase
import novalogics.android.bitemap.location.domain.usecase.GetNearByPlacesUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllPlacesFromDbUseCase: GetAllPlacesFromDbUseCase,
    private val getNearByPlacesUseCase: GetNearByPlacesUseCase
):ViewModel() {
    val list = getAllPlacesFromDbUseCase()

    private val _restaurants = MutableStateFlow<List<Place>>(emptyList())
    val restaurants: StateFlow<List<Place>> get() = _restaurants

    fun fetchNearbyRestaurants(location: String, radius: Int, apiKey: String) {
        viewModelScope.launch {
            try {
                getNearByPlacesUseCase(location, radius,"restaurant", apiKey).collectLatest {
                    when(it){
                        is UiEvent.Loading -> {}
                        is UiEvent.Error -> {

                        }
                        is UiEvent.Success -> {
                            _restaurants.value = it.data?.results ?: emptyList()
                        }
                    }

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
