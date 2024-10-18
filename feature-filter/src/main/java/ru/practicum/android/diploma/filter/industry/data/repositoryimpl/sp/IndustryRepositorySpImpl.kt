package ru.practicum.android.diploma.filter.industry.data.repositoryimpl.sp

import ru.practicum.android.diploma.data.sp.api.FilterSp
import ru.practicum.android.diploma.filter.industry.data.mappers.IndustryMapper
import ru.practicum.android.diploma.filter.industry.domain.model.IndustryModel
import ru.practicum.android.diploma.filter.industry.domain.repository.IndustryRepositorySp

class IndustryRepositorySpImpl(
    private val filterSp: FilterSp
) : IndustryRepositorySp {

    override suspend fun updateProfessionInDataFilter(branchOfProfession: IndustryModel): Int {
        return filterSp.updateProfessionInDataFilter(
            IndustryMapper.map(branchOfProfession)
        )
    }

    override suspend fun getBranchOfProfessionDataFilter(): IndustryModel? {
        return filterSp.getBranchOfProfessionDataFilter()?.let { IndustryMapper.map(it) }
    }
}
