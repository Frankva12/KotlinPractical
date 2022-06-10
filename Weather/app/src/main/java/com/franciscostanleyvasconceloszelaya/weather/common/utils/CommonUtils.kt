package com.franciscostanleyvasconceloszelaya.weather.common.utils

import java.text.SimpleDateFormat
import java.util.*

object CommonUtils {
    fun getHour(epoch: Long): String = getFormatedTime(epoch, "HH:mm")

    private fun getFormatedTime(epoch: Long, pattern: String): String {
        return SimpleDateFormat(pattern, Locale.getDefault()).format(epoch * 1000)
    }
}