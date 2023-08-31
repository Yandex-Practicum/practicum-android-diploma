package ru.practicum.android.diploma.features.vacancydetails.domain

import ru.practicum.android.diploma.features.vacancydetails.domain.models.Email

class SharingInteractorImpl : SharingInteractor {
    override fun createEmailObject(address: String, vacancyName: String): Email? {
        return if (address.isEmpty()) {
            null
        } else if (vacancyName.isEmpty()) {
            Email(address = address, subject = null)
        } else {
            Email(address = address, subject = vacancyName)
        }
    }

    override fun generateShareText(strings: List<String>): String {
        return strings.filter { it.isNotEmpty() }
            .joinToString("\n")
    }
}