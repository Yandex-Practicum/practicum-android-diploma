package ru.practicum.android.diploma.domain.models.filter

data class Industry(
    val id: String,
    val name: String,
    var isChecked: Boolean = false
)
