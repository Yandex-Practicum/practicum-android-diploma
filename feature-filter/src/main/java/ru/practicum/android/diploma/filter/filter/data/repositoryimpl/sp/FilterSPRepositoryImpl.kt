package ru.practicum.android.diploma.filter.filter.data.repositoryimpl.sp

import ru.practicum.android.diploma.data.sp.api.FilterSp
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

    override suspend fun updateDataFilterBuffer(filter: FilterSettings): Int {
        return filterSp.updateDataFilterBuffer(FilterMapper.map(filter)).code
    }

    override suspend fun updateDataFilter(filter: FilterSettings): Int {
        return filterSp.updateDataFilter(FilterMapper.map(filter)).code
    }

    override suspend fun clearPlaceInDataFilterBuffer(): Int {
        return filterSp.updatePlaceInDataFilterBuffer(FilterMapper.mapClearPlace()).code
    }

    override suspend fun clearProfessionInDataFilterBuffer(): Int {
        return filterSp.updateProfessionInDataFilterBuffer(FilterMapper.mapClearIndustry()).code
    }

    override suspend fun updateSalaryInDataFilterBuffer(expectedSalary: String): Int {
        return filterSp.updateSalaryInDataFilterBuffer(expectedSalary).code
    }

    override suspend fun updateDoNotShowWithoutSalaryInDataFilterBuffer(doNotShowWithoutSalary: Boolean): Int {
        return filterSp.updateDoNotShowWithoutSalaryInDataFilterBuffer(doNotShowWithoutSalary).code
    }

    override suspend fun setForceSearch() {
        filterSp.forceSearch()
    }

    override suspend fun dropForceSearch() {
        filterSp.disableForceSearch()
    }
}
