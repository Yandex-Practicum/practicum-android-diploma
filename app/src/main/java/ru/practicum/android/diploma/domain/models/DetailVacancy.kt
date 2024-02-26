package ru.practicum.android.diploma.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailVacancy(
    val id: String,
    val areaId: String?,
    val areaName: String?,
    val areaUrl: String?,
    val contactsCallTrackingEnabled: Boolean?,
    val contactsEmail: String?,
    val contactsName: String?,
    val contactsPhones: List<String>?,
    val comment: String?,
    val description: String?,
    val employerName: String?,
    val employmentId: String?,
    val employmentName: String?,
    val experienceId: String?,
    val experienceName: String?,
    val keySkillsNames: List<String>?,
    val name: String?,
    val salaryCurrency: String?,
    val salaryFrom: Int?,
    val salaryGross: Boolean?,
    val salaryTo: Int?,
    val scheduleId: String?,
    val scheduleName: String?,
    val logoUrl: String?,
    val logoUrl90: String?,
    val logoUrl240: String?,
    val employerUrl: String?,
) : Parcelable {
    val isFavorite: FavBooleanClass = FavBooleanClass()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val vacancy = other as Vacancy
        return id == vacancy.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}
class FavBooleanClass {
    var isFavorite: Boolean = false
}

