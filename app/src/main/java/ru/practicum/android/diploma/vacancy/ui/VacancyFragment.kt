package ru.practicum.android.diploma.vacancy.ui

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.domain.model.DetailVacancy
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.vacancy.presentation.VacancyScreenState
import ru.practicum.android.diploma.vacancy.presentation.VacancyViewModel

class VacancyFragment : Fragment() {
    private val viewModel by viewModel<VacancyViewModel>()
    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!
    private var detailVacancy: DetailVacancy? = null
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
        initListeners()
        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
    }

    private fun render(vacancyScreenState: VacancyScreenState) {
        when (vacancyScreenState) {
            is VacancyScreenState.Loading -> showProgressBar()
            is VacancyScreenState.Error -> showError()
            is VacancyScreenState.Content -> {
                setContent(vacancyScreenState.vacancy)
                showContent()
            }
        }
    }

    private fun showProgressBar() {
        binding.scrollViewVacancy.isVisible = false
        binding.progressBarVacancy.isVisible = true
        binding.imageViewServerError.isVisible = false
        binding.textViewServerError.isVisible = false
    }

    private fun showContent() {
        binding.scrollViewVacancy.isVisible = true
        binding.progressBarVacancy.isVisible = false
        binding.imageViewServerError.isVisible = false
        binding.textViewServerError.isVisible = false
    }

    private fun showError() {
        binding.scrollViewVacancy.isVisible = false
        binding.progressBarVacancy.isVisible = false
        binding.imageViewServerError.isVisible = true
        binding.textViewServerError.isVisible = true
    }

    private fun setContent(detailVacancy: DetailVacancy) {
        this.detailVacancy = detailVacancy
        binding.textViewVacancyValue.text = detailVacancy.name
        binding.textViewEmployerValue.text = detailVacancy.employerName
        binding.textViewEmployerCityValue.text = detailVacancy.city
        binding.textViewRequiredExperienceValue.text = detailVacancy.experience
        binding.textViewSchedule.text = detailVacancy.workSchedule
        binding.textViewDescriptionValue.setText(Html.fromHtml(detailVacancy.description, Html.FROM_HTML_MODE_COMPACT))
        setSalary(detailVacancy)
        setLogo(detailVacancy)
        setKeySkills(detailVacancy)
        setContactInfo(detailVacancy)
    }

    private fun setLogo(detailVacancy: DetailVacancy) {
        Glide.with(binding.imageViewEmployerLogo)
            .load(detailVacancy.employerLogoUrl)
            .placeholder(R.drawable.placeholder_vacancy)
            .transform(
                RoundedCorners(
                    requireContext().resources
                        .getDimensionPixelSize(R.dimen.tv_detailed_vacancy_corner_radius)
                )
            )
            .into(binding.imageViewEmployerLogo)
    }

    private fun setSalary(detailVacancy: DetailVacancy) {
        if (detailVacancy.salaryFrom.isNullOrEmpty() && detailVacancy.salaryTo.isNullOrEmpty()) {
            binding.textViewSalaryInfoValue.text = requireContext().resources.getString(R.string.tv_salary_no_info)
        } else {
            if (detailVacancy.salaryTo.isNullOrEmpty()) {
                binding.textViewSalaryInfoValue.text =
                    requireContext().resources.getString(R.string.tv_salary_from_info, detailVacancy.salaryFrom)
            } else {
                binding.textViewSalaryInfoValue.text = requireContext().resources.getString(
                    R.string.tv_salary_from_to_info,
                    detailVacancy.salaryFrom,
                    detailVacancy.salaryTo
                )
            }
        }
    }

    private fun setKeySkills(detailVacancy: DetailVacancy) {
        if (detailVacancy.keySkills.isNullOrEmpty()) {
            binding.textViewKeySkillsTitle.isVisible = false
            binding.textViewKeySkillsValue.isVisible = false
        } else {
            var keySkillsText = ""
            detailVacancy.keySkills.forEach { keySkill ->
                val line = "${keySkill}\n"
                keySkillsText += line
            }
            binding.textViewKeySkillsValue.text = keySkillsText
        }
    }

    private fun setContactInfo(detailVacancy: DetailVacancy) {
        if (detailVacancy.contactName.isNullOrEmpty()) {
            binding.textViewContactNameTitle.isVisible = false
            binding.textViewContactNameValue.isVisible = false
        } else {
            binding.textViewContactNameValue.text = detailVacancy.contactName
        }
        if (detailVacancy.phone.isNullOrEmpty()) {
            binding.textViewEmailTitle.isVisible = false
            binding.textViewEmailValue.isVisible = false
        } else {
            binding.textViewEmailValue.text = detailVacancy.email
        }

        if (detailVacancy.phone.isNullOrEmpty()) {
            binding.textViewPhoneTitle.isVisible = false
            binding.textViewPhoneValue.isVisible = false
        } else {
            binding.textViewPhoneValue.text = detailVacancy.phone
        }

        if (detailVacancy.contactComment.isNullOrEmpty()) {
            binding.textViewCommentTitle.isVisible = false
            binding.textViewCommentValue.isVisible = false
        } else {
            binding.textViewCommentValue.text = detailVacancy.contactComment
        }
    }

    private fun initListeners() {
        binding.vacancyToolbar.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.imageViewShareVacancy.setOnClickListener {
            viewModel.shareVacancy(detailVacancy!!.alternateUrl)
        }

        binding.imageViewFavorite.setOnClickListener {
            TODO()
        }

        binding.textViewPhoneValue.setOnClickListener {
            viewModel.makeCall(detailVacancy!!.phone)
        }

        binding.textViewEmailValue.setOnClickListener {
            viewModel.sendEmail(detailVacancy!!.email)
        }
    }
}
