package ru.practicum.android.diploma.network_client.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class HHRegionsResponse(override var resultCode: Int = 0) : ArrayList<HHRegionsResponseItem>(), Response, Parcelable

