package ru.practicum.android.diploma.core.ui.utils

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.domain.models.Vacancy
import ru.practicum.android.diploma.core.ui.preview.mockList
import ru.practicum.android.diploma.core.ui.theme.AppTheme
import ru.practicum.android.diploma.core.ui.theme.Dimens
import ru.practicum.android.diploma.core.ui.theme.LightGray

@Composable
fun VacancyCard(
    vacancy: Vacancy,
    onClick: (Vacancy) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = Dimens.padding16, vertical = Dimens.padding9)
            .clickable { onClick(vacancy) },
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(Dimens.icon48)
                .clip(RoundedCornerShape(Dimens.radius12))
                .border(
                    width = Dimens.border1,
                    color = LightGray,
                    shape = RoundedCornerShape(Dimens.radius12)
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.ic_core_placeholder),
                modifier = Modifier
                    .size(Dimens.icon32)
            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(vacancy.employerLogoUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(Dimens.icon48)
                    .clip(RoundedCornerShape(Dimens.radius12))

            )
        }
        Spacer(Modifier.width(Dimens.padding12))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = vacancy.name,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = vacancy.employerName,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = vacancy.salary,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun VacancyCardPreview() {
    AppTheme {
        Column() {
            Vacancy.mockList().forEach { vacancy ->
                VacancyCard(vacancy) { }
            }
        }
    }
}
