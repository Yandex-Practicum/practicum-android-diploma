package ru.practicum.android.diploma.util

import android.view.View
import android.widget.TextView

fun checkIfNotNull(itemString: String?, view: TextView): Boolean {
    return if (itemString != null) {
        view.visibility = View.VISIBLE
        view.text = itemString
        true
    } else {
        view.visibility = View.GONE
        false
    }
}
