package ru.practicum.android.diploma.domain.country

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.data.country.dto.AreaDto

@Serializable
data class Country(
    val id: String,
    @SerializedName("parent_id")
    val parentId: String?,
    val name: String?,
    val areas: List<AreaDto>
)
