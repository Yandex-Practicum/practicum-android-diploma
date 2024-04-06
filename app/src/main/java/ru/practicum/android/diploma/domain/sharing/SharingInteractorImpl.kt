package ru.practicum.android.diploma.domain.sharing

class SharingInteractorImpl(
    private val externalNavigator: ExternalNavigator
) : SharingInteractor {
    override fun writeEmail(address: String) {
        externalNavigator.writeEmail(address)
    }
}
