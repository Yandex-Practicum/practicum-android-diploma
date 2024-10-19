package ru.practicum.android.diploma.filters.areas.domain.impl

import ru.practicum.android.diploma.filters.areas.domain.api.AreaCashInteractor
import ru.practicum.android.diploma.filters.areas.domain.api.AreaCashRepository
import ru.practicum.android.diploma.filters.areas.domain.models.Area

class AreaCashInteractorImpl(val areaCashRepository: AreaCashRepository) : AreaCashInteractor {
    override fun setCashArea(area: Area) {
        areaCashRepository.setCashArea(area)
    }

    override fun cleanArea() {
        areaCashRepository.cleanArea()
    }

    override fun cleanCashRegion() {
        areaCashRepository.cleanCashRegion()
    }

    override fun cleanCashArea() {
        areaCashRepository.cleanCashArea()
    }

    override fun saveArea() {
        areaCashRepository.saveArea()
    }

    override fun getCashArea(): Area? {
        return areaCashRepository.getCashArea()
    }

    override fun resetCashArea() {
        areaCashRepository.resetCashArea()
    }
}
