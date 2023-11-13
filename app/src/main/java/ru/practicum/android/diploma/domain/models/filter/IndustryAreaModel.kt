package ru.practicum.android.diploma.domain.models.filter

abstract class IndustryAreaModel(
    open val id: String,
    open val name: String,
    open var isChecked: Boolean = false
)