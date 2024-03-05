package ru.practicum.android.diploma.core.data.network.dto

import com.google.gson.annotations.SerializedName

data class AreasDto (
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("parent_id")
    val parentId: String?,
    @SerializedName("areas")
    val areas: List<AreasDto>?
)
