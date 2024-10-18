package ru.practicum.android.diploma.filter.industry.domain.repository

import ru.practicum.android.diploma.filter.industry.domain.model.IndustryModel

interface IndustryRepositorySp {
    suspend fun updateProfessionInDataFilter(branchOfProfession: IndustryModel): Int
    suspend fun getBranchOfProfessionDataFilter(): IndustryModel?
}
