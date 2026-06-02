package ru.practicum.android.diploma.core.domain.models

data class Industry(
    val id: String,
    val name: String
) {
    companion object
}

typealias Industries = List<Industry>
