package ru.practicum.android.diploma.data.networkclient.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoVacancy(
    @SerializedName("cover_picture")
    val coverPicture: ru.practicum.android.diploma.data.networkclient.data.dto.CoverPicture,
    @SerializedName("video_url")
    val videoUrl: String,
) : Parcelable
