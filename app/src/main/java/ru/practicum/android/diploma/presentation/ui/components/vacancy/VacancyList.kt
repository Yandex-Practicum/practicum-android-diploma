package ru.practicum.android.diploma.presentation.ui.components.vacancy

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import ru.practicum.android.diploma.presentation.ui.theme.Dimens

@Composable
fun VacancyList(
    vacancies: List<VacancyUiModel>,
    onVacancyClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(Dimens.paddingDefault),
    ) {
        items(
            items = vacancies,
            key = VacancyUiModel::id,
        ) { vacancy ->
            VacancyListItem(
                vacancy = vacancy,
                onClick = { onVacancyClick(vacancy.id) },
            )
        }
    }
}

@Composable
fun VacancyListItem(
    vacancy: VacancyUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.Top,
    ) {
        VacancyLogo(logo = vacancy.logo)
        Spacer(modifier = Modifier.width(Dimens.paddingMedium))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = vacancy.title,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 25.sp,
                ),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
            vacancy.company?.takeIf(String::isNotBlank)?.let { company ->
                Text(
                    text = company,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 20.sp,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Text(
                text = vacancy.salary,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 20.sp,
                ),
            )
        }
    }
}
