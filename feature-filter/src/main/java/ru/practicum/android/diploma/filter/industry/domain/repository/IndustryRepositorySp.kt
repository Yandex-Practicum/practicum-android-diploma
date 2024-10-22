package ru.practicum.android.diploma.filter.industry.domain.repository

import ru.practicum.android.diploma.filter.industry.domain.model.IndustryModel

internal interface IndustryRepositorySp {
    suspend fun updateProfessionInDataFilterBuffer(branchOfProfession: IndustryModel): Int
    suspend fun getBranchOfProfessionDataFilterBuffer(): IndustryModel?
}
