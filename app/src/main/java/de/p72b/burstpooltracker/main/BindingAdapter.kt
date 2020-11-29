package de.p72b.burstpooltracker.main

import android.widget.TextView
import androidx.databinding.BindingAdapter


@BindingAdapter("doubleToString")
fun TextView.doubleToString(value: Double?) {
    value?.let {
        text = "$value"
    }
}
