package com.franciscostanleyvasconceloszelaya.coupons.common.adapters

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.franciscostanleyvasconceloszelaya.coupons.R

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) View.GONE else View.VISIBLE
}

@BindingAdapter("TextColorChange")
fun changeTextColor(text: TextView, couponStatus: Boolean) {
    if (couponStatus) {
        text.setText(R.string.main_text_title)
        text.setTextColor(Color.GRAY)
    } else {
        text.setText(R.string.main_text_create)
        text.setTextColor(Color.BLUE)
    }
}