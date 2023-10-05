package com.guijarro.weatherapp.presentation.viewmodels

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.guijarro.weatherapp.domain.WeatherDomain
import com.guijarro.weatherapp.domain.usecases.GetCityWeatherUseCase
import com.guijarro.weatherapp.domain.usecases.GetLocationWeatherUseCase
import com.guijarro.weatherapp.presentation.navigation.Routes
import com.guijarro.weatherapp.utils.DbConstants.PERMISSION_REQUEST_CODE
import com.guijarro.weatherapp.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    application: Application,
    private val getCityWeatherUseCase: GetCityWeatherUseCase,
    private val getLocationWeatherUseCase: GetLocationWeatherUseCase,
) : ViewModel() {

    private val _application = application
    private val _locationPermissionGranted = MutableLiveData<Boolean>()

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    private val _weather: MutableStateFlow<UIState<WeatherDomain>> =
        MutableStateFlow(UIState.LOADING)
    val weather: StateFlow<UIState<WeatherDomain>> get() = _weather.asStateFlow()

    private val _city: MutableStateFlow<String> = MutableStateFlow("")
    val city: StateFlow<String> get() = _city.asStateFlow()

    fun onSearchWeatherTap(navHost: NavHostController) {
        getWeatherCity()
        navigateToWeatherDetailsPage(navHost)
    }

    fun onSearchWeatherByLocationTap(navHost: NavHostController, context: Activity) {
        if (checkLocationPermission()) {
            fetchUserLocation(navHost)
        } else {
            requestLocationPermission(context)
        }
    }

    private fun navigateToWeatherDetailsPage(navHost: NavHostController) {
        navHost.navigate(route = Routes.WeatherDetails.route)
    }

    fun setCity(cityName: String) = viewModelScope.launch {
        _city.value = cityName
    }

    fun getWeatherCity() = viewModelScope.launch {
        getCityWeatherUseCase.invoke(city = _city.value).collect {
            _weather.value = it
        }
    }

    private fun getWeatherLocation(lat: Double, lon: Double) = viewModelScope.launch {
        getLocationWeatherUseCase.invoke(lat = lat, lon = lon).collect {
            _weather.value = it
        }
    }

    @SuppressLint("MissingPermission")
    fun fetchUserLocation(navHostController: NavHostController) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    getWeatherLocation(location.latitude, location.longitude)
                }
                navigateToWeatherDetailsPage(navHostController)
            }
            .addOnFailureListener { e ->
                // Handle location retrieval failure
            }
    }

    private fun checkLocationPermission(): Boolean {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        val granted = PackageManager.PERMISSION_GRANTED
        val application = _application

        val hasPermission = ActivityCompat.checkSelfPermission(application, permission) == granted
        _locationPermissionGranted.value = hasPermission

        return hasPermission
    }

    private fun requestLocationPermission(context: Activity) {

        ActivityCompat.requestPermissions(
            context,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_REQUEST_CODE
        )
    }
}