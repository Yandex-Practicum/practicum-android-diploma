package ru.practicum.android.diploma.vacancy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.databinding.VacancyDetailFragmentBinding
import ru.practicum.android.diploma.vacancy.presentation.VacancyDetailsViewModel
import ru.practicum.android.diploma.vacancy.presentation.VacancyScreenState

class VacancyDetailFragment : Fragment() {
    private var _binding: VacancyDetailFragmentBinding? = null
    private val binding get() = _binding!!

    private val vacancyId by lazy { requireArguments().getString(VACANCY_ID) }
    private val viewModel by viewModel<VacancyDetailsViewModel> {
        parametersOf(vacancyId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = VacancyDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getVacancyState().observe(viewLifecycleOwner) { state ->
            renderState(state)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderState(state: VacancyScreenState) {
        when (state) {
            is VacancyScreenState.ContentState -> TODO()
            VacancyScreenState.EmptyState -> TODO()
            VacancyScreenState.LoadingState -> TODO()
            VacancyScreenState.NetworkErrorState -> TODO()
        }
    }

    companion object {
        private const val VACANCY_ID = "vacancyId"
        fun createArgs(vacancyId: String): Bundle {
            return bundleOf(VACANCY_ID to vacancyId)
        }
    }
}
