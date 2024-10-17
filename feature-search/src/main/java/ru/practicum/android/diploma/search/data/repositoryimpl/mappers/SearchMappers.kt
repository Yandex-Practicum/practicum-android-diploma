package ru.practicum.android.diploma.search.data.repositoryimpl.mappers

import ru.practicum.android.diploma.data.sp.dto.FilterDto
import ru.practicum.android.diploma.search.domain.models.sp.FilterSearch
import ru.practicum.android.diploma.search.domain.models.sp.IndustrySearch
import ru.practicum.android.diploma.search.domain.models.sp.PlaceSearch

object SearchMappers {
    fun map(filterDto: FilterDto): FilterSearch {
        return with(filterDto) {
            FilterSearch(
                placeSearch = PlaceSearch(
                    idCountry = placeDto?.idCountry,
                    nameCountry = placeDto?.nameCountry,
                    idRegion = placeDto?.idRegion,
                    nameRegion = placeDto?.nameRegion
                ),
                branchOfProfession = IndustrySearch(
                    id = branchOfProfession?.id,
                    name = branchOfProfession?.name
                ),
                expectedSalary = expectedSalary,
                doNotShowWithoutSalary = doNotShowWithoutSalary
            )
        }
    }
}
