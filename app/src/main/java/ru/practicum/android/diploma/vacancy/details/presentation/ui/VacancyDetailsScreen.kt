package ru.practicum.android.diploma.vacancy.details.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.search.domain.model.VacancyDetail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VacancyDetailsScreen(
    vacancy: VacancyDetail?,
    onBack: () -> Unit,
    onShare: () -> Unit,
    onFavoriteClick: () -> Unit,
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
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "В избранное"
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
                // TODO: Показать состояние загрузки или ошибки
                Text(
                    text = "Загрузка...",
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                VacancyDetailsContent(vacancy = vacancy)
            }
        }
    }
}

@Composable
private fun VacancyDetailsContent(vacancy: VacancyDetail) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // id: String
        VacancyFieldCard(
            label = "ID",
            value = vacancy.id
        )

        Spacer(modifier = Modifier.height(16.dp))

        // name: String
        VacancyFieldCard(
            label = "Название вакансии",
            value = vacancy.name
        )

        Spacer(modifier = Modifier.height(16.dp))

        // description: String
        VacancyFieldCard(
            label = "Описание",
            value = vacancy.description
        )

        Spacer(modifier = Modifier.height(16.dp))

        // salary: Salary?
        VacancyFieldCard(
            label = "Зарплата",
            value = if (vacancy.salary != null) {
                "От: ${vacancy.salary.from}, До: ${vacancy.salary.to}, Валюта: ${vacancy.salary.currency}"
            } else {
                "Не указана"
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // address: Address?
        VacancyFieldCard(
            label = "Адрес",
            value = if (vacancy.address != null) {
                "${vacancy.address.city}, ${vacancy.address.street}, ${vacancy.address.building}. Полный: ${vacancy.address.fullAddress}"
            } else {
                "Не указан"
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // experience: Experience?
        VacancyFieldCard(
            label = "Опыт работы",
            value = if (vacancy.experience != null) {
                "${vacancy.experience.name} (ID: ${vacancy.experience.id})"
            } else {
                "Не указан"
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // schedule: Schedule?
        VacancyFieldCard(
            label = "График работы",
            value = if (vacancy.schedule != null) {
                "${vacancy.schedule.name} (ID: ${vacancy.schedule.id})"
            } else {
                "Не указан"
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // employment: Employment?
        VacancyFieldCard(
            label = "Тип занятости",
            value = if (vacancy.employment != null) {
                "${vacancy.employment.name} (ID: ${vacancy.employment.id})"
            } else {
                "Не указан"
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // contacts: Contacts?
        VacancyFieldCard(
            label = "Контакты",
            value = if (vacancy.contacts != null) {
                "Имя: ${vacancy.contacts.name}, Email: ${vacancy.contacts.email}, Телефоны: ${vacancy.contacts.phone.joinToString(", ")} (ID: ${vacancy.contacts.id})"
            } else {
                "Не указаны"
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // employer: Employer
        VacancyFieldCard(
            label = "Работодатель",
            value = "${vacancy.employer.name}, Логотип: ${vacancy.employer.logo} (ID: ${vacancy.employer.id})"
        )

        Spacer(modifier = Modifier.height(16.dp))

        // area: FilterArea
        VacancyFieldCard(
            label = "Регион",
            value = "${vacancy.area.name} (ID: ${vacancy.area.id}, Parent ID: ${vacancy.area.parentId})"
        )

        Spacer(modifier = Modifier.height(16.dp))

        // skills: List<String>
        VacancyFieldCard(
            label = "Навыки",
            value = if (vacancy.skills.isNotEmpty()) {
                vacancy.skills.joinToString(", ")
            } else {
                "Не указаны"
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // url: String
        VacancyFieldCard(
            label = "URL",
            value = vacancy.url
        )

        Spacer(modifier = Modifier.height(16.dp))

        // industry: FilterIndustry
        VacancyFieldCard(
            label = "Отрасль",
            value = "${vacancy.industry.name} (ID: ${vacancy.industry.id})"
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun VacancyFieldCard(
    label: String,
    value: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
