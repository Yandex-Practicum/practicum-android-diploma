package ru.practicum.android.diploma.vacancy.details.presentation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.presentation.ui.theme.CustomTypography
import ru.practicum.android.diploma.search.domain.model.VacancyDetail

// нужно будет доработать код. после получения реальных описаний вакансий
// нужно будет добавить bullets перед списками
// нужна обработка строк в зависимости от вида предоставления списков в вакансиях

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
                VacancyDetailsContent2(vacancy = vacancy)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VacancyDetailsContent2(vacancy: VacancyDetail) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
//        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        vacancy.name.let {
            item {
                Text(
                    vacancy.name,
                    style = CustomTypography.displayLarge
//                    style = MaterialTheme.typography.displayLarge
                )
            }
        }

        // откорректировать после получения функции анализа данных по ЗП
        vacancy.salary?.let { salary ->
            item {
                Text(
                    text = "от: ${salary.from}, до: ${salary.to}, ${salary.currency}",
                    style = CustomTypography.headlineMedium
                )
            }
        }

        // Блок с работодателем
        item {
            EmployerItem(vacancy = vacancy)
        }

        vacancy.experience?.let { experience ->
            item {
                Text(
                    text = "Требуемый опыт",
                    style = CustomTypography.titleMedium
                )
            }
            item {
                Text(
                    text = experience.name,
                    style = CustomTypography.bodyMedium
                )
            }
        }

        if (vacancy.employment != null && vacancy.schedule != null) {
            item {
                Text(
                    text = "${vacancy.employment.name} ${vacancy.schedule.name}",
                    style = CustomTypography.bodyMedium
                )
            }
        } else {
            vacancy.employment?.let { employment ->
                item {
                    Text(
                        text = employment.name,
                        style = CustomTypography.bodyMedium
                    )
                }
            }

            vacancy.schedule?.let { schedule ->
                item {
                    Text(
                        text = schedule.name,
                        style = CustomTypography.bodyMedium
                    )
                }
            }
        }

        vacancy.description.let { description ->
            item {
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

        vacancy.skills.let {
            item {
                Text(
                    text = "Ключевые навыки",
                    style = CustomTypography.headlineMedium

                )
            }
            vacancy.skills.forEach { skill ->
                item {
                    Text(
                        text = skill,
                        style = CustomTypography.bodyMedium
                    )
                }
            }
        }
    }
}
