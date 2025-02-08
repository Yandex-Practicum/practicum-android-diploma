package ru.practicum.android.diploma.common.util

import ru.practicum.android.diploma.common.data.dto.region.SearchRegionResponse
import ru.practicum.android.diploma.filter.data.dto.model.AreaDto
import ru.practicum.android.diploma.filter.domain.model.Area
import ru.practicum.android.diploma.filter.domain.model.Country

object RegionConverter {
    fun convertToArea(areaDto: AreaDto?): Area {
        return areaDto?.let { dto ->
            Area(
                id = dto.id,
                name = dto.name,
                parentId = dto.parentId,
                isSelected = false,
                areas = dto.areas?.map { convertToArea(it) } ?: emptyList()
            )
        } ?: Area(id = "", name = "", parentId = null, isSelected = false, areas = emptyList())
    }

    fun convertToCountry(searchRegionResponse: SearchRegionResponse?): Country {
        return searchRegionResponse?.let { response ->
            Country(
                id = response.id.toString(),
                name = response.name,
            )
        } ?: Country("", "")
    }

    fun applyCountryById(searchRegionResponse: SearchRegionResponse?): Country {
        return searchRegionResponse?.let { response ->
            when (response.parentId) {
                null -> Country(id = response.id.toString(), name = response.name)
                else -> processParentId(response.parentId.toString())
            }
        } ?: Country("", "")
    }

    private fun processParentId(parentId: String): Country {
        return when (parentId) {
            RUSSIA_ID -> Country(id = RUSSIA_ID, name = RUSSIA)
            KAZAKHSTAN_ID -> Country(id = KAZAKHSTAN_ID, name = KAZAKHSTAN)
            AZERBAIJAN_ID -> Country(id = AZERBAIJAN_ID, name = AZERBAIJAN)
            BELARUS_ID -> Country(id = BELARUS_ID, name = BELARUS)
            GEORGIA_ID -> Country(id = GEORGIA_ID, name = GEORGIA)
            UKRAINE_ID -> Country(id = UKRAINE_ID, name = UKRAINE)
            KYRGYZSTAN_ID -> Country(id = KYRGYZSTAN_ID, name = KYRGYZSTAN)
            UZBEKISTAN_ID -> Country(id = UZBEKISTAN_ID, name = UZBEKISTAN)
            OTHER_REGIONS_ID -> Country(id = OTHER_REGIONS_ID, name = OTHER_REGIONS)
            else -> Country("", "")
        }
    }

    fun mapRegions(areas: List<Area>): List<Area> {
        val resultAreaList = mutableListOf<Area>()
        for (area in areas) {
            if (area.areas.isEmpty()) {
                resultAreaList.add(area)
            } else {
                for (city in area.areas) {
                    resultAreaList.add(city)
                }
            }

        }
        return resultAreaList
    }

    fun mapAllCisRegions(areas: List<Area>): List<Area> {
        val resultAreaList = mutableListOf<Area>()
        for (country in areas) {
            if (!assertRegionIsCis(country.name)) continue

            for (region in country.areas) {
                if (region.areas.isEmpty()) {
                    resultAreaList.add(region)
                }
                for (city in region.areas) {
                    resultAreaList.add(city)
                }
            }
        }
        return resultAreaList
    }

    private fun assertRegionIsCis(areaName: String?): Boolean {
        val cisCountries = setOf(
            RUSSIA,
            KAZAKHSTAN,
            UKRAINE,
            AZERBAIJAN,
            BELARUS,
            GEORGIA,
            KYRGYZSTAN,
            UZBEKISTAN
        )
        return areaName?.trim() in cisCountries
    }

    private const val RUSSIA = "Россия"
    private const val KAZAKHSTAN = "Казахстан"
    private const val AZERBAIJAN = "Азербайджан"
    private const val BELARUS = "Беларусь"
    private const val GEORGIA = "Грузия"
    private const val UKRAINE = "Украина"
    private const val KYRGYZSTAN = "Кыргызстан"
    private const val UZBEKISTAN = "Узбекистан"
    private const val OTHER_REGIONS = "Другие регионы"

    private const val RUSSIA_ID = "113"
    private const val KAZAKHSTAN_ID = "40"
    private const val AZERBAIJAN_ID = "9"
    private const val BELARUS_ID = "16"
    private const val GEORGIA_ID = "28"
    private const val UKRAINE_ID = "5"
    private const val KYRGYZSTAN_ID = "48"
    private const val UZBEKISTAN_ID = "97"
    private const val OTHER_REGIONS_ID = "1001"

}
