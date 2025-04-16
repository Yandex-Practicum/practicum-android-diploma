package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class AreaDto(
//    val areas: ArrayList<AreaDto> = arrayListOf(),  !это надо менять
    val id: String? = null,
    val name: String? = null,
    @SerializedName("parent_id")
    val parentId: String? = null
)
