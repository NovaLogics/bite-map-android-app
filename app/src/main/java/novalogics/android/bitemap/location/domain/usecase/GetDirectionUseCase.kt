package novalogics.android.bitemap.location.domain.usecase

import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import novalogics.android.bitemap.core.navigation.events.UiEvent
import novalogics.android.bitemap.location.domain.model.DirectionDetails
import novalogics.android.bitemap.location.domain.repository.LocationRepository
import javax.inject.Inject

class GetDirectionUseCase @Inject constructor(
    private val locationRepository: LocationRepository
){
    operator fun invoke(
        start: LatLng,
        destination: LatLng,
        key: String
    ) = flow<UiEvent<DirectionDetails>> {
        emit(UiEvent.Loading())
        emit(UiEvent.Success(data = locationRepository.getDirection(start,destination,key)))
    }.catch {
        emit(UiEvent.Error(message = it.message.toString()))
    }.flowOn(Dispatchers.IO)
}
