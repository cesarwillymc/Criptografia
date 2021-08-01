package com.cesarwillymc.cripto.core.binding

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("app:visible")
fun setVisibilty(view: View, isVisible: Boolean) {
    if (isVisible) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}