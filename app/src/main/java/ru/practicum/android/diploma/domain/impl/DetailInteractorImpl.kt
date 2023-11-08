package ru.practicum.android.diploma.domain.impl

import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.DetailInteractor
import ru.practicum.android.diploma.domain.ExternalNavigator
import ru.practicum.android.diploma.domain.api.DetailRepository
import ru.practicum.android.diploma.domain.models.detail.FullVacancy
import ru.practicum.android.diploma.util.Resource

class DetailInteractorImpl(val repository: DetailRepository, private val navigator: ExternalNavigator) : DetailInteractor {

    override fun getVacancy(id: String): Flow<Pair<FullVacancy?, String?>> {
        return repository.getVacancy(id).map { result ->
            when (result) {
                is Resource.Success<*> -> {
                    Pair(result.data, null)
                }

                is Resource.Error<*> -> {
                    Pair(null, result.message)
                }
            }
        }
    }
    override fun sharePhone(phone: RecyclerView){
        navigator.sharePhone(phone)
    }

    override fun shareEmail(email: String) {
        navigator.shareEmail(email)
    }
}