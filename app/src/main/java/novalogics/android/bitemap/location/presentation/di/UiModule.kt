package novalogics.android.bitemap.location.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import novalogics.android.bitemap.location.presentation.navigation.LocationNavigationApi
import novalogics.android.bitemap.location.presentation.navigation.LocationNavigationApiImpl

@Module
@InstallIn(SingletonComponent::class)
object UiModule {

    @Provides
    fun provideLocationFeatureApi(): LocationNavigationApi = LocationNavigationApiImpl()
}
