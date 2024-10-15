package ru.practicum.android.diploma.filter.filter.domain.usecase.impl

import ru.practicum.android.diploma.data.sp.dto.FilterDto
import ru.practicum.android.diploma.filter.filter.domain.repository.FilterSPRepository
import ru.practicum.android.diploma.filter.place.domain.model.Place

internal class FilterSPInteractorImpl(private val repository: FilterSPRepository) : FilterSPInteractor {
    override suspend fun clearDataFilter() {
        TODO("Not yet implemented")
    }

    override suspend fun getPlaceDataFilter(): Place? {
        TODO("Not yet implemented")
    }

    override suspend fun getBranchOfProfessionDataFilter(): String? {
        TODO("Not yet implemented")
    }

    override suspend fun getExpectedSalaryDataFilter(): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun isDoNotShowWithoutSalaryDataFilter(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getDataFilter(): FilterDto {
        TODO("Not yet implemented")
    }

    override suspend fun updatePlaceInDataFilter(place: Place): Int {
        TODO("Not yet implemented")
    }

    override suspend fun updateProfessionInDataFilter(industry: String): Int {
        TODO("Not yet implemented")
    }

    override suspend fun updateSalaryInDataFilter(expectedSalary: Int): Int {
        TODO("Not yet implemented")
    }

    override suspend fun updateDoNotShowWithoutSalaryInDataFilter(doNotShowWithoutSalary: Boolean): Int {
        TODO("Not yet implemented")
    }

    override suspend fun clearSalaryFilter() {
        TODO("Not yet implemented")
    }

    override suspend fun clearPlaceFilter() {
        TODO("Not yet implemented")
    }

    override suspend fun clearIndustryFilter() {
        TODO("Not yet implemented")
    }


}

