package ru.practicum.android.diploma.domain.vacancydetails.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.vacancydetails.api.GetVacancyDetailsUseCase
import ru.practicum.android.diploma.domain.vacancydetails.api.VacancyDetailsRepository

class GetVacancyDetailsUseCaseImpl(
    private val vacancyDetailsRepository: VacancyDetailsRepository
) : GetVacancyDetailsUseCase {

    override fun execute(vacancyId: String): Flow<Pair<Vacancy?, String?>> {
        return vacancyDetailsRepository.searchVacancyById(vacancyId).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.value, null)
                }

                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }

//        return vacancyRepository.getVacancy(id).map { result ->
//            when (result) {
//                is Resource.Success -> {
//                    result.data
//                }
//
//                is Resource.Error -> {
//                    null
//                }
//            }
//        }
    }
}
