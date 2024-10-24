package ru.practicum.android.diploma.filter.filter.data.repositoryimpl.sp

import ru.practicum.android.diploma.data.sp.api.FilterSp
import ru.practicum.android.diploma.data.sp.api.SpResult
import ru.practicum.android.diploma.filter.filter.data.mappers.FilterMapper
import ru.practicum.android.diploma.filter.filter.domain.model.FilterSettings
import ru.practicum.android.diploma.filter.filter.domain.repository.FilterSPRepository

internal class FilterSPRepositoryImpl(private val filterSp: FilterSp) : FilterSPRepository {

    override suspend fun clearDataFilterAll() {
        filterSp.clearDataFilterAll()
    }

    override suspend fun getExpectedSalaryDataFilterBuffer(): String? {
        return filterSp.getExpectedSalaryDataFilterBuffer()
    }

    override suspend fun isDoNotShowWithoutSalaryDataFilterBuffer(): Boolean {
        return filterSp.isDoNotShowWithoutSalaryDataFilterBuffer()
    }

    override suspend fun getDataFilter(): FilterSettings {
        return FilterMapper.map(filterSp.getDataFilter())
    }

    override suspend fun getDataFilterBuffer(): FilterSettings {
        return FilterMapper.map(filterSp.getDataFilterBuffer())
    }

    override suspend fun copyDataFilterInDataFilterBuffer() {
        filterSp.copyDataFilterInDataFilterBuffer()
    }

    override suspend fun copyDataFilterBufferInDataFilter() {
        filterSp.copyDataFilterBufferInDataFilter()
    }

    override suspend fun updateDataFilterBuffer(filter: FilterSettings): SpResult {
        return filterSp.updateDataFilterBuffer(FilterMapper.map(filter))
    }

    override suspend fun updateDataFilter(filter: FilterSettings): SpResult {
        return filterSp.updateDataFilter(FilterMapper.map(filter))
    }

    override suspend fun clearPlaceInDataFilterBuffer(): SpResult {
        return filterSp.updatePlaceInDataFilterBuffer(FilterMapper.mapClearPlace())
    }

    override suspend fun clearProfessionInDataFilterBuffer(): SpResult {
        return filterSp.updateProfessionInDataFilterBuffer(FilterMapper.mapClearIndustry())
    }

    override suspend fun updateSalaryInDataFilterBuffer(expectedSalary: String): SpResult {
        return filterSp.updateSalaryInDataFilterBuffer(expectedSalary)
    }

    override suspend fun updateDoNotShowWithoutSalaryInDataFilterBuffer(doNotShowWithoutSalary: Boolean): SpResult {
        return filterSp.updateDoNotShowWithoutSalaryInDataFilterBuffer(doNotShowWithoutSalary)
    }
}
