package ru.practicum.android.diploma.industry.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.industry.domain.api.IndustriesInteractor
import ru.practicum.android.diploma.core.domain.models.Industry

class IndustryViewModelImpl(interactor: IndustriesInteractor) : IndustryViewModel() {
    init {
        // TODO удалить после реализации IndustryViewModelImpl
        // пример получения списка фильтров
        viewModelScope.launch {
            Log.d("Industry", interactor.getIndustries("Пром").toString())
            interactor.applyFilter(Industry("388", "Промышленное оборудование, техника, станки и комплектующие"))
        }
    }
}
