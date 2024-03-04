package ru.practicum.android.diploma.filter.industyFilter

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import ru.practicum.android.diploma.core.data.network.HhApiRetrofitBuilder
import ru.practicum.android.diploma.core.data.network.dto.IndustryDto

class IndustryFilterRequestTest {
    @Test
    fun getIndustriesNotEmptyTest() = runBlocking {
        val api = HhApiRetrofitBuilder.buildRetrofit()
        val response = api.getIndustries().body()
        assertNotNull(response)
        assertTrue(response is List<IndustryDto>)
        assertTrue((response as List<IndustryDto>).isNotEmpty())
    }
}
