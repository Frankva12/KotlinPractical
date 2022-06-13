package com.franciscostanleyvasconceloszelaya.weather.mainModule.view.adapters

import com.franciscostanleyvasconceloszelaya.weather.common.entities.Forecast

interface OnClickListener {
    fun onClick(forecast: Forecast)
}