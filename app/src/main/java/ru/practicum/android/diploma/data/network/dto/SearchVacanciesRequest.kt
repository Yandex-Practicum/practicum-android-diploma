package ru.practicum.android.diploma.data.network.dto

import com.google.gson.annotations.SerializedName

//  @SerializedName  по сути не используется, просто оставил, чтобы не забыть, как вставлять в запрос

data class SearchVacanciesRequest(
    val text: String,

    @SerializedName("area")
    val areaIDs: MutableList<String>? = null,

    @SerializedName("industry")
    val industryIDs: MutableList<String>? = null,

    val salary: UInt? = null,

    @SerializedName("only_with_salary")
    val onlyWithSalary: Boolean? = null,

    @Suppress("DataClassShouldBeImmutable")
    var page: Int, //  оставил var, чтобы переиспользовать запрос при дозагрузке страниц

    @SerializedName("per_page")
    val perPage: Int = 20,

)

fun SearchVacanciesRequest.toQueryParams(): Map<String, String> {
    var params: HashMap<String, String> = HashMap()
    params["text"] = text
    params["page"] = page.toString()
    params["per_page"] = perPage.toString()

    if (onlyWithSalary != null) {
        params["only_with_salary"] = onlyWithSalary.toString().lowercase()
    }

    if (!areaIDs.isNullOrEmpty()) {
        params["area"] = areaIDs.joinToString(",")
    }

    if (!industryIDs.isNullOrEmpty()) {
        params["industry"] = industryIDs.joinToString(",")
    }

    if (salary != null && salary > 0u) {
        params["salary"] = salary.toString()
    }

    return params
}
