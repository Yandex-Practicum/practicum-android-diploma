package ru.practicum.android.diploma.ui.common

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import coil3.compose.AsyncImage

import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.util.extentions.formatDescription
import ru.practicum.android.diploma.util.extentions.formatSalary

@Composable
fun VacancyItem(modifier: Modifier = Modifier, vacancy: Vacancy, onClick: () -> Unit = {}) {
    val vacancyDescription = vacancy.formatDescription()
    val salary = vacancy.salary.formatSalary()
    val imageModifier = Modifier
        .size(48.dp)
        .clip(RoundedCornerShape(12.dp))

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick
            )
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (vacancy.logo != null) {
            AsyncImage(
                modifier = imageModifier,
                model = vacancy.logo,
                contentDescription = null,
                placeholder = painterResource(R.drawable.ic_logo_48),
                contentScale = ContentScale.Crop,
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

private const val SALARY_FROM = 10_000
private const val SALARY_TO = 20_000
private val vacancyCard: Vacancy = Vacancy(
    id = "wfwe",
    name = "Android-разработчик",
    company = "Еда",
    city = "Мoсква",
    salary = Salary(SALARY_FROM, SALARY_TO, "Р"),
    logo = null
)

@Preview(showSystemUi = true)
@Composable
private fun VacancyItemPreview() {
    AppTheme {
        VacancyItem(modifier = Modifier.fillMaxWidth(), vacancyCard)
    }
}
