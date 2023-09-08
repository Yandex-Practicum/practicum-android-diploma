package ru.practicum.android.diploma.search.data.network


import ru.practicum.android.diploma.filter.data.model.CountryDto
import ru.practicum.android.diploma.search.data.network.dto.response.VacanciesResponse
import ru.practicum.android.diploma.util.functional.Either
import ru.practicum.android.diploma.util.functional.Failure

//@NewResponse
interface AlternativeRemoteDataSource {
    suspend fun getVacancies(query: String): Either<Failure, VacanciesResponse>
    suspend fun getAllCountries() : Either<Failure, List<CountryDto>>
}