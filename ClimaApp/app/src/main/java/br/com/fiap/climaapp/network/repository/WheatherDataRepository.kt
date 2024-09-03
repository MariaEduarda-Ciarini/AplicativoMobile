package br.com.fiap.climaapp.network.repository

import android.annotation.SuppressLint
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import br.com.fiap.climaapp.data.CurrentLocation
import android.location.Geocoder
import br.com.fiap.climaapp.data.RemoteLocation
import br.com.fiap.climaapp.data.RemoteWeatherData
import br.com.fiap.climaapp.network.repository.api.WeatherAPI
import retrofit2.http.Query

class WeatherDataRepository(private val weatherAPI: WeatherAPI) {

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(
        fusedLocationProviderClient: FusedLocationProviderClient,
        onSuccess: (currentLocation: CurrentLocation) -> Unit,
        onFailure: () -> Unit
    ) {
        fusedLocationProviderClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            CancellationTokenSource().token
        ).addOnSuccessListener { location ->
            location
                ?: return@addOnSuccessListener onFailure() // Corrigido o retorno para onFailure
            onSuccess(
                CurrentLocation(
                    latitude = location.latitude,
                    longitude = location.longitude
                )
            )
        }.addOnFailureListener {
            onFailure()
        }
    }

    @Suppress("DEPRECATION")
    fun updateAddressText(
        currentLocation: CurrentLocation,
        geocoder: Geocoder
    ): CurrentLocation {
        val latitude = currentLocation.latitude ?: return currentLocation
        val longitude = currentLocation.longitude ?: return currentLocation
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)

        return if (!addresses.isNullOrEmpty()) {
            val address = addresses[0]
            val addressText = StringBuilder().apply {
                append(address.locality).append(", ")
                append(address.adminArea).append(", ")
                append(address.countryName)
            }.toString()

            currentLocation.copy(location = addressText)
        } else {
            currentLocation
        }
    }

    suspend fun searchLocation(query: String): List<RemoteLocation>? {
        val response = weatherAPI.searchLocation(query = query)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun getWeatherData(latitude: Double, longitude: Double): RemoteWeatherData? {
        val response = weatherAPI.getWeatherData(query = "$latitude,$longitude")
        return if (response.isSuccessful) response.body() else null
    }
}