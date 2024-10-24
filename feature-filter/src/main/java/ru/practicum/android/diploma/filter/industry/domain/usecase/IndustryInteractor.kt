package ru.practicum.android.diploma.filter.industry.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.industry.domain.model.IndustryModel

internal interface IndustryInteractor {
    fun getIndustriesList(): Flow<Pair<List<IndustryModel>?, String?>>
    suspend fun updateProfessionInDataFilterBuffer(branchOfProfession: IndustryModel): Int
    suspend fun getBranchOfProfessionDataFilterBuffer(): IndustryModel?
}
