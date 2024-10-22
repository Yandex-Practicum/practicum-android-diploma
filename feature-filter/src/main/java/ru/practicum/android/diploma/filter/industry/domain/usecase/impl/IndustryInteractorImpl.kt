package ru.practicum.android.diploma.filter.industry.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.commonutils.Resource
import ru.practicum.android.diploma.filter.industry.domain.model.IndustryModel
import ru.practicum.android.diploma.filter.industry.domain.repository.IndustryRepositoryNetwork
import ru.practicum.android.diploma.filter.industry.domain.repository.IndustryRepositorySp
import ru.practicum.android.diploma.filter.industry.domain.usecase.IndustryInteractor

internal class IndustryInteractorImpl(
    private val repositoryNetwork: IndustryRepositoryNetwork,
    private val repositorySp: IndustryRepositorySp,
) : IndustryInteractor {
    override fun getIndustriesList(): Flow<Pair<List<IndustryModel>?, String?>> {
        return repositoryNetwork.getIndustriesList().map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.message ?: "")
                }
            }
        }
    }

    override suspend fun updateProfessionInDataFilterBuffer(branchOfProfession: IndustryModel): Int {
        return repositorySp.updateProfessionInDataFilterBuffer(branchOfProfession)
    }

    override suspend fun getBranchOfProfessionDataFilterBuffer(): IndustryModel? {
        return repositorySp.getBranchOfProfessionDataFilterBuffer()
    }
}
