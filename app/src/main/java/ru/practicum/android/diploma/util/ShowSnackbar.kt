package ru.practicum.android.diploma.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun showSnackbar(view: View, message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(view, message, duration).show()
}
