package ru.practicum.android.diploma

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Test
import ru.practicum.android.diploma.core.data.network.ConnectionChecker
import ru.practicum.android.diploma.core.data.network.HhApiRetrofitBuilder
import ru.practicum.android.diploma.core.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.filter.data.FilterRepositoryImpl
import ru.practicum.android.diploma.filter.domain.usecase.GetCountriesUseCase
import ru.practicum.android.diploma.util.Resource

class CountryFilterUseCaseTest {
    private val api = HhApiRetrofitBuilder.buildRetrofit()
    private val connectionChecker: ConnectionChecker = ConnectionChecker {
        true
    }
    private val networkClient = RetrofitNetworkClient(api, connectionChecker)

    @Test
    fun getCountriesNotEmpty() = runBlocking {
        val repository = FilterRepositoryImpl(networkClient)
        val useCase = GetCountriesUseCase(repository)
        useCase.execute().collect {
            assertTrue(it is Resource.Success)
            val result = it as Resource.Success
            Assert.assertNotNull(result.data)
            assertTrue(result.data!!.isNotEmpty())
        }
    }
}
