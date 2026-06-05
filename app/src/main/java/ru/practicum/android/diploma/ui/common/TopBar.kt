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
    endFirstIcon: IconResource? = null,
    endSecondIconVisible: Boolean,
    endSecondIcon: IconResource? = null,
    onNavClick: () -> Unit = {},
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
                icon = IconResource.DefaultIcon(
                    resId = R.drawable.ic_back,
                    contentDescriptionStringId = R.string.back,
                    onClick = onNavClick,
                    color = MaterialTheme.colorScheme.onBackground
                )
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
                icon = endFirstIcon!!
            )
        }
        if (endSecondIconVisible) {
            IconImage(
                icon = endSecondIcon!!
            )
        }
    }
}

@Composable
fun IconImage(
    modifier: Modifier = Modifier,
    icon: IconResource
) {
    Box(
        modifier = modifier
            .height(Dimens.TopBarIconSize)
            .width(Dimens.TopBarIconSize)
            .then(
                if (icon.onClick != null) {
                    Modifier.clickable(onClick = icon.onClick)
                } else {
                    Modifier
                },
            ),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = icon.resId),
            contentDescription = if (icon.contentDescriptionStringId != null) {
                stringResource(icon.contentDescriptionStringId)
            } else {
                null
            },
            colorFilter = icon.color?.let { ColorFilter.tint(icon.color) }
        )
    }
}
