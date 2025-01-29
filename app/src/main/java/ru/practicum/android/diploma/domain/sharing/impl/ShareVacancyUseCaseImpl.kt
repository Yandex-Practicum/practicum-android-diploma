package ru.practicum.android.diploma.domain.sharing.impl

import ru.practicum.android.diploma.domain.sharing.api.ExternalNavigator
import ru.practicum.android.diploma.domain.sharing.api.ShareVacancyUseCase

class ShareVacancyUseCaseImpl(private val externalNavigator: ExternalNavigator) : ShareVacancyUseCase {
    override fun execute(vacancyLink: String) {
        externalNavigator.shareVacancy(vacancyLink)
    }
}
