package ru.practicum.android.diploma.data.networkclient.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.practicum.android.diploma.search.domain.model.HttpStatus

@Parcelize
data class HHRegionsResponse(override val resultCode: HttpStatus = HttpStatus.OK) : ArrayList<HHRegionsResponseItem>(),
    Response, Parcelable
