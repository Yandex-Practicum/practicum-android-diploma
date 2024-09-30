package ru.practicum.android.diploma.data.networkclient.api.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HHRegionsResponse(override var resultCode: HttpStatus = HttpStatus.OK) :
    ArrayList<HHRegionsResponseItem>(), Response, Parcelable
