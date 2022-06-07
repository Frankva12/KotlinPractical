package com.franciscostanleyvasconceloszelaya.weather.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.franciscostanleyvasconceloszelaya.weather.R
import com.franciscostanleyvasconceloszelaya.weather.common.entities.WeatherForecastEntity
import com.franciscostanleyvasconceloszelaya.weather.mainModule.model.MainRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository = MainRepository()

    private val result = MutableLiveData<WeatherForecastEntity>()
    fun getResult(): LiveData<WeatherForecastEntity> = result

    private val snackbarMsg = MutableLiveData<Int>()
    fun getSnackbarMsg() = snackbarMsg

    private val loaded = MutableLiveData<Boolean>()
    fun isLoaded() = loaded

    suspend fun getWeatherAndForecast(
        lat: Double,
        long: Double,
        appId: String,
        units: String,
        lang: String
    ) {
        viewModelScope.launch {
            try {
                loaded.value = false
                val resultServer = repository.getWeatherAndForecast(lat, long, appId, units, lang)
                result.value = resultServer
            } catch (e: Exception) {
                snackbarMsg.value = R.string.main_error_server
            } finally {
                loaded.value = true
            }
        }
    }
}