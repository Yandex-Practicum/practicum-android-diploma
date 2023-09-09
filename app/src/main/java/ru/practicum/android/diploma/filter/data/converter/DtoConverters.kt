package ru.practicum.android.diploma.filter.data.converter

import ru.practicum.android.diploma.filter.data.model.CountryDto
import ru.practicum.android.diploma.filter.data.model.IndustryDto
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.filter.domain.models.IndustryArea
import ru.practicum.android.diploma.filter.domain.models.Region
import ru.practicum.android.diploma.filter.data.model.RegionListDto

fun mapRegionListDtoToRegionList(response: RegionListDto): List<Region> {
    val regionList = mutableListOf<Region>()
    response.areas?.forEach { regionDto ->
        val id = regionDto?.areas?.firstOrNull()?.id ?: ""
        val name = regionDto?.name ?: ""
        regionList.add(Region(id, name))
    }
    return regionList.filter { it.id.isEmpty() or it.name.isEmpty() }
}

fun countryDtoToCountry(list: List<CountryDto>): List<Country> {
    return list
        .map { Country(id = it.id ?: "", name = it.name ?: "") }
        .sortedWith(compareBy({ it.name == OTHER }, { it.name }))
}

fun industryDtoListToIndustryList(list: List<IndustryDto>): List<Industry> {
    return list.map {
        Industry(
            id = it.id ?: "",
            industries = it.industries?.map { industryAreaDto ->
                IndustryArea(
                    id = industryAreaDto.id ?: "",
                    name = industryAreaDto.name ?: ""
                )
            } ?: emptyList(),
            name = it.name ?: ""
        )
    }
}

private const val OTHER = "Другие регионы"