package ru.practicum.android.diploma.filter.filter.data.repositoryimpl.sp

import ru.practicum.android.diploma.data.sp.api.FilterSp
import ru.practicum.android.diploma.filter.filter.data.mappers.FilterMapper
import ru.practicum.android.diploma.filter.filter.domain.model.FilterSettings
import ru.practicum.android.diploma.filter.filter.domain.repository.FilterSPRepository

class FilterSPRepositoryImpl(
    val filterSp: FilterSp
) : FilterSPRepository {

    override suspend fun clearDataFilter() {
        filterSp.clearDataFilter()
    }

    override suspend fun getExpectedSalaryDataFilter(): String? {
        return filterSp.getExpectedSalaryDataFilter()
    }

    override suspend fun isDoNotShowWithoutSalaryDataFilter(): Boolean {
        return filterSp.isDoNotShowWithoutSalaryDataFilter()
    }

    override suspend fun getDataFilter(): FilterSettings {
        return FilterMapper.map(filterSp.getDataFilter())
    }

    override suspend fun clearPlaceInDataFilter(): Int {
        return filterSp.updatePlaceInDataFilter(FilterMapper.mapClearPlace())
    }

    override suspend fun clearProfessionInDataFilter(): Int {
        return filterSp.updateProfessionInDataFilter(FilterMapper.mapClearIndustry())
    }

    override suspend fun updateSalaryInDataFilter(expectedSalary: String): Int {
        return filterSp.updateSalaryInDataFilter(expectedSalary)
    }

    override suspend fun updateDoNotShowWithoutSalaryInDataFilter(doNotShowWithoutSalary: Boolean): Int {
        return filterSp.updateDoNotShowWithoutSalaryInDataFilter(doNotShowWithoutSalary)
    }
}
