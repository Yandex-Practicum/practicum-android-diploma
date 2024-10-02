package ru.practicum.android.diploma.data.networkclient.api.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class HHIndustriesResponse(
    override var resultCode: HttpStatus = HttpStatus.OK,
) : ArrayList<HHIndustriesResponseItem>(), Response, Parcelable
