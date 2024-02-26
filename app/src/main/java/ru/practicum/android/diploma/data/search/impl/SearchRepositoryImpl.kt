package ru.practicum.android.diploma.data.search.impl

import ru.practicum.android.diploma.data.Constant
import ru.practicum.android.diploma.data.Convertors
import ru.practicum.android.diploma.data.search.network.JobSearchRequest
import ru.practicum.android.diploma.data.search.network.NetworkClient
import ru.practicum.android.diploma.data.search.network.Resource
import ru.practicum.android.diploma.data.search.network.SearchListDto
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.search.SearchRepository

class SearchRepositoryImpl(private val networkClient: NetworkClient) : SearchRepository {

    override suspend fun search(expression: String, page: Int): Resource<List<Vacancy>> {
        val options = HashMap<String, String>()

        options[Constant.PAGE] = page.toString()
        options[Constant.PER_PAGE] = Constant.PER_PAGE_ITEMS
        options[Constant.TEXT] = expression

        val response = networkClient.search(JobSearchRequest(options))
        return when (response.resultCode) {
            Constant.NO_CONNECTIVITY_MESSAGE -> {
                Resource<List<Vacancy>>(code = Constant.NO_CONNECTIVITY_MESSAGE)
            }
            Constant.SUCCESS_RESULT_CODE -> {
                Resource(
                    (response as SearchListDto).results.map { vacancyDto ->
                        Convertors().convertorToVacancy(vacancyDto)
                    },
                    Constant.SUCCESS_RESULT_CODE
                )

            }

            else -> {
                Resource<List<Vacancy>>(code = Constant.SERVER_ERROR)
            }
        }

    }
}
