package ru.practicum.android.diploma.presentation.filtration.region.mapper

import ru.practicum.android.diploma.domain.models.FilterArea
import ru.practicum.android.diploma.ui.filtration.region.model.RegionUi

const val NO_COUNTRY_ID = -1

fun List<FilterArea>.toRegionUiList(countryId: Int): List<RegionUi> {
    val sourceAreas = if (countryId == NO_COUNTRY_ID) {
        this
    } else {
        listOfNotNull(findAreaById(countryId))
    }

    return sourceAreas
        .flatMap { area -> area.flattenChildrenToRegionUi() }
        .distinctBy { region -> region.id }
        .sortedBy { region -> region.name }
}

private fun List<FilterArea>.findAreaById(id: Int): FilterArea? {
    return firstNotNullOfOrNull { area ->
        if (area.id == id) {
            area
        } else {
            area.areas.findAreaById(id)
        }
    }
}

private fun FilterArea.flattenChildrenToRegionUi(): List<RegionUi> {
    return areas.flatMap { child ->
        listOf(RegionUi(id = child.id, name = child.name)) + child.flattenChildrenToRegionUi()
    }
}
