package ru.practicum.android.diploma.data.networkclient.api.dto.response.industries

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.practicum.android.diploma.commonutils.network.HttpStatus
import ru.practicum.android.diploma.commonutils.network.Response
import ru.practicum.android.diploma.data.networkclient.api.dto.response.industries.item.HHIndustriesResponseItem

@Parcelize
class HHIndustriesResponse(
    override var resultCode: HttpStatus = HttpStatus.OK,
) : ArrayList<HHIndustriesResponseItem>(), Response, Parcelable
