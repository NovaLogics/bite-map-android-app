package novalogics.android.bitemap.location.domain.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import novalogics.android.bitemap.location.domain.model.PlaceDetails

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(placeDetails: PlaceDetails)

    @Query("SELECT * FROM PlaceDetails")
    fun getAllPlaceDetails(): Flow<List<PlaceDetails>>
}
