package ru.practicum.android.diploma.networkclient.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoVacancy(
    @SerializedName("cover_picture")
    val coverPicture: CoverPicture,
    @SerializedName("video_url")
    val videoUrl: String,
) : Parcelable
