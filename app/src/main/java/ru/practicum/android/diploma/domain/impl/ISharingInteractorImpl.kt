package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.ISharingInteractor
import ru.practicum.android.diploma.domain.api.ISharingProvider

class ISharingInteractorImpl(val provider: ISharingProvider) : ISharingInteractor {
    override fun share() {
        provider.shareVacancy()
    }
}
