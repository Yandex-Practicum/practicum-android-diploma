package ru.practicum.android.diploma.data.response

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.Response

data class AreasResponse(
    val area: List<AreaDto>
) : Response()

data class AreaDto(
    val id: String,
    @SerializedName("parent_id")
    val parentId: String?,
    val name: String?,
    val areas: List<AreaDto>
) : Response()
