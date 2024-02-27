package ru.practicum.android.diploma

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import ru.practicum.android.diploma.core.data.NetworkClient
import ru.practicum.android.diploma.core.data.network.HhApiProvider
import ru.practicum.android.diploma.core.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.core.domain.model.SearchFilterParameters
import ru.practicum.android.diploma.search.data.SearchVacancyRepositoryImpl
import ru.practicum.android.diploma.search.domain.api.SearchVacancyRepository
import ru.practicum.android.diploma.search.domain.usecase.SearchVacancyUseCase
import ru.practicum.android.diploma.util.Resource

class SearchVacancyUseCaseTest {
    private val networkClient: NetworkClient =
        RetrofitNetworkClient(HhApiProvider.hhService) { true }
    private val searchVacancyRepository: SearchVacancyRepository = SearchVacancyRepositoryImpl(networkClient)
    private val searchVacancyUseCase = SearchVacancyUseCase(searchVacancyRepository)

    @Test
    fun searchWithoutFilters_isCorrect() = runBlocking {
        searchVacancyUseCase.execute(
            searchText = "Android",
            page = 0,
            filterParameters = SearchFilterParameters()
        ).collect {
            assertTrue(it is Resource.Success)
            val result = it as Resource.Success
            assertNotNull(result.data)
            assertTrue(result.data!!.vacancies.isNotEmpty())
            assertEquals(20, result.data!!.vacancies.size)
        }
    }
}
