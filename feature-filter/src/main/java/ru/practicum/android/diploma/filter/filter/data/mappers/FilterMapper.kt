package ru.practicum.android.diploma.filter.filter.data.mappers

import ru.practicum.android.diploma.data.sp.dto.FilterDto
import ru.practicum.android.diploma.data.sp.dto.IndustryDto
import ru.practicum.android.diploma.data.sp.dto.PlaceDto
import ru.practicum.android.diploma.filter.filter.domain.model.FilterSettings
import ru.practicum.android.diploma.filter.filter.domain.model.PlaceSettings
import ru.practicum.android.diploma.filter.industry.domain.model.IndustryModel

internal object FilterMapper {
    fun map(filterDto: FilterDto): FilterSettings {
        return with(filterDto) {
            FilterSettings(
                placeSettings = PlaceSettings(
                    idCountry = placeDto?.idCountry,
                    nameCountry = placeDto?.nameCountry,
                    idRegion = placeDto?.idRegion,
                    nameRegion = placeDto?.nameRegion
                ),
                branchOfProfession = IndustryModel(
                    id = branchOfProfession?.id,
                    name = branchOfProfession?.name
                ),
                expectedSalary = expectedSalary,
                doNotShowWithoutSalary = doNotShowWithoutSalary
            )
        }
    }

    fun map(filter: FilterSettings): FilterDto {
        return with(filter) {
            FilterDto(
                placeDto = PlaceDto(
                    idCountry = placeSettings?.idCountry,
                    nameCountry = placeSettings?.nameCountry,
                    idRegion = placeSettings?.idRegion,
                    nameRegion = placeSettings?.nameRegion
                ),
                branchOfProfession = IndustryDto(
                    id = branchOfProfession?.id,
                    name = branchOfProfession?.name
                ),
                expectedSalary = expectedSalary,
                doNotShowWithoutSalary = doNotShowWithoutSalary
            )
        }
    }

    fun map(placeDto: PlaceDto): PlaceSettings {
        return with(placeDto) {
            PlaceSettings(
                idCountry = idCountry,
                nameCountry = nameCountry,
                idRegion = idRegion,
                nameRegion = nameRegion
            )
        }
    }

    fun map(place: PlaceSettings): PlaceDto {
        return with(place) {
            PlaceDto(
                idCountry = idCountry,
                nameCountry = nameCountry,
                idRegion = idRegion,
                nameRegion = nameRegion
            )
        }
    }

    fun mapClearPlace(): PlaceDto {
        return PlaceDto(
            idCountry = null,
            nameCountry = null,
            idRegion = null,
            nameRegion = null
        )
    }

    fun mapClearIndustry(): IndustryDto {
        return IndustryDto(
            id = null,
            name = null
        )
    }
}
