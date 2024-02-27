package ru.practicum.android.diploma.vacancy.domain.usecase

import ru.practicum.android.diploma.vacancy.domain.api.ExternalNavigator

class MakeCallUseCase(private val externalNavigator: ExternalNavigator) {

    fun execute(phoneNumber: String) {
        externalNavigator.makeCall(phoneNumber)
    }
}
