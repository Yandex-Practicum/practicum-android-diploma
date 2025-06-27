package ru.practicum.android.diploma.ui.filter.industry

import ru.practicum.android.diploma.domain.models.IndustriesDetails

data class IndustryListItem(
    val id: String,
    val name: String,
    val details: List<IndustriesDetails>? = null,
    val isSelected: Boolean = false
)
