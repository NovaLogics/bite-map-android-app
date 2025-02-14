package novalogics.android.bitemap.location.domain.usecase

import novalogics.android.bitemap.location.domain.room.LocationDao
import javax.inject.Inject

class GetAllPlacesFromDbUseCase @Inject constructor(
    private val locationDao: LocationDao
){
    operator fun invoke() = locationDao.getAllPlaceDetails()
}
