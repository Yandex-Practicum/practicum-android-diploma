package ru.practicum.android.diploma.vacancy.presentation.ui

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
import ru.practicum.android.diploma.commonutils.Utils
import ru.practicum.android.diploma.ui.R
import ru.practicum.android.diploma.vacancy.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.vacancy.domain.model.Vacancy
import ru.practicum.android.diploma.vacancy.presentation.ui.state.VacancyInputState
import ru.practicum.android.diploma.vacancy.presentation.viewmodel.VacancyDetailViewModel
import ru.practicum.android.diploma.vacancy.presentation.viewmodel.state.VacancyDetailState
import ru.practicum.android.diploma.vacancy.presentation.viewmodel.state.VacancyFavoriteMessageState

class VacancyFragment : Fragment() {

    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!

    private val vacancyDetailViewModel: VacancyDetailViewModel by viewModel()
    private var idDb: Int = 0
    private var idNetwork: String = ""
    private var argsState: Int = 2
    private var vacancy: Vacancy? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        argsState = requireArguments().getInt(ARGS_STATE)
        idDb = requireArguments().getInt(VACANCY_ID_DB)
        idNetwork = requireArguments().getString(VACANCY_ID_NETWORK).toString()
        val shareLink = if (idDb != 0) {
            getString(R.string.share_link) + idDb
        } else { getString(R.string.share_link) + idNetwork }
        binding.vacancyHeader.setOnClickListener { findNavController().navigateUp() }
        binding.shareButton.setOnClickListener { vacancyDetailViewModel.share(shareLink) }
        vacancyDetailViewModel.updateFavorite(idDb)
        requireArguments().getString(VACANCY_ID_NETWORK)?.let { vacancyDetailViewModel.updateFavorite(it.toInt()) }
        if (argsState == INPUT_NETWORK_STATE) vacancyDetailViewModel.showVacancyNetwork(idNetwork)
        if (argsState == INPUT_DB_STATE) vacancyDetailViewModel.showVacancyDb(idDb)
        vacancyDetailViewModel.observeVacancyState().observe(viewLifecycleOwner) { state ->
            getVacancyState(state)
        }
        binding.favoriteButton.setOnClickListener {
            vacancy?.let { it1 -> vacancyDetailViewModel.modifyingStatusOfVacancyFavorite(it1) }
        }
        vacancyDetailViewModel.observeVacancyFavoriteMessageState().observe(viewLifecycleOwner) { state ->
            getFavoriteState(state)
        }
        vacancyDetailViewModel.observeFavoriteState().observe(viewLifecycleOwner) { isFavorite ->
            val imageResource = if (isFavorite) R.drawable.favorites_on else R.drawable.favorites_off
            binding.favoriteButton.setImageResource(imageResource)
        }
    }

    private fun getVacancyState(state: VacancyDetailState) {
        when (state) {
            is VacancyDetailState.Loading -> { showLoading() }
            is VacancyDetailState.Content -> {
                showContent(state.vacancy)
                vacancy = state.vacancy
            }
            is VacancyDetailState.Error -> { showError() }
            is VacancyDetailState.Empty -> { showEmpty() }
        }
    }

    private fun getFavoriteState(state: VacancyFavoriteMessageState) {
        when (state) {
            is VacancyFavoriteMessageState.Empty -> { makeToast(getString(R.string.message_empty)) }
            is VacancyFavoriteMessageState.NoAddFavorite -> {
                makeToast(getString(R.string.message_no_add_favorite))
            }
            is VacancyFavoriteMessageState.NoDeleteFavorite -> {
                makeToast(getString(R.string.message_no_delete_favorite))
            }
            is VacancyFavoriteMessageState.Error -> { makeToast(getString(R.string.message_error)) }
        }
    }

    private fun makeToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading() {
        binding.vacancyNotFoundError.visibility = View.GONE
        binding.vacancyServerError.visibility = View.GONE
        binding.progressbar.visibility = View.VISIBLE
    }

    private fun showContent(vacancy: Vacancy) {
        binding.progressbar.visibility = View.GONE
        binding.vacancyServerError.visibility = View.GONE
        binding.progressbar.visibility = View.GONE
        binding.vacancyName.text = vacancy.nameVacancy
        binding.vacancySalary.text = vacancy.salary

        Glide.with(this).load(vacancy.urlLogo).placeholder(R.drawable.placeholder_logo_item_favorite).centerCrop()
            .transform(
                RoundedCorners(
                    Utils.doToPx(
                        RADIUS_ROUND_VIEW,
                        requireContext()
                    )
                )
            ).transform().into(binding.vacancyImage)
        binding.vacancyCompany.text = vacancy.nameCompany
        binding.vacancyCity.text = vacancy.location
        binding.vacancyExperienceInfo.text = vacancy.experience
        binding.vacancyConditions.text = vacancy.employment
        binding.vacancyDescriptionInfo.text = Html.fromHtml(vacancy.description, Html.FROM_HTML_MODE_COMPACT).trim()
        binding.vacancyKeySkills.visibility = View.GONE
        val htmlKeys = Html.fromHtml(vacancy.keySkills, Html.FROM_HTML_MODE_COMPACT)
        if (htmlKeys != null && htmlKeys.toString() != "") binding.vacancyKeySkills.visibility = View.VISIBLE
        binding.vacancyKeySkillsInfo.text = Html.fromHtml(vacancy.keySkills, Html.FROM_HTML_MODE_COMPACT)
    }

    private fun showError() {
        binding.vacancyCard.visibility = View.GONE
        binding.vacancyExperience.visibility = View.GONE
        binding.vacancyDescription.visibility = View.GONE
        binding.vacancyKeySkills.visibility = View.GONE
        binding.progressbar.visibility = View.GONE
        binding.vacancyNotFoundError.visibility = View.GONE
        binding.vacancyServerError.visibility = View.VISIBLE
    }

    private fun showEmpty() {
        binding.vacancyCard.visibility = View.GONE
        binding.vacancyExperience.visibility = View.GONE
        binding.vacancyDescription.visibility = View.GONE
        binding.vacancyKeySkills.visibility = View.GONE
        binding.progressbar.visibility = View.GONE
        binding.vacancyServerError.visibility = View.GONE
        binding.vacancyNotFoundError.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val VACANCY_ID_NETWORK = "vacancy_instance"
        private const val VACANCY_ID_DB = "vacancy_id"

        private const val ARGS_STATE = "args_state"

        private const val INPUT_NETWORK_STATE = 0
        private const val INPUT_DB_STATE = 1

        private const val RADIUS_ROUND_VIEW = 12f

        fun createArgs(vacancyInputState: VacancyInputState): Bundle {
            return when (vacancyInputState) {
                is VacancyInputState.VacancyNetwork -> {
                    bundleOf(
                        ARGS_STATE to INPUT_NETWORK_STATE,
                        VACANCY_ID_NETWORK to vacancyInputState.id
                    )
                }

                is VacancyInputState.VacancyDb -> {
                    bundleOf(
                        ARGS_STATE to INPUT_DB_STATE,
                        VACANCY_ID_DB to vacancyInputState.id
                    )
                }
            }
        }
    }
}
