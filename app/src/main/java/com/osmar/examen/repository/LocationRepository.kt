package com.osmar.examen.repository


import com.osmar.domain.models.LocationPoint
import com.osmar.web.NetworkResult
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getUserLocation():Flow<LocationPoint>
    suspend fun getUserLocations():Flow<NetworkResult<List<LocationPoint>>>
    suspend fun saveUserLocation(locationPoint: LocationPoint)
}