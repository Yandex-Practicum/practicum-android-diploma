package ru.practicum.android.diploma.vacancy.details.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.presentation.ui.theme.CustomTypography
import ru.practicum.android.diploma.core.presentation.ui.theme.black
import ru.practicum.android.diploma.core.presentation.ui.theme.grey
import ru.practicum.android.diploma.core.presentation.ui.theme.red
import ru.practicum.android.diploma.search.domain.model.Contacts
import ru.practicum.android.diploma.search.domain.model.Experience
import ru.practicum.android.diploma.search.domain.model.Salary
import ru.practicum.android.diploma.search.domain.model.VacancyDetail

// нужно будет доработать код. после получения реальных описаний вакансий
// нужно будет добавить bullets перед списками
// нужна обработка строк в зависимости от вида предоставления списков в вакансиях

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VacancyDetailsScreen(
    vacancy: VacancyDetail?,
    isFavorite: Boolean = false,
    onBack: () -> Unit,
    onShare: () -> Unit,
    onFavoriteClick: () -> Unit,
    onPhoneClick: (String) -> Unit = {},
    onEmailClick: (String) -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.title_vacancy_details))
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onShare) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Поделиться"
                        )
                    }
                    IconButton(onClick = onFavoriteClick) {
                        Icon(
                            painter = painterResource(
                                id = if (isFavorite) R.drawable.ic_favorites_active else R.drawable.ic_favorites_inactive
                            ),
                            contentDescription = if (isFavorite) "Удалить из избранного" else "В избранное",
                            tint = if (isFavorite) red else black,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (vacancy == null) {
                Text(
                    text = "Загрузка...",
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                VacancyDetailsContent(
                    vacancy = vacancy,
                    onPhoneClick = onPhoneClick,
                    onEmailClick = onEmailClick
                )
            }
        }
    }
}

@Composable
private fun VacancyDetailsContent(
    vacancy: VacancyDetail,
    onPhoneClick: (String) -> Unit,
    onEmailClick: (String) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
    ) {
        item { VacancyTitle(vacancy.name) }

        vacancy.salary?.let {
            item { VacancySalary(it) }
        }

        item { EmployerItem(vacancy) }

        vacancy.experience?.let {
            item { VacancyExperience(it) }
        }

        item { VacancyEmploymentAndSchedule(vacancy) }

        item { VacancyDescription(vacancy.description) }

        item { VacancySkills(vacancy.skills) }

        vacancy.contacts?.let {
            item { VacancyContacts(it, onPhoneClick, onEmailClick) }
        }
    }
}

@Composable
private fun VacancyTitle(name: String) {
    Text(
        text = name,
        style = CustomTypography.displayLarge
    )
}

@Composable
private fun VacancySalary(salary: Salary) {
    Text(
        text = "от: ${salary.from}, до: ${salary.to}, ${salary.currency}",
        style = CustomTypography.headlineMedium
    )
}

@Composable
private fun VacancyExperience(experience: Experience) {
    Column {
        Text(
            text = "Требуемый опыт",
            style = CustomTypography.titleMedium
        )
        Text(
            text = experience.name,
            style = CustomTypography.bodyMedium
        )
    }
}

@Composable
private fun VacancyEmploymentAndSchedule(vacancy: VacancyDetail) {
    val text = when {
        vacancy.employment != null && vacancy.schedule != null ->
            "${vacancy.employment.name} ${vacancy.schedule.name}"

        vacancy.employment != null ->
            vacancy.employment.name

        vacancy.schedule != null ->
            vacancy.schedule.name

        else -> null
    }

    text?.let {
        Text(
            text = it,
            style = CustomTypography.bodyMedium
        )
    }
}

@Composable
private fun VacancyDescription(description: String) {
    Column {
        Text(
            text = "Описание вакансии",
            style = CustomTypography.headlineMedium
        )
        Text(
            text = "Обязанности",
            style = CustomTypography.titleMedium
        )
        Text(
            text = description,
            style = CustomTypography.bodyMedium
        )
    }
}

@Composable
private fun VacancySkills(skills: List<String>) {
    Column {
        Text(
            text = "Ключевые навыки",
            style = CustomTypography.headlineMedium
        )

        skills.forEach { skill ->
            Text(
                text = skill,
                style = CustomTypography.bodyMedium
            )
        }
    }
}

@Composable
private fun VacancyContacts(
    contacts: Contacts,
    onPhoneClick: (String) -> Unit,
    onEmailClick: (String) -> Unit,
) {
    Column {
        contacts.phone.forEach { phone ->
            Text(
                text = phone,
                modifier = Modifier.clickable { onPhoneClick(phone) },
                style = CustomTypography.bodyMedium
            )
        }
        if (contacts.email.isNotBlank()) {
            Text(
                text = contacts.email,
                modifier = Modifier.clickable { onEmailClick(contacts.email) },
                style = CustomTypography.bodyMedium
            )
        }
    }
}
