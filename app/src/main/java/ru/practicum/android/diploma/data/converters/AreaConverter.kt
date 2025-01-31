package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.dto.AreaDto
import ru.practicum.android.diploma.data.dto.AreasDto
import ru.practicum.android.diploma.domain.models.Area

class AreaConverter {
    fun convertCountries(response: AreasDto): List<Area> {
        return response.areas.map { it.toArea() }
    }

    fun convertRegions(response: AreaDto): List<Area> {
        return response.areas?.map { it.toArea() } ?: emptyList()
    }

    private fun AreaDto.toArea(): Area {
        return Area(
            id = this.id,
            name = this.name,
        )
    }
}
