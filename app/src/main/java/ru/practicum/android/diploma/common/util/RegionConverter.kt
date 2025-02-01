package ru.practicum.android.diploma.common.util

import ru.practicum.android.diploma.filter.data.dto.model.AreaDto
import ru.practicum.android.diploma.filter.domain.model.Area
import ru.practicum.android.diploma.filter.domain.model.City

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

    fun convertToCity(areaDto: Area): City {
        return City(
            id = areaDto.id,
            name = areaDto.name,
            parentId = areaDto.parentId,
            isSelected = false,
        )
    }

    fun mapRegions(areas: List<Area>): List<Area> {
        return areas.flatMap { regionDto ->
            regionDto.areas
        }
//
//                ?.map { cities ->
//                convertToArea(cities)
//            } ?: emptyList()
        }


//    private fun mapAllRegions(areas: List<AreaDto>): List<Area> {
//        return areas.flatMap { regionDto ->
//            regionDto.areas?.flatMap { nestedRegionDto ->
//                nestedRegionDto.areas?.map { Converter.convertToArea(it) }
//            } ?: emptyList()
//        }
//    }

    fun mapAllCisRegions(areas: List<Area>): List<Area> {
        val resultAreaList = mutableListOf<Area>()
        if (areas.isNotEmpty()) {
            for (country in areas) {
                if (assertRegionIsCis(country?.name)) {
                    for (region in country?.areas.orEmpty()) {
                        for (city in region.areas.orEmpty()) {
                            resultAreaList.add(city)
                        }
                    }
                }
            }
        }
        return resultAreaList
    }

    fun mapNonCisRegions(areas: List<Area>): List<Area> {
        val resultAreaList = mutableListOf<Area>()
        if (areas.isNotEmpty()) {
            for (country in areas) {
                if (!assertRegionIsCis(country?.name)) {
                    for (region in country?.areas.orEmpty()) {
                        for (city in region.areas.orEmpty()) {
                            resultAreaList.add(city)
                        }
                    }
                }
            }
        }
        return resultAreaList
    }

    private fun assertRegionIsCis(areaName: String?): Boolean {
        val cisCountries = setOf(
            RUSSIA, KAZAKHSTAN, UKRAINE, AZERBAIJAN,
            BELARUS, GEORGIA, KYRGYZSTAN, UZBEKISTAN
        )
        return areaName?.trim() in cisCountries
    }

    //    private fun convertToArea(areaDto: AreaDto): Area {
//        return Area(
//            id = areaDto.id,
//            name = areaDto.name,
//            parentId = areaDto.parentId,
//            areas = areaDto.areas?.map { convertToArea(it) } ?: emptyList()
//        )
//    }
        private const val RUSSIA = "Россия"
        private const val KAZAKHSTAN = "Казахстан"
        private const val AZERBAIJAN = "Азербайджан"
        private const val BELARUS = "Беларусь"
        private const val GEORGIA = "Грузия"
        private const val UKRAINE = "Украина"
        private const val KYRGYZSTAN = "Кыргызстан"
        private const val UZBEKISTAN = "Узбекистан"

}
