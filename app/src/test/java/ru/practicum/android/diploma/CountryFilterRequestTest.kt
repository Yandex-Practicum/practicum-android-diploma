package ru.practicum.android.diploma

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import ru.practicum.android.diploma.core.data.network.HhApiProvider
import ru.practicum.android.diploma.core.data.network.dto.CountryDto

class CountryFilterRequestTest {
    @Test
    fun getIndustriesNotEmptyTest() = runBlocking {
        val api = HhApiProvider.buildRetrofit()
        val response = api.getCountries().body()
        assertNotNull(response)
        assertTrue(response is List<CountryDto>)
        assertTrue((response as List<CountryDto>).isNotEmpty())
    }
}
