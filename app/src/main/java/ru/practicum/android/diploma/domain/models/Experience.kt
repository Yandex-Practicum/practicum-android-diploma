package ru.practicum.android.diploma.domain.models

sealed class Experience(val id: String, val label: String) {
    object NoExperience : Experience("noExperience", "Нет опыта")
    object Between1And3 : Experience("between1And3", "От 1 года до 3 лет")
    object Between3And6 : Experience("between3And6", "От 3 до 6 лет")
    object MoreThan6 : Experience("moreThan6", "Более 6 лет")

    companion object {
        fun fromId(id: String): Experience = when (id) {
            NoExperience.id -> NoExperience
            Between1And3.id -> Between1And3
            Between3And6.id -> Between3And6
            MoreThan6.id -> MoreThan6
            else -> throw IllegalArgumentException("Unknown experience id: $id")
        }

        fun all(): List<Experience> = listOf(
            NoExperience,
            Between1And3,
            Between3And6,
            MoreThan6
        )
    }
}
