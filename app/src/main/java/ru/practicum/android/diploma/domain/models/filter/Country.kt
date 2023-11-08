package ru.practicum.android.diploma.domain.models.filter

data class Country(
    override val id: String,
    override val name: String,
    override var isChecked: Boolean = false
): IndustryAreaModel(id, name)
