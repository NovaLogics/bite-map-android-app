package novalogics.android.bitemap.location.domain.usecase

import novalogics.android.bitemap.location.domain.repository.LocationRepository
import javax.inject.Inject

class FetchRestaurantDetailUseCase @Inject constructor(
    private val locationRepository: LocationRepository
){
    operator fun invoke(placeId:String) = locationRepository.fetchPlace(placeId = placeId)
}
