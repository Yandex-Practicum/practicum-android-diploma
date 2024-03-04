package ru.practicum.android.diploma.filter.industyFilter

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test
import ru.practicum.android.diploma.core.data.network.ConnectionChecker
import ru.practicum.android.diploma.core.data.network.HhApiRetrofitBuilder
import ru.practicum.android.diploma.core.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.filter.industry.data.IndustryRepositoryImpl
import ru.practicum.android.diploma.filter.industry.domain.model.IndustryError
import ru.practicum.android.diploma.filter.industry.domain.usecase.GetIndustriesByTextUseCase
import ru.practicum.android.diploma.util.Result

class IndustryFilterUseCaseTest {
    private val api = HhApiRetrofitBuilder.buildRetrofit()
    private val connectionChecker: ConnectionChecker = ConnectionChecker {
        true
    }
    private val networkClient = RetrofitNetworkClient(api, connectionChecker)

    @Test
    fun getIndustriesNotEmpty() = runBlocking {
        val repository = IndustryRepositoryImpl(networkClient)
        val useCase = GetIndustriesByTextUseCase(repository)
        useCase.execute("").collect {
            assertTrue(it is Result.Success)
            assertTrue((it as Result.Success).data.isNotEmpty())
        }
    }

    @Test
    fun searchExistItemNotEmpty() = runBlocking {
        val repository = IndustryRepositoryImpl(networkClient)
        val useCase = GetIndustriesByTextUseCase(repository)
        useCase.execute("").collect {
            assertTrue(it is Result.Success)
            val data = (it as Result.Success).data
            assertTrue(data.isNotEmpty())
            useCase.execute(data.first().name).collect { filteredResult ->
                assertTrue(filteredResult is Result.Success)
                val filteredData = (filteredResult as Result.Success).data
                assertTrue(filteredData.isNotEmpty())
            }
        }
    }

    @Test
    fun searchNotExistItem() = runBlocking {
        val repository = IndustryRepositoryImpl(networkClient)
        val useCase = GetIndustriesByTextUseCase(repository)
        useCase.execute("amdmalkdmla12i3091931").collect {
            assertTrue(it is Result.Error)
            assertTrue((it as Result.Error).errorType is IndustryError.NotFound)
        }
    }
}
