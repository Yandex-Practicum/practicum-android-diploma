package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.dto.AreaDto
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Areas

class AreaConverter {
    fun convertCountries(response: List<AreaDto>): List<Area> {
        return response.map { it.toArea() }
    }

    fun convertAreas(countries: List<AreaDto>, otherAreas: List<AreaDto>): Areas {
        return Areas(
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
