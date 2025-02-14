package novalogics.android.bitemap.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import novalogics.android.bitemap.navigation.NavigationProvider
import novalogics.android.bitemap.dashboard.presentation.navigation.DashboardApi
import novalogics.android.bitemap.database.AppDatabase
import novalogics.android.bitemap.location.domain.room.LocationDao
import novalogics.android.bitemap.location.presentation.navigation.LocationFeatureApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNavigationProvider(
        dashboardApi: DashboardApi,
        locationFeatureApi: LocationFeatureApi
    ): NavigationProvider =
        NavigationProvider(
            dashboardApi = dashboardApi,
            locationFeatureApi = locationFeatureApi,
        )

    @Singleton
    @Provides
    fun provideAppDatabase(
      @ApplicationContext  context: Context,
    ):AppDatabase{
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideLocationDao(
        appDatabase: AppDatabase,
    ): LocationDao {
        return appDatabase.getLocationDao()
    }
}
