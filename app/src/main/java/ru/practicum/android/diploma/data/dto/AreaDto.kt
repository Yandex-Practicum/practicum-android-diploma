package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class AreaDto(
    val id: String,
    val name: String?,
    @SerializedName("parent_id")
    val parentId: String?,
    val areas: List<AreaDto>
) : Response()
