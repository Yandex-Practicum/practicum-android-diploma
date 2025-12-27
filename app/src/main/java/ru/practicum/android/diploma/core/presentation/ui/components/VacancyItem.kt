package ru.practicum.android.diploma.core.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.presentation.ui.theme.VacancySearchAppTheme
import ru.practicum.android.diploma.core.presentation.ui.theme.dp12
import ru.practicum.android.diploma.core.presentation.ui.theme.dp16
import ru.practicum.android.diploma.core.presentation.ui.theme.dp48

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
    if (!logoUrl.isNullOrEmpty()) {
        AsyncImage(
            model = logoUrl,
            contentDescription = null,
            modifier = Modifier.size(dp48)
        )
    } else {
        Icon(
            painter = painterResource(R.drawable.logo_placeholder),
            contentDescription = null,
            tint = Color.Unspecified
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
