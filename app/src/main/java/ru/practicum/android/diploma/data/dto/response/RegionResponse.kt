package ru.practicum.android.diploma.data.dto.response

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.dto.Response

data class RegionResponse(
    val areas: List<RegionResponse>,
    val id: String? = null,
    val name: String? = null,
    @SerializedName("parent_id") val parentId: String? = null
) : Response()
