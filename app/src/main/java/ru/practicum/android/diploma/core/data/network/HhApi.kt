package ru.practicum.android.diploma.core.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.core.data.network.dto.SearchVacanciesResponse

interface HhApi {
    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: JobSeeker/${BuildConfig.VERSION_NAME} (${BuildConfig.DEVELOPER_EMAIL})"
    )
    @GET("vacancies")
    suspend fun getVacancies(@QueryMap queryMap: Map<String, String>): Response<SearchVacanciesResponse>
}

enum class HhApiQuery(val value: String) {
    SEARCH_TEXT("text"),
    PAGE("page"),
    PER_PAGE("per_page"),
    INDUSTRY_FILTER("industry"),
    SALARY_FILTER("salary"),
    IS_ONLY_WITH_SALARY_FILTER("only_with_salary"),
    REGION_FILTER("area")
}
