package ru.practicum.android.diploma.data.networkclient.rest_api.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import ru.practicum.android.diploma.search.domain.model.HttpStatus

@Parcelize
data class HHVacanciesResponse(
    val arguments: List<Argument>,
    val clusters: String?,
    val fixes: String?,
    val found: Int,
    val items: List<Item>,
    val page: Int,
    val pages: Int,
    @SerializedName("per_page") val perPage: Int,
    val suggests: String?,

    override val resultCode: HttpStatus = HttpStatus.OK,
) : Response, Parcelable
