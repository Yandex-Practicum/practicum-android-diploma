package ru.practicum.android.diploma.ui.filter.industry

import ru.practicum.android.diploma.domain.models.Industries

fun List<Industries>.toIndustryListItems(): List<IndustryListItem> {
    return this.flatMap { industry ->
        val parentItem = IndustryListItem(
            id = industry.id,
            name = industry.name,
            isSelected = false,
            details = industry.industries.takeIf { it.isNotEmpty() }
        )

        val childItems = industry.industries.map {
            IndustryListItem(
                id = it.id,
                name = it.name,
                isSelected = false,
                details = null
            )
        }

        listOf(parentItem) + childItems
    }
}
