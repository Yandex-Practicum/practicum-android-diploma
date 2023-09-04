package ru.practicum.android.diploma.details.domain.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import kotlinx.parcelize.Parcelize

@Parcelize
@Serializable
data class VacancyFullInfoModel(
    val id: String,
    val experience: String = "",
    val employment: String = "",
    val schedule: String = "",
    val description: String = "",
    @SerializedName("key_skills") val keySkills: String = "",
    val area: String = "",
    val salary: String = "",
    val date: String = "",
    val company: String = "",
    val logo: String = "",
    val title: String = "",
    val contactEmail: String = "",
    val contactName: String = "",
    val contactPhones: List<String> = emptyList(),
) : Parcelable