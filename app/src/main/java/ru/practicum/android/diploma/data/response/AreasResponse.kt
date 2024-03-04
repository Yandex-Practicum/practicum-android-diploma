package ru.practicum.android.diploma.data.response

import com.google.gson.annotations.SerializedName

data class AreasResponse(
    val id: String,
    @SerializedName("parent_id")
    val parentId: String?,
    val name: String?,
    val areas: List<AreasResponse>
)
