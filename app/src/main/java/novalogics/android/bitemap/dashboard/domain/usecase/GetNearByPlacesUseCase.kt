package novalogics.android.bitemap.dashboard.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import novalogics.android.bitemap.core.navigation.events.UiEvent
import novalogics.android.bitemap.dashboard.data.model.PlacesResponse
import novalogics.android.bitemap.dashboard.domain.repository.MapsRepository
import javax.inject.Inject

class GetNearByPlacesUseCase @Inject constructor(
    private val mapsRepository: MapsRepository
){
    operator fun invoke(
        radius: Int,
    ) = flow<UiEvent<PlacesResponse>> {
        emit(UiEvent.Loading())
        emit(UiEvent.Success(data = mapsRepository.fetchNearRestaurants(radius)))
    }.catch {
        emit(UiEvent.Error(message = it.message.toString()))
    }.flowOn(Dispatchers.IO)
}
