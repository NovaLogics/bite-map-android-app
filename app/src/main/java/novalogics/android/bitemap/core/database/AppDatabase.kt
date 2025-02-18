package novalogics.android.bitemap.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import novalogics.android.bitemap.location.domain.model.LatLngTypeConverters
import novalogics.android.bitemap.location.domain.model.PlaceDetails
import novalogics.android.bitemap.location.domain.room.LocationDao

const val DATABASE_NAME = "app_database"

@Database(
    entities = [PlaceDetails::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(LatLngTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }

    abstract fun getLocationDao() : LocationDao
}
