package ru.practicum.android.diploma.filter.profession.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.practicum.android.diploma.filter.profession.domain.model.Industry

class ProfessionViewModel(
    private val application: Application,
) : AndroidViewModel(application) {

    private var _screenStateLiveData = MutableLiveData<List<Industry>>()
    val screenStateLiveData: LiveData<SearchScreenState> = _screenStateLiveData

    companion object {
        private const val TAG: String = "ProfessionViewModel"
    }

}
