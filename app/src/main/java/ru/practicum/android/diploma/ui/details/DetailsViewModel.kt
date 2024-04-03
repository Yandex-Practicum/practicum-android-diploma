package ru.practicum.android.diploma.ui.details

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.api.details.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.models.vacacy.Salary

class DetailsViewModel(
    private val interactor: VacancyDetailsInteractor
) : ViewModel() {

    private val stateLiveData = MutableLiveData<DetailsViewState>()
    fun observeState(): LiveData<DetailsViewState> = stateLiveData

    fun onViewCreated(fragment: FragmentDetails) {
        val vacancyId = fragment.requireArguments().getString(FragmentDetails.vacancyIdKey)
        if (vacancyId == null) {
            assert(false) { "Vacancy id should be passed" }
            return
        }

        stateLiveData.postValue(DetailsViewState.Loading)

        viewModelScope.launch {
            interactor.getVacancyDetails(vacancyId).collect() {
                updateModel(it, fragment.requireContext())
            }
        }
    }

    private fun updateModel(vacancy: VacancyDetails, context: Context) {
        val content = DetailsViewState.Content(
            name = vacancy.name,
            salary = formatSalary(vacancy.salary, context),
            companyLogo = vacancy.employer?.logoUrls?.art90,
            companyName = vacancy.employer?.name,
            city = vacancy.city,
            experience = vacancy.experience,
            employment = vacancy.employment,
            description = vacancy.description,
            contactName = vacancy.contacts?.name,
            contactEmail = vacancy.contacts?.email,
            contactsPhones = vacancy.contacts?.phones
        )
        stateLiveData.postValue(content)
    }

    private fun formatSalary(salary: Salary?, context: Context) : String? {
        if (salary == null) { return null }

        var text = ""
        val from = salary.from
        val to = salary.to
        val currency = salary.currency

        if (from != null) {
            // TODO: (s.bogachev) use formatter
            text += "${context.getString(R.string.from)} $to "
        }
        if (to != null) {
            // TODO: (s.bogachev) use formatter
            text += "${context.getString(R.string.to)} $to "
        }
        if (text.isNotEmpty() && currency != null) {
            text += currency
        }
        return text.ifEmpty { null }
    }
}
