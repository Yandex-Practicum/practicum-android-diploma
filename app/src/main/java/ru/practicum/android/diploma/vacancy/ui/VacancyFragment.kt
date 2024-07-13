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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

        binding.tvEmail.setOnClickListener {
            viewModel.sendMessageToEmail()
        }

        binding.tvPhone.setOnClickListener {
            viewModel.callEmployer()
        }
    }

    private fun initializeObservers() {
        viewModel.screenState.observe(viewLifecycleOwner) { screenState ->
            when (screenState) {
                is VacancyState.Content -> showContent(screenState)
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

    private fun showContent(screenState: VacancyState.Content) {
        val vacancyFull = screenState.vacancyFull
        binding.progressBar.isVisible = false
        binding.svVacancyInfo.isVisible = true
        binding.ivPlaceholder.isVisible = false
        binding.tvPlaceholder.isVisible = false
        binding.tvContacts.isVisible = false
        binding.tvVacancyName.text = vacancyFull.name

        if (vacancyFull.salary.isNotEmpty()) {
            binding.tvSalary.text = vacancyFull.salary
        } else {
            binding.tvSalary.text = getString(R.string.salary_not_specified)
        }
        binding.tvExperienceValue.text = vacancyFull.experience

        binding.tvCompany.text = vacancyFull.company
        Glide.with(requireActivity())
            .load(vacancyFull.icon)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(R.drawable.ic_placeholder_logo)
            .centerInside()
            .into(binding.ivLogo)

        binding.ivLogo.clipToOutline = true

        binding.tvDescriptionValue.text = Html.fromHtml(vacancyFull.description, Html.FROM_HTML_MODE_COMPACT)

        showAddress(vacancyFull.area, vacancyFull.address)

        showKeySkills(vacancyFull.keySkills)

        showContacts(vacancyFull.contact, vacancyFull.email, vacancyFull.phone, vacancyFull.comment)

        showEmploymentAndSchedule(vacancyFull.employment, vacancyFull.schedule)
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

    private fun showContacts(nameContacts: String, email: String, phone: String, comment: String) {
        if (nameContacts.isNotEmpty()) {
            binding.tvContacts.isVisible = true
            binding.tvNameContact.isVisible = true
            binding.tvNameContact.text = nameContacts
        }
        if (email.isNotEmpty()) {
            binding.tvContacts.isVisible = true
            binding.tvEmail.isVisible = true
            binding.tvEmail.text = email
        }
        if (phone.isNotEmpty()) {
            binding.tvContacts.isVisible = true
            binding.tvPhone.isVisible = true
            binding.tvPhone.text = phone
        }
        if (comment.isNotEmpty()) {
            binding.tvContacts.isVisible = true
            binding.tvComment.isVisible = true
            binding.tvComment.text = comment
        }
    }

    private fun showKeySkills(keySkills: String) {
        if (keySkills.isNotEmpty()) {
            binding.tvKeySkillsValue.text = Html.fromHtml(keySkills, Html.FROM_HTML_MODE_COMPACT)
            binding.tvKeySkills.isVisible = true
            binding.tvKeySkillsValue.isVisible = true
        } else {
            binding.tvKeySkills.isVisible = false
            binding.tvKeySkillsValue.isVisible = false
        }
    }

    private fun showAddress(area: String, address: String) {
        if (address.isNotEmpty()) {
            binding.tvArea.text = address
        } else {
            binding.tvArea.text = area
        }
    }

    private fun showEmploymentAndSchedule(employment: String, schedule: String) {
        if (employment.isEmpty()) {
            if (schedule.isEmpty()) {
                binding.tvEmployment.isVisible = false
            } else {
                binding.tvEmployment.isVisible = true
                binding.tvEmployment.text = schedule
            }
        } else {
            if (schedule.isEmpty()) {
                binding.tvEmployment.isVisible = true
                binding.tvEmployment.text = employment
            } else {
                binding.tvEmployment.isVisible = true
                binding.tvEmployment.text = "$employment, $schedule"
            }
        }
    }

    companion object {
        private const val LIMIT_RESULTS = 100
        private const val VACANCY_ID = "VACANCY_ID"
        fun createArguments(vacancyId: Int): Bundle = bundleOf(VACANCY_ID to vacancyId)
    }
}
