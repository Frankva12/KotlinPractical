package com.franciscostanleyvasconceloszelaya.snapshots.utils

import android.widget.Toast

interface MainAux {
    fun showMessage(resId: Int, duration: Int = Toast.LENGTH_SHORT)
}