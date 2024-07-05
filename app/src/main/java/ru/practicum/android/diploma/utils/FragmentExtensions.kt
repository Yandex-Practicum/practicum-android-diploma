package ru.practicum.android.diploma.utils

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ru.practicum.android.diploma.R

// Snackbar как альтернатива Toast

fun Fragment.showSnackbar(message: String) {
    val snackbar = Snackbar.make(
        requireView(),
        message,
        Snackbar.LENGTH_SHORT
    )
    val snackbarView = snackbar.view
    snackbarView.setBackgroundResource(R.color.black_and_white)
    snackbar.show()
}
