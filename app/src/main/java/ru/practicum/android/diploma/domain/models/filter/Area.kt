package ru.practicum.android.diploma.domain.models.filter

data class Area(
    override val id: String,
    val parent_id: String?,
    override val name: String,
    val areas: List<Area>,
    override var isChecked: Boolean = false
) : IndustryAreaModel(id, name)
