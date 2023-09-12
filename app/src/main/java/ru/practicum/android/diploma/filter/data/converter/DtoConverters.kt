package ru.practicum.android.diploma.filter.data.converter

import ru.practicum.android.diploma.filter.data.model.CountryDto
import ru.practicum.android.diploma.filter.data.model.IndustryDto
import ru.practicum.android.diploma.filter.data.model.RegionDto
import ru.practicum.android.diploma.filter.data.model.RegionListDto
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.filter.domain.models.Region

fun mapRegionListDtoToRegionList(response: RegionListDto): List<Region> {
    val regionDtoList = mutableListOf<RegionDto>()
    response.areas?.forEach { regionDto ->
        if (regionDto != null) {
            regionDtoList.add(regionDto)
        }
    }
    val citiesList = regionDtoList.map { Region(id = it.id ?: "", name = it.name ?: "") }
    val minorCitiesList = regionDtoList.flatMap { it.areas ?: emptyList() }
        .map { Region(id = it?.id ?: "", name = it?.name ?: "") }.sortedBy { it.name }
        .toMutableList()
    minorCitiesList.addAll(citiesList)
    return minorCitiesList

}

fun countryDtoToCountry(list: List<CountryDto>): List<Country> {
    return list
        .map { Country(id = it.id ?: "", name = it.name ?: "") }
        .sortedWith(compareBy({ it.name == OTHER }, { it.name }))
}

fun industryDtoListToIndustryList(list: List<IndustryDto>): List<Industry> {
    return list.flatMap { it.industries ?: emptyList() }
        .map { Industry(id = it.id ?: "", name = it.name ?: "", industries = emptyList()) }
        .sortedBy { it.name }
}

private const val OTHER = "Другие регионы"

