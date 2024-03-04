package ru.practicum.android.diploma.data.dto.fields

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.search.network.Response

data class CountryDto(
    val id: String,
    @SerializedName("parent_id")
    val parentId: String?,
    val name: String?,
) : Response()
