package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.dto.AreaDto
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Places

class AreaConverter {
    fun convertCountries(response: List<AreaDto>): List<Area> {
        return response.map { it.toArea() }
    }

    fun convertAreas(countries: List<AreaDto>, otherAreas: List<AreaDto>): Places {
        return Places(
            countries = countries.map { it.toArea() },
            others = otherAreas.map { it.toArea() }
        )
    }

    private fun AreaDto.toArea(): Area {
        return Area(
            id = this.id,
            name = this.name,
            parentId = parentId,
        )
    }
}
