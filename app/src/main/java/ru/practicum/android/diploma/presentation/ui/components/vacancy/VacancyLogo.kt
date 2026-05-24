package ru.practicum.android.diploma.presentation.ui.components.vacancy

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.ui.theme.Dimens
import ru.practicum.android.diploma.presentation.ui.theme.LightGray

@Composable
fun VacancyLogo(
    logo: String?,
    modifier: Modifier = Modifier,
) {
    val logoShape = RoundedCornerShape(Dimens.vacancyLogoCornerRadius)
    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .data(logo)
        .setHeader(USER_AGENT_HEADER, USER_AGENT_VALUE)
        .build()

    Box(
        modifier = modifier
            .size(Dimens.iconSizeMedium)
            .clip(logoShape)
            .background(MaterialTheme.colorScheme.background)
            .border(
                width = Dimens.vacancyLogoBorderWidth,
                color = LightGray,
                shape = logoShape,
            ),
        contentAlignment = Alignment.Center,
    ) {
        AsyncImage(
            model = imageRequest,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.vacancyLogoPadding),
            contentScale = ContentScale.Fit,
            placeholder = painterResource(R.drawable.ic_placeholder_32),
            error = painterResource(R.drawable.ic_placeholder_32),
            fallback = painterResource(R.drawable.ic_placeholder_32),
        )
    }
}

private const val USER_AGENT_HEADER = "User-Agent"
private const val USER_AGENT_VALUE = "practicum-android-diploma"
