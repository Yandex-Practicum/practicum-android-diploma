package ru.practicum.android.diploma.vacancy.ui

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
    private var vacancyUrl: String? = null

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

        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.shareButton.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, vacancyUrl)
                type = "text/plain"
            }
            val share = Intent.createChooser(intent, null)
            share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context?.startActivity(share)

        }

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
            is VacancyScreenState.ContentState -> {
                vacancyUrl = state.vacancy.vacancyUrl
                showContent(state.vacancy)
            }

            is VacancyScreenState.EmptyState -> showEmpty()
            is VacancyScreenState.LoadingState -> showLoading()
            is VacancyScreenState.NetworkErrorState -> showNetworkError()
        }
    }

    private fun showContent(vacancy: Vacancy) {
        binding.apply {
            detailsView.visibility = View.VISIBLE
            progressCircular.visibility = View.GONE
            emptyErrorPlaceholder.visibility = View.GONE
            serverErrorPlaceholder.visibility = View.GONE

            vacancyName.text = vacancy.name
            salary.text = vacancy.salary
            companyName.text = vacancy.companyName
            city.text = vacancy.address
            experience.text = vacancy.experience
            scheduleAndEmployment.text = getScheduleAndEmployment(vacancy)
            description.text = Html.fromHtml(vacancy.description, Html.FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM)
            if (vacancy.keySkills.isEmpty()) {
                keySkillTitle.visibility = View.GONE
            } else {
                keySkill.text = Html.fromHtml(vacancy.keySkills, Html.FROM_HTML_MODE_COMPACT)
            }

            val corner = resources.getDimension(R.dimen.radius_size_12).toInt()

            Glide.with(requireContext())
                .load(vacancy.companyLogo)
                .fitCenter()
                .placeholder(R.drawable.ic_placeholder_32px)
                .transform(RoundedCorners(corner))
                .into(logo)
        }
    }

    private fun getScheduleAndEmployment(vacancy: Vacancy): String {
        return when {
            vacancy.employment == null && vacancy.schedule == null -> ""
            vacancy.employment == null && vacancy.schedule != null -> vacancy.schedule
            vacancy.employment != null && vacancy.schedule == null -> vacancy.employment
            else -> "${vacancy.employment}, ${vacancy.schedule}"
        }
    }

    private fun showEmpty() {
        binding.apply {
            detailsView.visibility = View.GONE
            progressCircular.visibility = View.GONE
            emptyErrorPlaceholder.visibility = View.VISIBLE
            serverErrorPlaceholder.visibility = View.GONE
        }
    }

    private fun showLoading() {
        binding.apply {
            detailsView.visibility = View.GONE
            progressCircular.visibility = View.VISIBLE
            emptyErrorPlaceholder.visibility = View.GONE
            serverErrorPlaceholder.visibility = View.GONE
        }
    }

    private fun showNetworkError() {
        binding.apply {
            detailsView.visibility = View.GONE
            progressCircular.visibility = View.GONE
            emptyErrorPlaceholder.visibility = View.GONE
            serverErrorPlaceholder.visibility = View.VISIBLE
        }
    }

    private fun renderFavorite(current: Boolean) {
        binding.favoriteButton.setImageResource(
            if (current) {
                R.drawable.ic_favorites_on_24px
            } else {
                R.drawable.ic_favorites_off_24px
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
