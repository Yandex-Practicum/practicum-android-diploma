package ru.practicum.android.diploma.ui.common.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import coil3.network.NetworkHeaders
import coil3.network.httpHeaders
import coil3.request.ImageRequest
import coil3.request.crossfade
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.mocks.MocData
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.util.extentions.formatDescription
import ru.practicum.android.diploma.util.extentions.formatSalary

@Composable
fun VacancyItem(
    modifier: Modifier = Modifier,
    vacancy: Vacancy,
    onClick: (String) -> Unit
) {
    val vacancyDescription = vacancy.formatDescription()
    val salary = vacancy.salary.formatSalary()
    val imageModifier = Modifier
        .size(48.dp)
        .clip(RoundedCornerShape(12.dp))
    val context = LocalContext.current
    val imageRequest = remember(vacancy.logo) {
        ImageRequest.Builder(context)
            .data(vacancy.logo)
            .crossfade(true)
            .httpHeaders(
                NetworkHeaders.Builder()
                    .set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    .build()
            )
            .build()
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                onClick = { onClick(vacancy.id) }
            )
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (vacancy.logo != null) {
            AsyncImage(
                modifier = imageModifier,
                model = imageRequest,
                contentDescription = null,
                placeholder = painterResource(R.drawable.ic_logo_48),
                contentScale = ContentScale.Fit,
                error = painterResource(R.drawable.ic_logo_48)
            )
        } else {
            Image(
                modifier = imageModifier,
                painter = painterResource(R.drawable.ic_logo_48),
                contentDescription = null
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = vacancyDescription,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = vacancy.company ?: stringResource(R.string.no_company),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = salary,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun VacancyItemPreview() {
    AppTheme {
        VacancyItem(
            modifier = Modifier.fillMaxWidth(),
            MocData.vacancy,
            onClick = {}
        )
    }
}
