package ru.practicum.android.diploma.ui.team.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale

data class TeamMember(
    val name: String,
    @DrawableRes val avatarResId: Int,
    val role: String,
    @DrawableRes val roleIconResId: Int,
    val roleColor: Color,
    val roleBackgroundColor: Color,
    val avatarContentScale: ContentScale = ContentScale.Crop,
)
