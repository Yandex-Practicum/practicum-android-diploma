package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.domain.models.VacanciesPreview

sealed class RecyclerViewItem {
    data class VacancyItem(val vacancy: VacanciesPreview) : RecyclerViewItem()
    object LoadingItem : RecyclerViewItem()
}
