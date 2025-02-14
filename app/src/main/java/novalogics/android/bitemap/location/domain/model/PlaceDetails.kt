package novalogics.android.bitemap.location.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class PlaceDetails(
    @PrimaryKey(autoGenerate = false)
    val placeId: String,
    val name: String,
    val destination: LatLng,
    var origin: LatLng,
    val delivery: Boolean,
    val rating: Float,
) : Parcelable

class LatLngTypeConverters{
    @TypeConverter
    fun stringToLatLng(value:String): LatLng = Gson().fromJson(value, LatLng::class.java)

    @TypeConverter
    fun latLngToString(latLng: LatLng): String = Gson().toJson(latLng)
}
