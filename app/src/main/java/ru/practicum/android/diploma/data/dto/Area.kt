package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class Area(
    var areas: ArrayList<Area> = arrayListOf(),
    var id: String? = null,
    var name: String? = null,
    @SerializedName("parent_id")
    var parentId: String? = null
)
