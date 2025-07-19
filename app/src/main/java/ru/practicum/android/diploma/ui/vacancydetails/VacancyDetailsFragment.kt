package ru.practicum.android.diploma.ui.vacancydetails

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancyDetailsFragmentBinding
import ru.practicum.android.diploma.domain.models.vacancydetails.EmploymentForm
import ru.practicum.android.diploma.presentation.vacancydetailsscreen.uistate.VacancyDetailsUiState
import ru.practicum.android.diploma.presentation.vacancydetailsscreen.viewmodel.VacancyDetailsViewModel
import ru.practicum.android.diploma.ui.extensions.format
import ru.practicum.android.diploma.util.dpToPx

class VacancyDetailsFragment : Fragment() {

    private var _binding: VacancyDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private val args: VacancyDetailsFragmentArgs by navArgs()
    private val vacancyId by lazy { args.vacancyId }

    private var vacancyUrl: String? = null

    private val viewModel by viewModel<VacancyDetailsViewModel> {
        parametersOf(vacancyId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = VacancyDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getVacancyDetailsState.observe(viewLifecycleOwner) {
            vacancyState(it)
        }

        viewModel.getIsFavouriteVacancy.observe(viewLifecycleOwner) {
            renderLikeButton(it)
        }

        binding.btnArrowBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnShare.setOnClickListener {
            vacancyUrl?.let { link ->
                viewModel.shareVacancy(link)
            } ?: "$LINK_VACANCY_HH$vacancyId"
        }

        binding.btnFavorite.setOnClickListener {
            viewModel.onFavouriteClicked()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bind(state: VacancyDetailsUiState.Content) {
        val combinedEmployment = formatEmployment(
            state.data.employmentForm,
            state.data.workFormat
        )

        val skillsText = formattedKeySkills(state.data.keySkills)

        with(binding) {
            tvNameVacancy.text = state.data.name
            tvSalaryVacancy.text = state.data.salary.format(requireContext())
            tvEmployeeName.text = state.data.employer
            tvCityName.text = state.data.city ?: ""
            tvExperienceValue.text = state.data.experience ?: ""
            tvDescriptionValue.text = descriptionHtml(state.data.description)

            if (skillsText != null) {
                keySkillsTitle.isVisible = true
                keySkillsValue.isVisible = true
                keySkillsValue.text = skillsText
            } else {
                keySkillsTitle.isVisible = false
                keySkillsValue.isVisible = false
            }

            if (combinedEmployment.isNotBlank()) {
                tvEmploymentFormValue.text = combinedEmployment
                tvEmploymentFormValue.isVisible = true
            } else {
                tvEmploymentFormValue.isVisible = false
            }

            Glide.with(this@VacancyDetailsFragment)
                .load(state.data.logoUrl)
                .placeholder(R.drawable.vacancy_placeholder)
                .transform(RoundedCorners(dpToPx(COVER_ROUND, requireContext())))
                .into(ivCoverVacancy)
        }
    }

    private fun vacancyState(state: VacancyDetailsUiState) {
        binding.progressBar.isVisible = false
        when (state) {
            is VacancyDetailsUiState.Content -> {
                binding.groupContent.isVisible = true
                binding.groupPlaceholder.isVisible = false

                vacancyUrl = state.data.alternateUrl

                bind(state)

            }

            is VacancyDetailsUiState.Loading -> {
                binding.progressBar.isVisible = true
                binding.groupPlaceholder.isVisible = false
                binding.groupContent.isVisible = false
            }

            is VacancyDetailsUiState.NothingFound -> {
                binding.groupPlaceholder.isVisible = true
                binding.groupContent.isVisible = false
                binding.ivPlaceholderCover.setImageResource(R.drawable.placeholder_details_vacancy_not_found)
                binding.tvPlaceholder.setText(R.string.vacancy_not_found_or_deleted)
            }

            is VacancyDetailsUiState.ServerError -> {
                binding.groupPlaceholder.isVisible = true
                binding.groupContent.isVisible = false
                binding.ivPlaceholderCover.setImageResource(R.drawable.placeholder_details_vacancy_server_error)
                binding.tvPlaceholder.setText(R.string.server_error)
            }
        }
    }

    private fun renderLikeButton(active: Boolean) {
        if (active) {
            binding.btnFavorite.setImageResource(R.drawable.favorites_on_24px)
        } else {
            binding.btnFavorite.setImageResource(R.drawable.favourites_empty_24px)
        }
    }

    private fun descriptionHtml(description: String): String {
        val descWithSep = listOf(description).joinToString(separator = "<br>•") { it }
        return Html.fromHtml(descWithSep, Html.FROM_HTML_MODE_COMPACT).toString()
    }

    private fun formattedKeySkills(keySkills: List<String>?): String? {
        if (keySkills.isNullOrEmpty()) return null

        val skillsWithSep = keySkills.joinToString(separator = "<br>•") { it }
        return Html.fromHtml(skillsWithSep, Html.FROM_HTML_MODE_COMPACT).toString()
    }

    private fun formatEmployment(employmentForm: EmploymentForm?, workFormat: List<String>?): String {
        return buildList {
            employmentForm?.let {
                val text = if (it.requiresSuffix) {
                    getString(R.string.employment_with_suffix, it.name)
                } else {
                    it.name
                }
                add(text)
            }
            workFormat?.let { addAll(it) }
        }.joinToString(separator = ", ")
    }

    companion object {
        private const val COVER_ROUND = 12f
        private const val LINK_VACANCY_HH = "https://hh.ru/vacancy/"
    }
}
