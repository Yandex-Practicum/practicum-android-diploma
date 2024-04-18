package ru.practicum.android.diploma.ui.details

import android.Manifest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markodevcic.peko.PermissionRequester
import com.markodevcic.peko.PermissionResult
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.vacancies.VacancyDetailsException
import ru.practicum.android.diploma.domain.api.details.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.models.vacacy.Contacts
import ru.practicum.android.diploma.domain.sharing.ExternalNavigator
import ru.practicum.android.diploma.util.SalaryFormatter

class DetailsViewModel(
    private val detailsInteractor: VacancyDetailsInteractor,
    private val externalNavigator: ExternalNavigator
) : ViewModel() {

    private val stateLiveData = MutableLiveData<DetailsViewState>()
    fun observeState(): LiveData<DetailsViewState> = stateLiveData

    private var vacancyDetails: VacancyDetails? = null

    fun onViewCreated(vacancyId: String) {
        stateLiveData.postValue(DetailsViewState.Loading)

        viewModelScope.launch {
            @Suppress("detekt:TooGenericExceptionCaught", "detekt:SwallowedException")
            try {
                val dbVacancy = detailsInteractor.getVacancyFromDatabase(vacancyId)
                var vacancyContacts: Contacts? = null
                if (dbVacancy != null) {
                    vacancyContacts = Contacts(
                        dbVacancy.contacts?.email,
                        dbVacancy.contacts?.name,
                        dbVacancy.contacts?.phone,
                        dbVacancy.contacts?.comment
                    )
                }
                detailsInteractor.getVacancyDetails(vacancyId).collect {
                    vacancyDetails = it
                    updateModel(it.copy(contacts = vacancyContacts))
                }
            } catch (e: VacancyDetailsException) {
                if (!e.message.isNullOrBlank() && e.message == "Network error") {
                    val dbVacancy = detailsInteractor.getVacancyFromDatabase(vacancyId)
                    if (dbVacancy != null) {
                        vacancyDetails = dbVacancy
                        updateModel(dbVacancy)
                    } else {
                        stateLiveData.postValue(DetailsViewState.Error)
                    }
                } else {
                    stateLiveData.postValue(DetailsViewState.Error)
                }
            } catch (e: Exception) {
                stateLiveData.postValue(DetailsViewState.Error)
            }
        }
    }

    fun writeEmail(email: String?) {
        if (email != null) {
            externalNavigator.writeEmail(email)
        }
    }

    fun call(phone: String?) {
        viewModelScope.launch {
            PermissionRequester.instance().request(
                Manifest.permission.CALL_PHONE
            ).collect() {
                when (it) {
                    is PermissionResult.Granted -> {
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

    private fun updateModel(vacancy: VacancyDetails) {
        val salaryFrom = vacancy.salary?.from
        val salaryTo = vacancy.salary?.to
        val content = DetailsViewState.Content(
            name = vacancy.name,
            salaryFrom = if (salaryFrom != null) SalaryFormatter.format(salaryFrom.toString()) else null,
            salaryTo = if (salaryTo != null) SalaryFormatter.format(salaryTo.toString()) else null,
            currency = vacancy.salary?.currency,
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
            contactPhone = vacancy.contacts?.phone,
            contactComment = vacancy.contacts?.comment,
            keySkills = vacancy.keySkills
        )
        stateLiveData.postValue(content)
    }

    fun favoriteIconClicked(
        contactEmail: String?,
        contactName: String?,
        contactPhone: String?,
        contactComment: String?
    ) {
        val vacancyWithContacts = vacancyDetails?.copy(
            contacts = Contacts(
                contactEmail,
                contactName,
                contactPhone,
                contactComment
            )
        )
        vacancyWithContacts?.let {
            viewModelScope.launch {
                if (detailsInteractor.isVacancyFavorite(vacancyWithContacts.id ?: "")) {
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
        const val SIZE_OF_MONEY_PART = 3
    }
}
