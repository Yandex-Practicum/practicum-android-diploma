package ru.practicum.android.diploma.data.filter

import ru.practicum.android.diploma.data.dto.filter.CountryDto
import ru.practicum.android.diploma.data.dto.filter.IndustryDto
import ru.practicum.android.diploma.data.dto.filter.RegionListDto
import ru.practicum.android.diploma.domain.models.filter.Area
import ru.practicum.android.diploma.domain.models.filter.Country
import ru.practicum.android.diploma.domain.models.filter.Industry

class FiltersMapper {
    fun mapCoyntryFromDto(countryDto: CountryDto): Country {
        return Country(
            countryDto.id,
            countryDto.name
        )
    }

    fun mapAreasFromDto(AreaDto: RegionListDto): Area {
        return Area(
            AreaDto.id,
            AreaDto.parent_id,
            AreaDto.name,
            AreaDto.areas.map { mapAreasFromDto(it)}
        )
    }

    fun mapIndustryFromDto(industryDto: IndustryDto):Industry {
        return Industry(
            industryDto.id,
            industryDto.name,
            false
        )

    }

}
