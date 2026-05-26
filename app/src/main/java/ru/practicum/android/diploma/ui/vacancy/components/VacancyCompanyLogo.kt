package ru.practicum.android.diploma.ui.vacancy.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import coil3.network.NetworkHeaders
import coil3.network.httpHeaders
import coil3.request.ImageRequest
import coil3.request.crossfade
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.Dimens

@Composable
fun VacancyCompanyLogo(
    logoUrl: String?,
    modifier: Modifier = Modifier,
) {
    val imageModifier = modifier
        .size(Dimens.VacancyCompanyLogoSize)
        .clip(RoundedCornerShape(Dimens.VacancyCardCornerRadius))
    val context = LocalContext.current
    val imageRequest = remember(logoUrl) {
        ImageRequest.Builder(context)
            .data(logoUrl)
            .crossfade(true)
            .httpHeaders(
                NetworkHeaders.Builder()
                    .set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    .build(),
            )
            .build()
    }

    if (logoUrl.isNullOrBlank()) {
        Image(
            modifier = imageModifier,
            painter = painterResource(R.drawable.ic_logo_48),
            contentDescription = null,
        )
    } else {
        AsyncImage(
            modifier = imageModifier,
            model = imageRequest,
            contentDescription = null,
            placeholder = painterResource(R.drawable.ic_logo_48),
            error = painterResource(R.drawable.ic_logo_48),
            contentScale = ContentScale.Fit,
        )
    }
}
