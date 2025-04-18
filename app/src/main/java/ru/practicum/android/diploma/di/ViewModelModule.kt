package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.ui.fav_vacancies.view_models.FavVacanciesViewModel
import ru.practicum.android.diploma.ui.filter_settings.view_models.FilterParametersViewModel
import ru.practicum.android.diploma.ui.filter_settings.view_models.SelectAreaViewModel
import ru.practicum.android.diploma.ui.filter_settings.view_models.SelectIndustriesViewModel
import ru.practicum.android.diploma.ui.filter_settings.view_models.SelectLocationViewModel
import ru.practicum.android.diploma.ui.search_vacancies.view_models.SearchVacanciesViewModel
import ru.practicum.android.diploma.ui.vacancy_details.view_models.VacancyDetailsViewModel

val viewModelModule = module {

    viewModel {
        FavVacanciesViewModel()
    }

    viewModel {
        FilterParametersViewModel()
    }

    viewModel {
        SelectAreaViewModel()
    }

    viewModel {
        SelectIndustriesViewModel()
    }

    viewModel {
        SelectLocationViewModel()
    }

    viewModel {
        SearchVacanciesViewModel()
    }

    viewModel {
        VacancyDetailsViewModel()
    }

}
