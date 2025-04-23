package ru.practicum.android.diploma.presentation.vacancy

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.InteractorFavoriteVacancies
import ru.practicum.android.diploma.domain.interactor.SearchVacancyInteractor
import ru.practicum.android.diploma.domain.models.additional.Employment
import ru.practicum.android.diploma.domain.models.additional.Experience
import ru.practicum.android.diploma.domain.models.additional.Schedule
import ru.practicum.android.diploma.domain.models.main.Salary
import ru.practicum.android.diploma.util.Currency
import ru.practicum.android.diploma.util.extensions.toVacancyShort
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

class VacancyViewModel(
    private val searchVacancyInteractor: SearchVacancyInteractor,
    private val interactorFavoriteVacancies: InteractorFavoriteVacancies,
) : ViewModel() {

    private val _isLiked = MutableLiveData<Boolean>()
    val isLiked: LiveData<Boolean> get() = _isLiked

    private val _vacancyState = MutableStateFlow<VacancyState>(
        value = VacancyState.Loading
    )
    val vacancyState: StateFlow<VacancyState> = _vacancyState

    fun getLongVacancy(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            searchVacancyInteractor.searchVacancyDetails(id).collect { pair ->

                _vacancyState.value = if (pair.first == null) VacancyState.Empty else VacancyState.Content(pair.first!!)
            }
        }
    }

    fun formatSalary(salary: Salary?): String {
        val numberFormat = (NumberFormat.getNumberInstance(Locale("ru")) as DecimalFormat).apply {
            isGroupingUsed = true
            maximumFractionDigits = 0
        }

        return when {
            salary == null -> "Зарплата не указана"
            salary.from != null && salary.to != null -> {
                val fromFormatted = numberFormat.format(salary.from)
                val toFormatted = numberFormat.format(salary.to)
                val currencySymbol = Currency.getCurrencySymbol(salary.currency ?: "")
                "от $fromFormatted до $toFormatted $currencySymbol".trim()
            }

            salary.from != null -> {
                val fromFormatted = numberFormat.format(salary.from)
                val currencySymbol = Currency.getCurrencySymbol(salary.currency ?: "")
                "от $fromFormatted $currencySymbol".trim()
            }

            salary.to != null -> {
                val toFormatted = numberFormat.format(salary.to)
                val currencySymbol = Currency.getCurrencySymbol(salary.currency ?: "")
                "до $toFormatted $currencySymbol".trim()
            }

            else -> "Зарплата не указана"
        }
    }

    fun getExperienceLabelById(id: String?): String {
        return when (id) {
            Experience.NoExperience.id -> Experience.NoExperience.label
            Experience.Between1And3.id -> Experience.Between1And3.label
            Experience.Between3And6.id -> Experience.Between3And6.label
            Experience.MoreThan6.id -> Experience.MoreThan6.label
            else -> "Не указано"
        }
    }

    fun formatEmploymentAndSchedule(
        employmentId: String?,
        scheduleId: String?,
        context: Context,
        defaultText: String = "Не указано"
    ): String {
        val employment = employmentId?.let { Employment.fromIdOrNull(it) }
        val schedule = scheduleId?.let { Schedule.fromId(it) }

        return when {
            employment != null && schedule != null ->
                "${employment.label}, ${context.getString(schedule.labelResId)}"

            employment != null -> employment.label
            schedule != null -> context.getString(schedule.labelResId)
            else -> defaultText
        }
    }

    fun checkInLiked(vacancyId: String) {
        viewModelScope.launch {
            val allFavorites = interactorFavoriteVacancies.getAllVacancies()
            if (allFavorites != null) {
                val isLiked = allFavorites.any { it.vacancyId == vacancyId }
                updateUiState(isLiked)
            }
        }
    }

    fun reactOnLikeButton() {
        val liked = _isLiked.value ?: false
        val vacancy = (_vacancyState.value as? VacancyState.Content)?.vacancy ?: return
        viewModelScope.launch {
            if (liked) {
                interactorFavoriteVacancies.deleteById(vacancy.vacancyId.toInt())
                _isLiked.postValue(false)
            } else {
                interactorFavoriteVacancies.insertVacancy(vacancy.toVacancyShort())
                _isLiked.postValue(true)
            }
        }
    }

    private fun updateUiState(isLiked: Boolean = _isLiked.value ?: false) {
        _isLiked.postValue(isLiked)
    }
}
