package novalogics.android.bitemap.dashboard.presentation.screens

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import novalogics.android.bitemap.location.domain.usecase.GetAllPlacesFromDbUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllPlacesFromDbUseCase: GetAllPlacesFromDbUseCase
):ViewModel() {
    val list = getAllPlacesFromDbUseCase()
}
