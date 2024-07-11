package ru.practicum.android.diploma.sharing.domain

interface SharingInteractor {

    fun shareVacancy(link: String)

    fun sendMessageToEmail(email: String)

    fun callEmployer(number: String)
}
