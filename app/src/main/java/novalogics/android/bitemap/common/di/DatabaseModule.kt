package novalogics.android.bitemap.common.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import novalogics.android.bitemap.common.database.AppDatabase
import novalogics.android.bitemap.location.domain.room.LocationDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideLocationDao(
        appDatabase: AppDatabase,
    ): LocationDao {
        return appDatabase.getLocationDao()
    }
}
