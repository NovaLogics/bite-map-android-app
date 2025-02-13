package novalogics.android.bitemap.location.domain.usecase

import novalogics.android.bitemap.location.domain.repository.LocationRepository
import javax.inject.Inject

class SearchRestaurantUseCase @Inject constructor(
    private val locationRepository: LocationRepository
){
    operator fun invoke (query: String) = locationRepository.searchRestaurants(query = query)
}
