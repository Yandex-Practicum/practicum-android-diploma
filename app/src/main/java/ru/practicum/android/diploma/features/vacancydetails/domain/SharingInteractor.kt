package ru.practicum.android.diploma.features.vacancydetails.domain

import ru.practicum.android.diploma.features.vacancydetails.domain.models.Email

interface SharingInteractor {

    fun createEmailObject(address: String, vacancyName: String): Email?
    fun generateShareText(strings: List<String>): String

}