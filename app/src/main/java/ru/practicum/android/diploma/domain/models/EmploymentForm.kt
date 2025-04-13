package ru.practicum.android.diploma.domain.models

sealed class EmploymentForm(val id: String, val label: String) {
    object Full : EmploymentForm("FULL", "Полная занятость")
    object Part : EmploymentForm("PART", "Подработка")
    object Project : EmploymentForm("PROJECT", "Проект или разовое задание")
    object FlyInFlyOut : EmploymentForm("FLY_IN_FLY_OUT", "Вахта")

    companion object {
        fun fromId(id: String): EmploymentForm = when (id) {
            Full.id -> Full
            Part.id -> Part
            Project.id -> Project
            FlyInFlyOut.id -> FlyInFlyOut
            else -> throw IllegalArgumentException("Unknown employment form id: $id")
        }

        fun all(): List<EmploymentForm> = listOf(
            Full,
            Part,
            Project,
            FlyInFlyOut
        )
    }
}
