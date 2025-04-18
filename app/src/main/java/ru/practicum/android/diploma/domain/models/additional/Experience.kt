package ru.practicum.android.diploma.domain.models.additional

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Experience(val id: String, val label: String) : Parcelable {
    NoExperience("noExperience", "Нет опыта"),
    Between1And3("between1And3", "От 1 года до 3 лет"),
    Between3And6("between3And6", "От 3 до 6 лет"),
    MoreThan6("moreThan6", "Более 6 лет");

    companion object {
        fun fromId(id: String): Experience = when (id) {
            NoExperience.id -> NoExperience
            Between1And3.id -> Between1And3
            Between3And6.id -> Between3And6
            MoreThan6.id -> MoreThan6
            else -> throw IllegalArgumentException("Unknown experience id: $id")
        }

        fun fromIdOrNull(id: String): Experience? = when (id) {
            NoExperience.id -> NoExperience
            Between1And3.id -> Between1And3
            Between3And6.id -> Between3And6
            MoreThan6.id -> MoreThan6
            else -> null
        }

        fun all(): List<Experience> = listOf(
            NoExperience,
            Between1And3,
            Between3And6,
            MoreThan6
        )
    }
}
