package ru.practicum.android.diploma.filter.data.dto.model

import com.google.gson.annotations.SerializedName

data class AreaDto( // дерево
    val id: String,
    val name: String,
    @SerializedName("parent_id") val parentId: String? = null,
    val areas: List<AreaDto>? = null,
)
