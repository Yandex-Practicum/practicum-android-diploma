package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.VacancyAction

interface VacancyActionInteractor {
    fun handleAction(action: VacancyAction)
}
