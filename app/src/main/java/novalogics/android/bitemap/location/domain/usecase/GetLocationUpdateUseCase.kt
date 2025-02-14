package novalogics.android.bitemap.location.domain.usecase

import com.google.android.gms.maps.model.LatLng
import novalogics.android.bitemap.location.domain.repository.LocationRepository
import javax.inject.Inject

class GetLocationUpdateUseCase @Inject constructor(
    private val locationRepository: LocationRepository
){
    operator fun invoke(destination: LatLng) = locationRepository.getLocation(destination)
}
