package ru.practicum.android.diploma.filter.areaFilter

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import ru.practicum.android.diploma.core.data.network.ConnectionChecker
import ru.practicum.android.diploma.core.data.network.HhApiRetrofitBuilder
import ru.practicum.android.diploma.core.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.filter.area.data.AreaRepositoryImpl
import ru.practicum.android.diploma.filter.area.domain.model.AreaError
import ru.practicum.android.diploma.filter.area.domain.usecase.GetAreasByTextUseCase
import ru.practicum.android.diploma.util.Result

class AreaFilterRequestTest {
    private val api = HhApiRetrofitBuilder.buildRetrofit()
    private val connectionChecker: ConnectionChecker = ConnectionChecker {
        true
    }
    private val networkClient = RetrofitNetworkClient(api, connectionChecker)

    @Test
    fun getAreasNotEmpty() = runBlocking {
        val repository = AreaRepositoryImpl(networkClient)
        val useCase = GetAreasByTextUseCase(repository)
        useCase.execute("", "").collect {
            Assert.assertTrue(it is Result.Success)
            Assert.assertTrue((it as Result.Success).data.isNotEmpty())
        }


    }

    @Test
    fun searchExistItemNotEmpty() = runBlocking {
        val repository = AreaRepositoryImpl(networkClient)
        val useCase = GetAreasByTextUseCase(repository)
        useCase.execute("Москва", "").collect {
            Assert.assertTrue(it is Result.Success)
            val data = (it as Result.Success).data
            Assert.assertTrue(data.isNotEmpty())
        }
    }

    @Test
    fun searchNotExistItem() = runBlocking {
        val repository = AreaRepositoryImpl(networkClient)
        val useCase = GetAreasByTextUseCase(repository)
        useCase.execute("amdmalkdmla12i3091931", "").collect {
            Assert.assertTrue(it is Result.Error)
            Assert.assertTrue((it as Result.Error).errorType is AreaError.NotFound)
        }
    }
}
