package ru.practicum.android.diploma.data.networkclient.api.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HHIndustriesResponse(
    override val resultCode: HttpStatus = HttpStatus.OK,
) : ArrayList<HHIndustriesResponseItem>(), Response, Parcelable
