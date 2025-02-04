package ru.practicum.android.diploma.common.util

import ru.practicum.android.diploma.filter.data.dto.model.AreaDto
import ru.practicum.android.diploma.filter.domain.model.Area

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
}
