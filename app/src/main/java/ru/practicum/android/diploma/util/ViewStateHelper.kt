package ru.practicum.android.diploma.util

import android.view.View
import androidx.core.view.isVisible

/**
 * Утилита для управления видимостью группы View.
 * Позволяет показывать одну View из списка, скрывая остальные.
 */
class ViewStateHelper(private val views: List<View>) {

    /**
     * Показывает только переданную [viewToShow], остальные View из списка скрывает.
     * Если передать null, будут скрыты все View.
     */
    fun showOnly(viewToShow: View?) {
        views.forEach { view ->
            view.isVisible = view == viewToShow
        }
    }

    /**
     * Скрывает все View, находящиеся под управлением хелпера.
     */
    fun hideAll() {
        views.forEach { it.isVisible = false }
    }
}
