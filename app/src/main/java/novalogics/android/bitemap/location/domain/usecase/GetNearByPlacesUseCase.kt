package novalogics.android.bitemap.location.domain.usecase

import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import novalogics.android.bitemap.core.navigation.events.UiEvent
import novalogics.android.bitemap.core.network.MapsApiService
import novalogics.android.bitemap.dashboard.data.model.PlacesResponse
import novalogics.android.bitemap.location.domain.model.DirectionDetails
import novalogics.android.bitemap.location.domain.repository.LocationRepository
import retrofit2.http.Query
import javax.inject.Inject

class GetNearByPlacesUseCase @Inject constructor(
    private val mapsApiService: MapsApiService
){
    operator fun invoke(
        location: String,
        radius: Int,
        type: String = "restaurant",
        apiKey: String
    ) = flow<UiEvent<PlacesResponse>> {
        emit(UiEvent.Loading())
        emit(UiEvent.Success(data = mapsApiService.getNearbyRestaurants(location,radius,type, apiKey)))
    }.catch {
        emit(UiEvent.Error(message = it.message.toString()))
    }.flowOn(Dispatchers.IO)
}
