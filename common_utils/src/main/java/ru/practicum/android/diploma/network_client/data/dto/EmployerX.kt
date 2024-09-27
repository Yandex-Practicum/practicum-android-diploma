package ru.practicum.android.diploma.network_client.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EmployerX(
    val alternate_url: String,
    val blacklisted: Boolean,
    val id: String,
    val logo_urls: LogoUrlsX,
    val name: String,
    val trusted: Boolean,
    val url: String,
) : Parcelable
