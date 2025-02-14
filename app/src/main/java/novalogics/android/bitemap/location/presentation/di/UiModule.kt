package novalogics.android.bitemap.location.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import novalogics.android.bitemap.location.presentation.navigation.LocationFeatureApi
import novalogics.android.bitemap.location.presentation.navigation.LocationFeatureApiImpl

@Module
@InstallIn(SingletonComponent::class)
object UiModule {

    @Provides
    fun provideLocationFeatureApi(): LocationFeatureApi = LocationFeatureApiImpl()
}
