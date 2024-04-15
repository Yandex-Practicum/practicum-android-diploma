package ru.practicum.android.diploma.ui.details

import android.Manifest
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markodevcic.peko.PermissionRequester
import com.markodevcic.peko.PermissionResult
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.vacancies.VacancyDetailsException
import ru.practicum.android.diploma.domain.api.details.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.models.vacacy.Salary
import ru.practicum.android.diploma.domain.sharing.ExternalNavigator
import ru.practicum.android.diploma.util.CurrencySymbol
import ru.practicum.android.diploma.util.SalaryFormatter

class DetailsViewModel(
    private val detailsInteractor: VacancyDetailsInteractor,
    private val externalNavigator: ExternalNavigator
) : ViewModel() {

    private val stateLiveData = MutableLiveData<DetailsViewState>()
    fun observeState(): LiveData<DetailsViewState> = stateLiveData

    private var vacancyDetails: VacancyDetails? = null

    fun onViewCreated(fragment: DetailsFragment) {
        val vacancyId = fragment.requireArguments().getString(DetailsFragment.vacancyIdKey)
        if (vacancyId == null) {
            assert(false) { "Vacancy id should be passed" }
            return
        }

        stateLiveData.postValue(DetailsViewState.Loading)

        viewModelScope.launch {
            @Suppress("detekt:TooGenericExceptionCaught", "detekt:SwallowedException")
            try {
                detailsInteractor.getVacancyDetails(vacancyId).collect {
                    vacancyDetails = it
                    updateModel(it, fragment.requireContext())
                }
            } catch (e: VacancyDetailsException) {
                if (!e.message.isNullOrBlank() && e.message == "Network error") {
                    val dbVacancy = detailsInteractor.getVacancyFromDatabase(vacancyId)
                    if (dbVacancy != null) {
                        vacancyDetails = dbVacancy
                        updateModel(dbVacancy, fragment.requireContext())
                    }

                } else {
                    stateLiveData.postValue(DetailsViewState.Error)
                }
            } catch (e: Exception) {
                stateLiveData.postValue(DetailsViewState.Error)
            }
        }
    }

    fun writeEmail() {
        val email = vacancyDetails?.contacts?.email ?: return
        externalNavigator.writeEmail(email)
    }

    fun call() {
        viewModelScope.launch {
            PermissionRequester.instance().request(
                Manifest.permission.CALL_PHONE
            ).collect() {
                when (it) {
                    is PermissionResult.Granted -> {
                        val phone = vacancyDetails?.contacts?.phones?.first()
                        if (phone != null) {
                            externalNavigator.call(phone)
                        }
                    }

                    is PermissionResult.Denied.DeniedPermanently -> {
                        externalNavigator.openApplicationSettings()
                    }

                    is PermissionResult.Denied.NeedsRationale -> {
                       stateLiveData.postValue(DetailsViewState.ToastPermissionDenied)
                    }

                    is PermissionResult.Cancelled -> {}
                }
            }
        }
    }

    fun shareVacancy() {
        val vacancy = vacancyDetails ?: return
        externalNavigator.share(vacancy.link)
    }

    private fun updateModel(vacancy: VacancyDetails, context: Context) {
        val content = DetailsViewState.Content(
            name = vacancy.name,
            salary = formatSalary(vacancy.salary, context),
            companyLogo = vacancy.employer?.logoUrls?.art90,
            companyName = vacancy.employer?.name,
            city = vacancy.city,
            fullAddress = vacancy.fullAddress,
            areaName = vacancy.areaName,
            experience = vacancy.experience,
            employment = vacancy.employment,
            description = vacancy.description,
            contactName = vacancy.contacts?.name,
            contactEmail = vacancy.contacts?.email,
            contactsPhones = vacancy.contacts?.phones,
            keySkills = vacancy.keySkills
        )
        stateLiveData.postValue(content)
    }

    private fun formatSalary(salary: Salary?, context: Context): String? {
        if (salary == null) return null

        var text = ""
        val from = salary.from
        val to = salary.to
        val currency = CurrencySymbol.get(salary.currency)

        if (from != null) {
            text += "${context.getString(R.string.from)} ${SalaryFormatter.format(from.toString())} "
        }
        if (to != null) {
            text += "${context.getString(R.string.to)} ${SalaryFormatter.format(to.toString())} "
        }
        if (text.isNotEmpty() && currency != null) {
            text += currency
        }
        return text.ifEmpty { null }
    }

    /*private fun formatPrice(price: String): String {
        return price.reversed().chunked(sizeOfMoneyPart).reversed().joinToString(" ") { it.reversed() }
    }*/

    fun favoriteIconClicked() {
        vacancyDetails?.let {
            viewModelScope.launch {
                if (detailsInteractor.isVacancyFavorite(vacancyDetails?.id ?: "")) {
                    detailsInteractor.makeVacancyNormal(it.id)
                    stateLiveData.postValue(
                        DetailsViewState.IsVacancyFavorite(false)
                    )
                } else {
                    detailsInteractor.makeVacancyFavorite(it)
                    stateLiveData.postValue(
                        DetailsViewState.IsVacancyFavorite(true)
                    )
                }
            }

        }
    }

    fun isVacancyFavorite() {
        viewModelScope.launch {
            stateLiveData.postValue(
                DetailsViewState.IsVacancyFavorite(
                    detailsInteractor.isVacancyFavorite(vacancyDetails?.id ?: "")
                )
            )
        }
    }

    companion object {
        const val sizeOfMoneyPart = 3
    }
}
