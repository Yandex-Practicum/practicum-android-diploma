package ru.practicum.android.diploma.ui.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import ru.practicum.android.diploma.R

sealed class IconResource(
    val resId: Int,
    val onClick: (() -> Unit)? = null,
    val color: Color? = null,
    val contentDescriptionStringId: Int? = null
) {
    class LikeIcon(isActive: Boolean, onClick: () -> Unit, defaultColor: Color) :
        IconResource(
            resId = if (isActive) R.drawable.ic_like_red else R.drawable.ic_like_empty,
            onClick = onClick,
            contentDescriptionStringId = R.string.add_to_favorites,
            color = if (isActive) null else defaultColor
        )

    class FilterIcon(isActive: Boolean, onClick: () -> Unit, defaultColor: Color) :
        IconResource(
            resId = if (isActive) R.drawable.ic_filter_active else R.drawable.ic_filter,
            onClick = onClick,
            contentDescriptionStringId = R.string.vacancies_filter,
            color = if (isActive) null else defaultColor
        )

    class DefaultIcon(
        @DrawableRes resId: Int,
        onClick: (() -> Unit)? = null,
        color: Color,
        @StringRes contentDescriptionStringId: Int? = null
    ) :
        IconResource(
            resId = resId,
            onClick = onClick,
            color = color,
            contentDescriptionStringId = contentDescriptionStringId
        )
}
