package ru.practicum.android.diploma.data.networkclient.rest_api.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Employer(
    @SerializedName("accredited_it_employer") val accreditedITEmployer: Boolean,
    @SerializedName("alternate_url") val alternateUrl: String,
    val id: String,
    @SerializedName("logo_urls") val logoUrls: LogoUrls,
    val name: String,
    val trusted: Boolean,
    val url: String,
) : Parcelable
