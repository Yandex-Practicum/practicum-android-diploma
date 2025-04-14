package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class Area(
    val areas: ArrayList<Area> = arrayListOf(),
    val id: String? = null,
    val name: String? = null,
    @SerializedName("parent_id")
    val parentId: String? = null
)
