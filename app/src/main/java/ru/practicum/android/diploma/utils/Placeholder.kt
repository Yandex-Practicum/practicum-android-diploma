package ru.practicum.android.diploma.utils

import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.view.isVisible

class Placeholder(
    private val placeholderTextView: TextView,
) {

    fun show(@DrawableRes drawableResId: Int, @StringRes textResId: Int? = null) {
        with(placeholderTextView) {
            isVisible = true
            setCompoundDrawablesWithIntrinsicBounds(0, drawableResId, 0, 0)

            textResId?.let { setText(it) } ?: run { text = null }
        }
    }

    fun hide() {
        placeholderTextView.isVisible = false
    }
}
