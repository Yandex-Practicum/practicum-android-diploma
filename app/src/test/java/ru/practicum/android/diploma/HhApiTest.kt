package ru.practicum.android.diploma

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import ru.practicum.android.diploma.core.data.network.HhApiProvider

class HhApiTest {
    @Test
    fun getVacancies_isWork() = runBlocking {
        val response = HhApiProvider.hhService.getVacancies(emptyMap())
        assertEquals(200, response.code())
    }

    @Test
    fun getOnePageVacancies_isCorrect() = runBlocking {
        val response = HhApiProvider.hhService.getVacancies(
            mapOf(
                "page" to "0",
                "per_page" to "20"
            )
        )
        assertEquals("response is not successful", 200, response.code())
        assertTrue("response body = null", response.body() != null)
        assertEquals(20, response.body()!!.vacancies.size)
    }

    @Test
    fun getVacanciesBySearchQuery_isCorrect() = runBlocking {
        val response = HhApiProvider.hhService.getVacancies(
            mapOf(
                "page" to "0",
                "per_page" to "20",
                "text" to "Android"
            )
        )
        assertEquals("response is not successful", 200, response.code())
        assertTrue("response body = null", response.body() != null)
        assertTrue(response.body()!!.vacancies.isNotEmpty())
    }
}
