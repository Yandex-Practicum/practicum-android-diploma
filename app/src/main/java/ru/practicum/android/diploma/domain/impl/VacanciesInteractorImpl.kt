package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.VacanciesRepository
import ru.practicum.android.diploma.domain.models.FilterOptions
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.vacancy.VacanciesInteractor
import ru.practicum.android.diploma.domain.vacancy.models.VacancyDetail
import ru.practicum.android.diploma.util.Resource

class VacanciesInteractorImpl(
    private val repository: VacanciesRepository
) : VacanciesInteractor{
    override fun searchVacancies(
        searchText: String,
        area: String,
        industry: String,
        currency: String,
        salary: Int?,
        onlyWithSalary: Boolean
    ): Flow<Resource<List<Vacancy>>> {
        val options = FilterOptions(
            text = searchText,
            area = area,
            industry = industry,
            currency = currency,
            salary = salary?.toString() ?: "",
            onlyWithSalary = onlyWithSalary.toString(),
        )
        return repository.searchVacancies(options)
    }

    override fun getVacancy(vacancyId: String): Flow<Resource<VacancyDetail>> {
        return repository.getVacancy(vacancyId)
    }
}
