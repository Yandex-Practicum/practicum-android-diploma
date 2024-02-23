package ru.practicum.android.diploma.vacancy.domain.usecase

import ru.practicum.android.diploma.vacancy.domain.api.ExternalNavigator

class ShareVacancyUseCase(private val externalNavigator: ExternalNavigator) {

    fun execute() {
        externalNavigator.shareVacancy()
    }
}
