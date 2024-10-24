package ru.practicum.android.diploma.filter.industry.domain.repository

import ru.practicum.android.diploma.data.sp.api.SpResult
import ru.practicum.android.diploma.filter.industry.domain.model.IndustryModel

internal interface IndustryRepositorySp {
    suspend fun updateProfessionInDataFilterBuffer(branchOfProfession: IndustryModel): SpResult
    suspend fun getBranchOfProfessionDataFilterBuffer(): IndustryModel?
}
