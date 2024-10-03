package ru.practicum.android.diploma.data.networkclient.api.dto.response.regions

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.practicum.android.diploma.commonutils.network.HttpStatus
import ru.practicum.android.diploma.commonutils.network.Response
import ru.practicum.android.diploma.data.networkclient.api.dto.response.regions.item.HHRegionsResponseItem

@Parcelize
class HHRegionsResponse(override var resultCode: HttpStatus = HttpStatus.OK) : ArrayList<HHRegionsResponseItem>(),
    Response, Parcelable
