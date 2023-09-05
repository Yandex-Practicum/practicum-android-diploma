package ru.practicum.android.diploma.sharing.domain.impl

import ru.practicum.android.diploma.sharing.domain.api.ExternalNavigator
import ru.practicum.android.diploma.sharing.domain.api.SharingInteractor
import javax.inject.Inject

class SharingInteractorImpl @Inject constructor(
    private val externalNavigator: ExternalNavigator,
) : SharingInteractor {
    override fun sendVacancy(link: String?) {
        externalNavigator.sendVacancy(link)
    }

    override fun writeEmail(link: String?) {
        externalNavigator.writeEmail(link)
    }

    override fun makeCall(link: String?) {
        externalNavigator.makeCall(link)
    }
}