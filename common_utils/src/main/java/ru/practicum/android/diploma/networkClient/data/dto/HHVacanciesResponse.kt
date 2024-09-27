package ru.practicum.android.diploma.networkclient.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.practicum.android.diploma.networkclient.domain.models.HttpStatus

@Parcelize
data class HHVacanciesResponse(
    val arguments: List<Argument>,
    val clusters: String?,
    val fixes: String?,
    val found: Int,
    val items: List<Item>,
    val page: Int,
    val pages: Int,
    val per_page: Int,
    val suggests: String?,

    override var resultCode: HttpStatus = HttpStatus.OK,
) : Response, Parcelable
