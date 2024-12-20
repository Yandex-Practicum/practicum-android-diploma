package ru.practicum.android.diploma.data.dto.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.practicum.android.diploma.data.dto.VacancySearchResponse
import ru.practicum.android.diploma.data.dto.model.VacancyFullItemDto

interface HhApi {

    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancyById(@Path("vacancy_id") vacancyId: String): VacancyFullItemDto

    @GET("/vacancies")
    suspend fun getVacancies(
        // Название вакансии
        @Query("text") searchQuery: String,
        // Тут нужно передать id региона из справочника hh.ru
        @Query("area") nameOfCityForFilter: String? = null,
        // Тут id отрасли (можно задать несколько, но нам вроде не нужно)
        @Query("industry") nameOfIndustryForFilter: String? = null,
        // Тут ставим true для вакансий только с зарплатой
        @Query("only_with_salary") onlyWithSalary: Boolean = false,
        // Тут задается валюта (они тоже в справочнике)
        @Query("currency") currencyOfSalary: String? = null,
        // Требуемая зарплата (чисто число)
        @Query("salary") expectedSalary: String? = null,
        // Количество вакансий на страницу(у нас 20)
        @Query("per_page") numberOfVacanciesOnPage: String = "20",
        // Номер нужной страницы списка вакасий
        @Query("page") numberOfPage: String
    ): VacancySearchResponse
}
