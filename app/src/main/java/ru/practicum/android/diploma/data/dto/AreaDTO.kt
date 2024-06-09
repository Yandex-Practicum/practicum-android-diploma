package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class AreaDTO(
    val id: String,
    @SerializedName("parent_id")
    val parentId: String?,
    val name: String,
    val areas: List<AreaDTO>
)
