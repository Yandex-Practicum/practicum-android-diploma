package ru.practicum.android.diploma.ui.vacancy

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import ru.practicum.android.diploma.data.dto.model.favorites.ShareData
import ru.practicum.android.diploma.domain.api.ShareInteractor
import ru.practicum.android.diploma.util.SingleLiveEvent

class VacancyFragmentViewModel(shareInteractor: ShareInteractor) : ViewModel() {

    private val shareState = SingleLiveEvent<ShareData>()

    fun observeShareState(): LiveData<ShareData> = shareState

}
