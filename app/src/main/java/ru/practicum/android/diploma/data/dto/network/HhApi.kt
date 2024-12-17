package ru.practicum.android.diploma.data.dto.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import ru.practicum.android.diploma.data.dto.VacancySearchResponse
import ru.practicum.android.diploma.data.dto.model.VacancyItemDto

interface HhApi {

    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancyById(@Path("vacancy_id") vacancyId: String): VacancyItemDto

    @GET("/vacancies")
    suspend fun getVacancies(
        @Query("text") searchQuery: String, // Название вакансии
        @Query("area") nameOfCityForFilter: String? = null, // Тут нужно передать id региона из справочника hh.ru
        @Query("industry") nameOfIndustryForFilter: String? = null, // Тут id отрасли (можно задать несколько, но нам вроде не нужно)
        @Query("only_with_salary") onlyWithSalary: Boolean = false, // Тут ставим true для вакансий только с зарплатой
        @Query("currency") currencyOfSalary: String? = null, // Тут задается валюта (они тоже в справочнике)
        @Query("salary") expectedSalary: String? = null, // Требуемая зарплата (чисто число)
        @Query("per_page") numberOfVacanciesOnPage: String = "20", // Количество вакансий на страницу(у нас 20)
        @Query("page") numberOfPage: String // Номер нужной страницы списка вакасий
    ): VacancySearchResponse
}
