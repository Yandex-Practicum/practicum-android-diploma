package ru.practicum.android.diploma.domain.impl.sharing

import ru.practicum.android.diploma.domain.api.sharing.ExternalNavigator
import ru.practicum.android.diploma.domain.api.sharing.SharingInteractor

class SharingInteractorImpl(
    private var externalNavigator: ExternalNavigator,
) : SharingInteractor {

    override fun shareApp(url: String) {
        externalNavigator.shareApp(url)
    }

    override fun phoneCall(phone: String) {
        externalNavigator.makeCall(phone)
    }

    override fun eMail(email: String) {
        externalNavigator.openEmail(email)
    }
}
