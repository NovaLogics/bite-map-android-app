package novalogics.android.bitemap.location.domain.usecase

import novalogics.android.bitemap.location.domain.model.PlaceDetails
import novalogics.android.bitemap.location.domain.room.LocationDao
import javax.inject.Inject

class InsertPlacesToDbUseCase @Inject constructor(
    private val locationDao: LocationDao
){
    suspend operator fun invoke(placeDetails: PlaceDetails) = locationDao.insert(placeDetails)
}
