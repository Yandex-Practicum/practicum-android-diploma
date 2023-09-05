package ru.practicum.android.diploma.details.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VacancyDetails(
    val contacts: Contacts?,
    val description: String,
    val employer: Employer?,
    val experience: Experience?,
    val key_skills: Array<KeySkill>,
    val schedule: Schedule?,
): Parcelable

@Parcelize
data class Contacts(
    val email: String?,
    val name: String?,
    val phones: Array<Phone>?,
) : Parcelable

@Parcelize
data class Phone(
    val city: String,
    val country: String,
    val number: String
) : Parcelable

@Parcelize
data class Employer(
    val logo_urls: LogoUrls?,
) : Parcelable

@Parcelize
data class LogoUrls(
    val v90: String,
    val v240: String,
    val original: String,
) : Parcelable

@Parcelize
data class Experience(
    val name: String,
) : Parcelable

@Parcelize
data class KeySkill(
    val name: String,
) : Parcelable

@Parcelize
data class Schedule(
    val name: String,
) : Parcelable