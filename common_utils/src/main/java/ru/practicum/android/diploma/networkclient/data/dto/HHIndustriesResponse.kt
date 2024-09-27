package ru.practicum.android.diploma.networkclient.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.practicum.android.diploma.networkclient.domain.models.HttpStatus

@Parcelize
class HHIndustriesResponse(override var resultCode: HttpStatus = HttpStatus.OK) : ArrayList<HHIndustriesResponseItem>(), Response,
    Parcelable
