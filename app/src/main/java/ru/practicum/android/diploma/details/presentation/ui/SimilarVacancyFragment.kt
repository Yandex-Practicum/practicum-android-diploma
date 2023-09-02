package ru.practicum.android.diploma.details.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentSimilarVacancyBinding
import ru.practicum.android.diploma.details.presentation.SimilarVacancyViewModel
import ru.practicum.android.diploma.util.BindingFragment

class SimilarVacancyFragment: BindingFragment<FragmentSimilarVacancyBinding>() {

    private val viewModel by viewModel<SimilarVacancyViewModel>()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSimilarVacancyBinding {
        return FragmentSimilarVacancyBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vacancyId = requireArguments().getString(VACANCY_ID)

    }

    companion object {
        const val VACANCY_ID = "vacancy_id"
    }

}