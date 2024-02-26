package ru.practicum.android.diploma.vacancy.domain.usecase

import ru.practicum.android.diploma.vacancy.domain.api.ExternalNavigator

class SendEmailUseCase(private val externalNavigator: ExternalNavigator) {

    fun execute(email: String) {
        externalNavigator.sendEmail(email)
    }
}
