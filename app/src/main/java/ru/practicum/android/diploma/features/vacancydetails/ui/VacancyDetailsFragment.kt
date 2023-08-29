package ru.practicum.android.diploma.features.vacancydetails.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentVacancydetailsBinding
import ru.practicum.android.diploma.features.vacancydetails.presentation.VacancyDetailsViewModel

class VacancyDetailsFragment: Fragment() {
    private val viewModel by viewModel<VacancyDetailsViewModel>()

    private var _binding: FragmentVacancydetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacancydetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getVacancyById(getIdFromArgs())

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getIdFromArgs(): String? {
        return requireArguments().getString(ARGS_VACANCY_ID)
    }

    companion object {

        private const val ARGS_VACANCY_ID = "ARGS_VACANCY_ID"
        fun createArgs(id: String): Bundle =
            bundleOf(ARGS_VACANCY_ID to id)
    }
}