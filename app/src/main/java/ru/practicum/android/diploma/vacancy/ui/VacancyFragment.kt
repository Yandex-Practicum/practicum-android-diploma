package ru.practicum.android.diploma.vacancy.ui

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.vacancy.domain.entity.Vacancy
import ru.practicum.android.diploma.vacancy.presentation.VacancyDetailsViewModel
import ru.practicum.android.diploma.vacancy.presentation.VacancyScreenState

class VacancyFragment : Fragment() {
    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!

    private var currentState: VacancyScreenState? = null
    private val vacancyId by lazy { requireArguments().getString(VACANCY_ID) }
    private val viewModel by viewModel<VacancyDetailsViewModel> {
        parametersOf(vacancyId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.shareButton.setOnClickListener {
            viewModel.share(requireContext())
        }

        viewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            updateFavoriteIcon(isFavorite)
        }

        binding.favoriteButton.setOnClickListener {
            onClickFavorite()
        }

        viewModel.getVacancyState().observe(viewLifecycleOwner) { state ->
            renderState(state)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderState(state: VacancyScreenState) {
        currentState = state
        when (state) {
            is VacancyScreenState.ContentState -> showContent(state.vacancy)
            VacancyScreenState.EmptyState -> showEmpty()
            VacancyScreenState.LoadingState -> showLoading()
            VacancyScreenState.NetworkErrorState -> showNetworkError()
            else -> {}
        }
    }

    private fun showContent(vacancy: Vacancy) {
        binding.apply {
            vacancyName.text = vacancy.name ?: ""
            salary.text = vacancy.salary ?: ""
            scrollView.visibility = View.VISIBLE
            progressCircular.visibility = View.GONE
            emptyError.visibility = View.GONE

            companyName.text = vacancy.companyName ?: ""
            city.text = vacancy.address ?: ""
            experience.text = vacancy.experience ?: ""
            scheduleAndEmployment.text = getScheduleAndEmployment(vacancy)
            description.text = Html.fromHtml(vacancy.description ?: "", Html.FROM_HTML_MODE_COMPACT)
            serverErrorPlaceholder.visibility = View.GONE
            if (vacancy.keySkills.isEmpty()) {
                keySkillTitle.visibility = View.GONE
            } else {
                keySkill.text = Html.fromHtml(vacancy.keySkills, Html.FROM_HTML_MODE_COMPACT)
            }

            Glide.with(requireContext())
                .load(vacancy.companyLogo)
                .fitCenter()
                .placeholder(R.drawable.ic_placeholder_32px)
                .transform(RoundedCorners(resources.getDimension(R.dimen.margin_12dp).toInt()))
                .into(logo)

        }
    }

    private fun getScheduleAndEmployment(vacancy: Vacancy): String {
        return if (vacancy.employment == null && vacancy.schedule == null) {
            ""
        } else if (vacancy.schedule == null) {
            vacancy.employment ?: ""
        } else if (vacancy.employment == null) {
            vacancy.schedule
        } else {
            "${vacancy.employment}, ${vacancy.schedule}"
        }
    }

    private fun showEmpty() {
        binding.apply {
            scrollView.visibility = View.GONE
            progressCircular.visibility = View.GONE
            emptyError.visibility = View.VISIBLE
            serverErrorPlaceholder.visibility = View.GONE
        }
    }

    private fun showLoading() {
        binding.apply {
            scrollView.visibility = View.GONE
            progressCircular.visibility = View.VISIBLE
            emptyError.visibility = View.GONE
            serverErrorPlaceholder.visibility = View.GONE
        }
    }

    private fun showNetworkError() {
        binding.apply {
            scrollView.visibility = View.GONE
            progressCircular.visibility = View.GONE
            emptyError.visibility = View.GONE
            serverErrorPlaceholder.visibility = View.VISIBLE
        }
    }

    private fun onClickFavorite() {
        val currentVacancy = when (val state = currentState) {
            is VacancyScreenState.ContentState -> state.vacancyDetails
            else -> null
        }

        if (currentVacancy != null) {
            val isAddedToFavorites = viewModel.onFavoriteClicked(currentVacancy)
            val message = if (isAddedToFavorites) {
                getString(R.string.vacancy_add_toast)
            } else {
                getString(R.string.vacancy_del_toast)
            }
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        if (isFavorite) {
            binding.favoriteButton.setImageResource(R.drawable.ic_favorites_active_24px)
        } else {
            binding.favoriteButton.setImageResource(R.drawable.ic_favorites_24px)
        }
    }

    companion object {
        private const val VACANCY_ID = "vacancyId"
        fun createArgs(vacancyId: String): Bundle {
            return bundleOf(VACANCY_ID to vacancyId)
        }
    }
}
