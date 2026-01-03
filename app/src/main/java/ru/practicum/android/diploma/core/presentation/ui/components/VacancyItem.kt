package ru.practicum.android.diploma.core.presentation.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.imageLoader
import coil3.network.NetworkHeaders
import coil3.network.httpHeaders
import coil3.request.ImageRequest
import coil3.util.DebugLogger
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.presentation.ui.theme.VacancySearchAppTheme
import ru.practicum.android.diploma.core.presentation.ui.theme.dp12
import ru.practicum.android.diploma.core.presentation.ui.theme.dp16

@Composable
fun VacancyItem(
    id: String,
    logoUrl: String?,
    vacancyName: String,
    city: String?,
    employer: String?,
    salary: String?,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClick(id) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(dp16),
            horizontalArrangement = Arrangement.spacedBy(dp12)
        ) {
            VacancyLogo(logoUrl = logoUrl)

            Row(
                modifier = Modifier
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                VacancyTexts(
                    vacancyName = vacancyName,
                    city = city,
                    employer = employer,
                    salary = salary,
                )
            }
        }
    }
}

@Composable
private fun VacancyLogo(logoUrl: String?) {
    val imageLoader = LocalContext.current.imageLoader.newBuilder()
        .logger(DebugLogger())
        .build()

    val header = NetworkHeaders.Builder().set("User-Agent", "Mozilla/5.0").build()

    val imageRequest = ImageRequest
        .Builder(LocalContext.current)
        .data(logoUrl)
        .httpHeaders(header)
        .build()
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = imageRequest,
            contentDescription = null,
            imageLoader = imageLoader,
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
            contentScale = ContentScale.Fit,
            placeholder = painterResource(R.drawable.logo_placeholder),
            error = painterResource(R.drawable.logo_placeholder)
        )
    }
}

@Composable
private fun VacancyTexts(
    vacancyName: String,
    city: String?,
    employer: String?,
    salary: String?,
) {
    Column(
        modifier = Modifier,
    ) {
        Text(
            text = "$vacancyName, $city",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )

        if (!employer.isNullOrEmpty()) {
            Text(
                text = employer,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        if (!salary.isNullOrEmpty()) {
            Text(
                text = salary,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        } else {
            Text(
                text = stringResource(R.string.salary_not_specified),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VacancyItemPreview() {
    VacancySearchAppTheme {
        VacancyItem(
            id = "1",
            logoUrl = null,
            vacancyName = "Java-разработчик",
            city = "Омск",
            employer = "Авто.ру",
            salary = "",
            onClick = {},
        )
    }
}
