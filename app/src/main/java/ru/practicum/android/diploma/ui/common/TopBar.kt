package ru.practicum.android.diploma.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.Dimens

@Composable
fun TopBar(
    text: String,
    navIconVisible: Boolean,
    endFirstIconVisible: Boolean,
    endFirstIconId: Int = R.drawable.ic_share,
    endSecondIconVisible: Boolean,
    endSecondIconId: Int = R.drawable.ic_like_empty,
    onNavClick: () -> Unit = {},
    onEndFirstIconClick: () -> Unit = {},
    onEndSecondIconClick: () -> Unit = {},
    hasActiveFilter: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.statusBars)
            .height(Dimens.TopBarHeight),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (navIconVisible) {
            IconImage(
                resId = R.drawable.ic_back,
                contentDescription = stringResource(R.string.back),
                onClick = onNavClick,
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge
                .copy(color = MaterialTheme.colorScheme.onBackground),
            modifier = Modifier.padding(start = Dimens.TopBarTitleStartPadding),
        )
        if (endFirstIconVisible || endSecondIconVisible) {
            Spacer(modifier = Modifier.weight(1f))
        }
        if (endFirstIconVisible) {
            IconImage(
                resId = endFirstIconId,
                contentDescription = stringResource(R.string.share),
                onClick = onEndFirstIconClick,
            )
        }
        if (endSecondIconVisible) {
            IconImage(
                resId = endSecondIconId,
                contentDescription = stringResource(R.string.add_to_favorites),
                onClick = onEndSecondIconClick,
                color = if (endSecondIconId == R.drawable.ic_like_red) MaterialTheme.colorScheme.error else null
            )
        }
    }
}

@Composable
fun IconImage(
    resId: Int,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    onClick: (() -> Unit)? = null,
    color: Color? = null
) {
    Box(
        modifier = modifier
            .height(Dimens.TopBarIconSize)
            .width(Dimens.TopBarIconSize)
            .then(
                if (onClick != null) {
                    Modifier.clickable(onClick = onClick)
                } else {
                    Modifier
                },
            ),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = resId),
            contentDescription = contentDescription,
            colorFilter = ColorFilter.tint(color ?: MaterialTheme.colorScheme.onBackground),
        )
    }
}
