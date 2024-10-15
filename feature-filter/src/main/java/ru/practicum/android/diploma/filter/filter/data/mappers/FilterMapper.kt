package ru.practicum.android.diploma.filter.filter.data.mappers

import ru.practicum.android.diploma.data.sp.dto.FilterDto
import ru.practicum.android.diploma.data.sp.dto.PlaceDto
import ru.practicum.android.diploma.filter.filter.domain.model.FilterSettings
import ru.practicum.android.diploma.filter.filter.domain.model.PlaceSettings

object FilterMapper {
    fun map(filterDto: FilterDto): FilterSettings {
        return with(filterDto) {
            FilterSettings(
                placeSettings = PlaceSettings(
                    idCountry = placeDto?.idCountry,
                    nameCountry = placeDto?.nameCountry,
                    idRegion = placeDto?.idRegion,
                    nameRegion = placeDto?.nameRegion
                ),
                branchOfProfession = branchOfProfession,
                expectedSalary = expectedSalary,
                doNotShowWithoutSalary = doNotShowWithoutSalary
            )
        }
    }

    fun mapClear(): PlaceDto {
        return PlaceDto(
            idCountry = null,
            nameCountry = null,
            idRegion = null,
            nameRegion = null
        )
    }
}
