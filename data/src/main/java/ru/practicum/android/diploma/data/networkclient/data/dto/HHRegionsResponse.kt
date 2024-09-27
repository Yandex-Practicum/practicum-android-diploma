package ru.practicum.android.diploma.data.networkclient.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.practicum.android.diploma.networkclient.domain.models.HttpStatus

@Parcelize
data class HHRegionsResponse(override val resultCode: HttpStatus = HttpStatus.OK) : ArrayList<ru.practicum.android.diploma.data.networkclient.data.dto.HHRegionsResponseItem>(),
    ru.practicum.android.diploma.data.networkclient.data.dto.Response, Parcelable
