package ru.practicum.android.diploma.sharing.domain.impl

import ru.practicum.android.diploma.sharing.domain.ExternalNavigator
import ru.practicum.android.diploma.sharing.domain.SharingInteractor

class SharingInteractorImpl(private val externalNavigator: ExternalNavigator) : SharingInteractor {

    override fun shareVacancy(link: String) {
        externalNavigator.shareLink(link)
    }

    override fun sendMessageToEmail(email: String) {
        externalNavigator.openEmail(email)
    }

    override fun callEmployer(number: String) {
        externalNavigator.openAppForCalls(number)
    }
}
