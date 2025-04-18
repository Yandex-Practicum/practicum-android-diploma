package ru.practicum.android.diploma.domain.models.additional

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Employment(val id: String, val label: String) : Parcelable {
    Full("full", "Полная занятость"),
    Part("part", "Подработка"),
    Project("project", "Проект или разовое задание"),
    FlyInFlyOut("flyInFlyOut", "Вахта");

    companion object {
        fun fromId(id: String): Employment = when (id) {
            Full.id -> Full
            Part.id -> Part
            Project.id -> Project
            FlyInFlyOut.id -> FlyInFlyOut
            else -> throw IllegalArgumentException("Unknown employment form id: $id")
        }

        fun fromIdOrNull(id: String): Employment? = when (id) {
            Full.id -> Full
            Part.id -> Part
            Project.id -> Project
            FlyInFlyOut.id -> FlyInFlyOut
            else -> null
        }

        fun all(): List<Employment> = listOf(
            Full,
            Part,
            Project,
            FlyInFlyOut
        )
    }
}
