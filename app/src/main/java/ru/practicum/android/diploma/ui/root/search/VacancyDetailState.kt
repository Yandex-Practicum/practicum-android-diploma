package ru.practicum.android.diploma.ui.root.search

import ru.practicum.android.diploma.domain.models.VacancyDetail

sealed class VacancyDetailState {
    data class Success(val vacancyDetail: VacancyDetail) : VacancyDetailState()
}
