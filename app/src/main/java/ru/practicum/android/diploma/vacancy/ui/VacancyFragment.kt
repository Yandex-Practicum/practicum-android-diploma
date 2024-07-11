package ru.practicum.android.diploma.vacancy.ui

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding

class VacancyFragment : Fragment() {

    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!
    private val viewModel: VacancyViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacancyBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vacancyId = requireArguments().getInt(VACANCY_ID, 0)
        viewModel.loadVacancy(vacancyId)
        initializeListeners()
        initializeObservers()
    }

    private fun initializeListeners() {
        binding.tbVacancy.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.ivShare.setOnClickListener {
            viewModel.shareVacancy()
        }

        binding.ivFavorite.setOnClickListener {
            viewModel.changeFavorite()
        }
    }

    private fun initializeObservers() {
        viewModel.screenState.observe(viewLifecycleOwner) { screenState ->
            when (screenState) {
                is VacancyState.Content -> showContext(screenState)
                VacancyState.ErrorVacancyNotFound -> showErrorVacancyNotFound()
                VacancyState.ErrorServer -> showErrorServer()
                VacancyState.Loading -> showLoading()
            }
        }
        viewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            binding.ivFavorite.setImageDrawable(
                if (isFavorite) {
                    getDrawable(requireActivity(), R.drawable.ic_favourite_on)
                } else {
                    getDrawable(
                        requireActivity(),
                        R.drawable.ic_favourite_off
                    )
                }
            )
        }
    }

    private fun showContext(screenState: VacancyState.Content) {
        val vacancyFull = screenState.vacancyFull
        binding.progressBar.isVisible = false
        binding.svVacancyInfo.isVisible = true
        binding.ivPlaceholder.isVisible = false
        binding.tvPlaceholder.isVisible = false
        binding.tvVacancyName.text = vacancyFull.name

        binding.tvSalary.text = vacancyFull.salary

        binding.tvExperienceValue.text = vacancyFull.experience
        binding.tvEmployment.text = vacancyFull.employment + ", " + vacancyFull.schedule

        binding.tvCompany.text = vacancyFull.company
        binding.tvArea.text = vacancyFull.area
        Glide.with(requireActivity())
            .load(vacancyFull.icon)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(R.drawable.ic_placeholder_logo)
            .centerCrop()
            .into(binding.ivLogo)

        binding.tvDescriptionValue.text = Html.fromHtml(vacancyFull.description, Html.FROM_HTML_MODE_COMPACT)
        binding.tvKeySkillsValue.text = Html.fromHtml(vacancyFull.keySkills, Html.FROM_HTML_MODE_COMPACT)

        binding.tvEmail.text = vacancyFull.email
        binding.tvEmail.setOnClickListener {
            viewModel.sendMessageToEmail()
        }

        binding.tvPhone.text = vacancyFull.phone
        binding.tvPhone.setOnClickListener {
            viewModel.callEmployer()
        }

        binding.tvComment.text = vacancyFull.comment
    }

    private fun showErrorVacancyNotFound() {
        binding.progressBar.isVisible = false
        binding.ivPlaceholder.setImageDrawable(getDrawable(requireContext(), R.drawable.placeholder_vacancy_not_found))
        binding.tvPlaceholder.text = getString(R.string.vacancy_not_found)
        binding.ivPlaceholder.isVisible = true
        binding.tvPlaceholder.isVisible = true
    }

    private fun showErrorServer() {
        binding.progressBar.isVisible = false
        binding.ivPlaceholder.setImageDrawable(getDrawable(requireContext(), R.drawable.placeholder_server_error_cat))
        binding.tvPlaceholder.text = getString(R.string.vacancy_server_error)
        binding.ivPlaceholder.isVisible = true
        binding.tvPlaceholder.isVisible = true
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
        binding.svVacancyInfo.isVisible = false
    }

    companion object {
        private const val LIMIT_RESULTS = 100
        private const val VACANCY_ID = "VACANCY_ID"
        fun createArguments(vacancyId: Int): Bundle = bundleOf(VACANCY_ID to vacancyId)
    }
}
