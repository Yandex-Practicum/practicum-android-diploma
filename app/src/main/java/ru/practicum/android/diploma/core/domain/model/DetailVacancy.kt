package ru.practicum.android.diploma.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailVacancy(
    val id: Long,
    val name: String,
    val salaryFrom: String,
    val salaryTo: String,
    val currency: String,
    val experience: String,
    val employment: String,
    val workSchedule: String,
    val description: String,
    val keySkills: String,
    val contactName: String,
    val email: String,
    val phone: String,
    val contactComment: String,
    val employerLogoUrl: String,
    val employerName: String,
    val city: String,
) : Parcelable
