package ru.practicum.android.diploma.details.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.details.presentation.VacancyViewModel
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.BindingFragment

class VacancyFragment: BindingFragment<FragmentVacancyBinding>() {

    private val viewModel by viewModel<VacancyViewModel>()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentVacancyBinding {
        return FragmentVacancyBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vacancy = Vacancy(
            id = "text",
            name = "text",
            city = "text",
            employerName = "text",
            employerLogoUrl = "text",
            salaryCurrency = "text",
            salaryFrom = "0",
            salaryTo = "0",
            found = 0
        )

        binding.similarVacanciesButton.setOnClickListener{
            findNavController().navigate(R.id.action_vacancyFragment_to_similarVacancyFragment, bundleOf(SimilarVacancyFragment.VACANCY_ID to vacancy.id))
        }
    }
}