package ru.practicum.android.diploma.common.data.dto.region

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.common.data.dto.Response
import ru.practicum.android.diploma.filter.data.dto.model.AreaDto

data class SearchRegionResponse(
    @SerializedName("id") val id: Int, // id региона
    @SerializedName("parent_id") val parentId: Int? = null, // id региона в область которого входит регион
    @SerializedName("name") val name: String, // название региона
    @SerializedName("areas") val areas: List<AreaDto>? = null, // список входящих регионов
) : Response()
