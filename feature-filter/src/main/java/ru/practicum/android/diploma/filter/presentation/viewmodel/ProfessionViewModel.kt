package ru.practicum.android.diploma.filter.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class ProfessionViewModel(
    private val application: Application,
) : AndroidViewModel(application) {

    companion object {
        private const val TAG: String = "ProfessionViewModel"
    }

}
