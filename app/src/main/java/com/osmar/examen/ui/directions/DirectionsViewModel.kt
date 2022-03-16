package com.osmar.examen.ui.directions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osmar.domain.models.LocationPoint
import com.osmar.examen.repository.LocationRepository
import com.osmar.web.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DirectionsViewModel @Inject constructor(
    val locationRepository: LocationRepository
):ViewModel(){
    private val _locations = MutableLiveData<NetworkResult<List<LocationPoint>>>()

    val locations:LiveData<NetworkResult<List<LocationPoint>>> = _locations

    /**
     * Obtiene las ubicaciones guardadas del usuairo desde el repositorio
     */
    fun getLocations() = viewModelScope.launch(Dispatchers.IO){
        locationRepository.getUserLocations().collect{
            _locations.postValue(it)
        }
    }
}