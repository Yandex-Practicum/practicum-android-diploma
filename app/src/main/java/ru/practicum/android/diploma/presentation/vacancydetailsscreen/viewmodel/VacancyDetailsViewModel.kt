package ru.practicum.android.diploma.presentation.vacancydetailsscreen.viewmodel

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.sharing.SharingInteractor

class VacancyDetailsViewModel(private val sharingInteractor: SharingInteractor) : ViewModel() {
    fun shareVacancy(linkVacancy: String) {
        sharingInteractor.shareVacancy(linkVacancy)
    }
}
