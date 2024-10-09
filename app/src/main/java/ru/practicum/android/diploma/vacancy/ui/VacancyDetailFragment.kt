package ru.practicum.android.diploma.vacancy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancyDetailFragmentBinding
import ru.practicum.android.diploma.vacancy.domain.entity.Vacancy
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
        viewModel.getIsFavorite().observe(viewLifecycleOwner) { current ->
            renderFavorite(current)
        }
        binding.favoriteButton.setOnClickListener {
            viewModel.onFavoriteClicked()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderState(state: VacancyScreenState) {
        when (state) {
            is VacancyScreenState.ContentState -> showContent(state.vacancy)
            VacancyScreenState.EmptyState -> showEmpty()
            VacancyScreenState.LoadingState -> showLoading()
            VacancyScreenState.NetworkErrorState -> showNetworkError()
        }
    }

    private fun showContent(vacancy: Vacancy) {
        // комент костыль
    }

    private fun showEmpty() {
        // комент костыль
    }

    private fun showLoading() {
        // комент костыль
    }

    private fun showNetworkError() {
        // комент костыль
    }

    private fun renderFavorite(current: Boolean) {
        binding.favoriteButton.setImageResource(
            if (current) {
                R.drawable.ic_favorites_on__24px
            } else {
                R.drawable.ic_favorites_off__24px
            }
        )
    }

    companion object {
        private const val VACANCY_ID = "vacancyId"
        fun createArgs(vacancyId: String): Bundle {
            return bundleOf(VACANCY_ID to vacancyId)
        }
    }
}
