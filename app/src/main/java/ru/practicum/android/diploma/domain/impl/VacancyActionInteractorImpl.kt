package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.ExternalNavigator
import ru.practicum.android.diploma.domain.api.VacancyActionInteractor
import ru.practicum.android.diploma.domain.models.VacancyAction

class VacancyActionInteractorImpl(
    private val navigator: ExternalNavigator
) : VacancyActionInteractor {
    override fun handleAction(action: VacancyAction) {
        when (action) {
            is VacancyAction.ShareVacancy -> {
                navigator.shareLink(action.vacancyLink, action.title)
            }

            is VacancyAction.EmailClick -> {
                navigator.openEmail(action.email, action.title)
            }

            is VacancyAction.PhoneNumberClick -> {
                navigator.makePhoneCall(action.phoneNumber, action.title)
            }
        }
    }
}
