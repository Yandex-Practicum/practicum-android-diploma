package ru.practicum.android.diploma.networkClient.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Employer(
    val accredited_it_employer: Boolean,
    val alternate_url: String,
    val id: String,
    val logo_urls: LogoUrls,
    val name: String,
    val trusted: Boolean,
    val url: String,
) : Parcelable
