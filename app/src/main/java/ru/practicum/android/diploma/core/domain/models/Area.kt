package ru.practicum.android.diploma.core.domain.models

data class Area(
    val id: String,
    val name: String
) {
    companion object
}

typealias Areas = List<Area>
