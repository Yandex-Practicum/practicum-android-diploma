package ru.practicum.android.diploma.vacancy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.ui.theme.Dimens
import ru.practicum.android.diploma.core.ui.utils.Image

@Composable
fun VacancyHeader(
    name: String,
    salary: String,
    employerName: String,
    employerLogoUrl: String?,
    employerLocation: String?,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth().padding(horizontal = Dimens.padding16)) {
        Text(
            text = name,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(Dimens.padding8))
        Text(
            text = salary,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(Dimens.padding24))
        EmployerCard(
            employerName = employerName,
            employerLogoUrl = employerLogoUrl,
            employerLocation = employerLocation
        )
    }
}

@Composable
private fun EmployerCard(
    employerName: String,
    employerLogoUrl: String?,
    employerLocation: String?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimens.radius12))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(Dimens.padding16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(Dimens.icon48)
                .clip(RoundedCornerShape(Dimens.radius12)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.ic_core_placeholder),
                modifier = Modifier.size(Dimens.icon32)
            )
            if (!employerLogoUrl.isNullOrBlank()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(employerLogoUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(Dimens.icon48)
                        .clip(RoundedCornerShape(Dimens.radius12))
                )
            }
        }
        Spacer(Modifier.width(Dimens.padding12))
        Column {
            Text(
                text = employerName,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            if (!employerLocation.isNullOrBlank()) {
                Text(
                    text = employerLocation,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}
