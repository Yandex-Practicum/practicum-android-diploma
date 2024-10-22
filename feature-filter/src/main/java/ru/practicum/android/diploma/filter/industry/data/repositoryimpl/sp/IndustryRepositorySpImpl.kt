package ru.practicum.android.diploma.filter.industry.data.repositoryimpl.sp

import ru.practicum.android.diploma.data.sp.api.FilterSp
import ru.practicum.android.diploma.filter.industry.data.mappers.IndustryMapper
import ru.practicum.android.diploma.filter.industry.domain.model.IndustryModel
import ru.practicum.android.diploma.filter.industry.domain.repository.IndustryRepositorySp

internal class IndustryRepositorySpImpl(
    private val filterSp: FilterSp
) : IndustryRepositorySp {

    override suspend fun updateProfessionInDataFilterBuffer(branchOfProfession: IndustryModel): Int {
        return filterSp.updateProfessionInDataFilterBuffer(
            IndustryMapper.map(branchOfProfession)
        )
    }

    override suspend fun getBranchOfProfessionDataFilterBuffer(): IndustryModel? {
        return filterSp.getBranchOfProfessionDataFilterBuffer()?.let { IndustryMapper.map(it) }
    }
}
