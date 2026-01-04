package ru.practicum.android.diploma.vacancy.details.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.presentation.ui.theme.CustomTypography
import ru.practicum.android.diploma.core.presentation.ui.theme.corner12
import ru.practicum.android.diploma.core.presentation.ui.theme.dp12
import ru.practicum.android.diploma.core.presentation.ui.theme.dp4
import ru.practicum.android.diploma.core.presentation.ui.theme.dp48
import ru.practicum.android.diploma.core.presentation.ui.theme.lightGrey
import ru.practicum.android.diploma.core.presentation.ui.theme.white
import ru.practicum.android.diploma.search.domain.model.Address
import ru.practicum.android.diploma.search.domain.model.Employer
import ru.practicum.android.diploma.search.domain.model.FilterArea
import ru.practicum.android.diploma.search.domain.model.FilterIndustry
import ru.practicum.android.diploma.search.domain.model.Salary
import ru.practicum.android.diploma.search.domain.model.VacancyDetail

@Composable
fun EmployerCard(vacancy: VacancyDetail) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(corner12))
            .background(lightGrey),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val context = LocalContext.current

        Box(
            modifier = Modifier
                .padding(start = 16.dp)
                .size(dp48)
                .clip(RoundedCornerShape(corner12))
                .background(white)
                .border(
                    width = 1.dp,
                    color = lightGrey,
                    shape = RoundedCornerShape(corner12)
                ),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(vacancy.employer.logo)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.logo_placeholder),
                error = painterResource(R.drawable.logo_placeholder),
                contentDescription = "company logo",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dp4),
                contentScale = ContentScale.Fit
            )
        }

        Column(modifier = Modifier.padding(start = dp12)) {
            Text(
                text = vacancy.employer.name,
                color = MaterialTheme.colorScheme.onBackground,
                style = CustomTypography.headlineMedium
            )

            if (vacancy.address?.city != null) {
                Text(
                    text = vacancy.address.city,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = CustomTypography.titleMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmployerItemPreview() {
    val testVacancy = VacancyDetail(
        id = "1",
        name = "Android Developer",
        description = "Разработка Android-приложений",
        url = "https://example.com",
        employer = Employer(
            id = "123",
            name = "Компания",
            logo = "https://via.placeholder.com/150"
        ),
        address = Address(
            city = "Москва",
            street = "Тверская",
            building = "1",
            fullAddress = "Москва, Тверская, 1"
        ),
        salary = Salary(
            from = 100_000,
            to = 150_000,
            currency = "RUB"
        ),
        experience = null,
        schedule = null,
        employment = null,
        contacts = null,
        area = FilterArea(
            id = 1,
            name = "Москва",
            parentId = null,
            areas = emptyList()
        ),
        skills = listOf("Kotlin", "Jetpack Compose"),
        industry = FilterIndustry(id = 1, name = "IT")
    )

    EmployerCard(vacancy = testVacancy)
}
