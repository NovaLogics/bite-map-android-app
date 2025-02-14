package novalogics.android.bitemap.common.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import novalogics.android.bitemap.location.domain.model.LatLngTypeConverters
import novalogics.android.bitemap.location.domain.model.PlaceDetails
import novalogics.android.bitemap.location.domain.room.LocationDao

@Database(entities = [PlaceDetails::class], version = 1, exportSchema = false)
@TypeConverters(LatLngTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    companion object{
        fun getInstance(context: Context): AppDatabase {
            return Room
                .databaseBuilder(context, AppDatabase::class.java, "database")
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun getLocationDao() : LocationDao
}
