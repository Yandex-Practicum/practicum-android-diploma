package ru.practicum.android.diploma.favorites.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.favorites.domain.FavoriteVacancyInteractor
import ru.practicum.android.diploma.favorites.ui.model.VacancyUiModel
import ru.practicum.android.diploma.vacancy.data.db.entity.FavoriteVacancyEntity

class FavoriteVacancyViewModel(private val interactor: FavoriteVacancyInteractor) : ViewModel() {

    private val _favorites = MutableStateFlow<List<VacancyUiModel>>(emptyList())
    val favorites: StateFlow<List<VacancyUiModel>> = _favorites.asStateFlow()

    init {
        viewModelScope.launch {
            interactor.getAllFavorites()
                .map { entities ->
                    entities.map { it.toUiModel() }
                }
                .collect { _favorites.value = it }
        }
    }

    private fun FavoriteVacancyEntity.toUiModel(): VacancyUiModel {
        return VacancyUiModel(
            id = id,
            name = name,
            employer = employerName ?: "Работодатель не указан",
            salary = formatSalary(salaryFrom, salaryTo, currency),
            logoUrl = logoUrl,
            area = areaName
        )
    }

    private fun formatSalary(from: Int?, to: Int?, currency: String?): String {
        if (from == null && to == null) return "Зарплата не указана"
        val formattedFrom = from?.let { "%,d".format(it).replace(',', ' ') }
        val formattedTo = to?.let { "%,d".format(it).replace(',', ' ') }
        return when {
            from != null && to != null -> "от $formattedFrom до $formattedTo $currency"
            from != null -> "от $formattedFrom $currency"
            to != null -> "до $formattedTo $currency"
            else -> "Зарплата не указана"
        }
    }

    fun addToFavorites(vacancy: FavoriteVacancyEntity) {
        viewModelScope.launch {
            interactor.addToFavorites(vacancy)
        }
    }
}
