package ru.practicum.android.diploma.domain.sharing.impl

import ru.practicum.android.diploma.data.sharing.ExternalNavigator
import ru.practicum.android.diploma.domain.sharing.SharingInteractor

class SharingInteractorImpl(private val externalNavigator: ExternalNavigator) : SharingInteractor {
    override fun shareVacancy(linkVacancy: String) {
        externalNavigator.shareVacancy(linkVacancy)
    }
}
