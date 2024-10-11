package ru.practicum.android.diploma.data.networkclient.api.dto.response.common.insider

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InsiderInterview(
    val id: String,
    val url: String,
) : Parcelable
