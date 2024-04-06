package ru.practicum.android.diploma.ui.details

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markodevcic.peko.PermissionRequester
import com.markodevcic.peko.PermissionResult
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.api.details.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.models.vacacy.Salary
import ru.practicum.android.diploma.domain.sharing.ExternalNavigator

class DetailsViewModel(
    private val interactor: VacancyDetailsInteractor,
    private val externalNavigator: ExternalNavigator
) : ViewModel() {

    private val stateLiveData = MutableLiveData<DetailsViewState>()
    fun observeState(): LiveData<DetailsViewState> = stateLiveData

    fun onViewCreated(fragment: DetailsFragment) {
        val vacancyId = fragment.requireArguments().getString(DetailsFragment.vacancyIdKey)
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

    fun writeEmail() {
        val content = stateLiveData.value as? DetailsViewState.Content
        if (content?.contactEmail != null) {
            externalNavigator.writeEmail(content.contactEmail)
        }
    }

    fun call(context: Context) {
        viewModelScope.launch {
            PermissionRequester.instance().request(
                Manifest.permission.CALL_PHONE
            ).collect() {
                when (it) {
                    is PermissionResult.Granted -> {
                        val content = stateLiveData.value as? DetailsViewState.Content
                        val phone = content?.contactsPhones?.first()
                        if (phone != null) {
                            externalNavigator.call(phone)
                        }
                    }
                    is PermissionResult.Denied.DeniedPermanently -> {
                        externalNavigator.openApplicationSettings()
                    }
                    is PermissionResult.Denied.NeedsRationale -> {
                        Toast.makeText(context, context.getString(R.string.call_permission_text), Toast.LENGTH_LONG).show()
                    }
                    is PermissionResult.Cancelled -> {
                        return@collect
                    }
                }
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

    private fun formatSalary(salary: Salary?, context: Context): String? {
        if (salary == null) return null

        var text = ""
        val from = salary.from
        val to = salary.to
        val currency = salary.currency

        if (from != null) {
            text += "${context.getString(R.string.from)} ${formatPrice(from.toString())} "
        }
        if (to != null) {
            text += "${context.getString(R.string.to)} ${formatPrice(to.toString())} "
        }
        if (text.isNotEmpty() && currency != null) {
            text += currency
        }
        return text.ifEmpty { null }
    }

    private fun formatPrice(price: String): String {
        return price.reversed().chunked(sizeOfMoneyPart).reversed().joinToString(" ") { it.reversed() }
    }

    companion object {
        const val sizeOfMoneyPart = 3
    }
}
