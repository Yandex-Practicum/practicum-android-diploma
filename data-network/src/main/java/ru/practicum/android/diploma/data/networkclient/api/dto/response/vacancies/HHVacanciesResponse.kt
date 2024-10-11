package ru.practicum.android.diploma.data.networkclient.api.dto.response.vacancies

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import ru.practicum.android.diploma.commonutils.network.HttpStatus
import ru.practicum.android.diploma.commonutils.network.Response
import ru.practicum.android.diploma.data.networkclient.api.dto.response.vacancies.argument.Argument
import ru.practicum.android.diploma.data.networkclient.api.dto.response.vacancies.item.Item

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
    @Suppress("detekt.DataClassShouldBeImmutable") override var resultCode: HttpStatus,
) : Response, Parcelable
