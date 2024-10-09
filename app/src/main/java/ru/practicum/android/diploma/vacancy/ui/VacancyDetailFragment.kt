package ru.practicum.android.diploma.vacancy.ui

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
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
        binding.apply {
            vacancyName.text = vacancy.name
            salary.text = vacancy.salary
            companyName.text = vacancy.companyName
            city.text = vacancy.address
            experience.text = vacancy.experience
            schedule.text = vacancy.scheduleAndEmployment
            description.text = Html.fromHtml(vacancy.description)
            keySkill.text = Html.fromHtml(vacancy.keySkills)

            val corner = resources.getDimension(R.dimen.radius_size_12).toInt()

            Glide.with(requireContext())
                .load(vacancy.companyLogo)
                .fitCenter()
                .placeholder(R.drawable.ic_placeholder_32px)
                .transform(RoundedCorners(corner))
                .into(logo)
        }
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

    companion object {
        private const val VACANCY_ID = "vacancyId"
        fun createArgs(vacancyId: String): Bundle {
            return bundleOf(VACANCY_ID to vacancyId)
        }
    }
}
