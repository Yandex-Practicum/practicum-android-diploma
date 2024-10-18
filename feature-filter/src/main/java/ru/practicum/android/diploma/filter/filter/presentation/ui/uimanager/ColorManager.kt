package ru.practicum.android.diploma.filter.filter.presentation.ui.uimanager

import android.content.Context
import androidx.core.content.ContextCompat
import java.lang.ref.WeakReference

class ColorManager(private val context: Context) {

    private val contextRef = WeakReference(context)

    fun getColorsForEditText(isEmpty: Boolean): IntArray {
        val context = contextRef.get() ?: return intArrayOf()
        return intArrayOf(
            ContextCompat.getColor(
                context,
                if (isEmpty) {
                    ru.practicum.android.diploma.ui.R.color.hintDefaultTextInputLayout
                } else {
                    ru.practicum.android.diploma.ui.R.color.black
                }
            ),
            ContextCompat.getColor(
                context,
                ru.practicum.android.diploma.ui.R.color.blue
            )
        )
    }

    fun getStatesEditTextFilter(): Array<IntArray> {
        return arrayOf(
            intArrayOf(-android.R.attr.state_focused),
            intArrayOf(android.R.attr.state_focused)
        )
    }
}
