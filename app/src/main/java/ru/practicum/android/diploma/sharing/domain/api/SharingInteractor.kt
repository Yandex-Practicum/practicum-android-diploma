package ru.practicum.android.diploma.sharing.domain.api

interface SharingInteractor {
    fun sendVacancy(link: String?)
    fun writeEmail(link: String?)
    fun makeCall(link: String?)
}