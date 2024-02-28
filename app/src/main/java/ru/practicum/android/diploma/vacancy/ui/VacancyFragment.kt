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
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.domain.model.DetailVacancy
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.util.CurrencySymbol
import ru.practicum.android.diploma.vacancy.presentation.VacancyScreenState
import ru.practicum.android.diploma.vacancy.presentation.VacancyViewModel
import kotlin.properties.Delegates

class VacancyFragment : Fragment() {
    private var id by Delegates.notNull<Long>()
    private val viewModel by viewModel<VacancyViewModel> { parametersOf(id) }
    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        id = VacancyFragmentArgs.fromBundle(requireArguments()).vacancyId
        super.onCreate(savedInstanceState)
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
        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
        binding.vacancyToolbar.setOnClickListener {
            findNavController().navigateUp()
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
        binding.textViewVacancyValue.text = detailVacancy.name
        binding.textViewEmployerValue.text = detailVacancy.employerName
        binding.textViewEmployerCityValue.text = detailVacancy.city
        binding.textViewRequiredExperienceValue.text = detailVacancy.experience
        binding.textViewSchedule.text = detailVacancy.workSchedule
        binding.textViewDescriptionValue.setText(Html.fromHtml(detailVacancy.description, Html.FROM_HTML_MODE_COMPACT))
        setSalary(detailVacancy.salaryFrom, detailVacancy.salaryTo, detailVacancy.currency)
        setLogo(detailVacancy.employerLogoUrl)
        setKeySkills(detailVacancy.keySkills)
        setContactInfo(
            detailVacancy.contactName,
            detailVacancy.email,
            detailVacancy.phone,
            detailVacancy.contactComment
        )
        binding.imageViewShareVacancy.setOnClickListener { viewModel.shareVacancy(detailVacancy.alternateUrl) }
        binding.imageViewFavorite.setOnClickListener { TODO() }
        binding.textViewPhoneValue.setOnClickListener { viewModel.makeCall(detailVacancy.phone) }
        binding.textViewEmailValue.setOnClickListener { viewModel.sendEmail(detailVacancy.email) }
    }

    private fun setLogo(employerLogoUrl: String) {
        Glide.with(binding.imageViewEmployerLogo)
            .load(employerLogoUrl)
            .placeholder(R.drawable.placeholder_vacancy)
            .transform(
                RoundedCorners(
                    requireContext().resources
                        .getDimensionPixelSize(R.dimen.tv_detailed_vacancy_corner_radius)
                )
            )
            .into(binding.imageViewEmployerLogo)
    }

    private fun setSalary(salaryFrom: String, salaryTo: String, currency: String) {
        if (salaryFrom.isNullOrEmpty() && salaryTo.isNullOrEmpty()) {
            binding.textViewSalaryInfoValue.text = requireContext().resources.getString(R.string.tv_salary_no_info)
        } else {
            val currencySymbol = CurrencySymbol.getCurrencySymbol(currency)
            if (salaryTo.isNullOrEmpty()) {
                binding.textViewSalaryInfoValue.text =
                    requireContext().resources.getString(R.string.tv_salary_from_info, salaryFrom, currencySymbol)
            } else {
                binding.textViewSalaryInfoValue.text = requireContext().resources.getString(
                    R.string.tv_salary_from_to_info,
                    salaryFrom,
                    salaryTo,
                    currencySymbol
                )
            }
        }
    }

    private fun setKeySkills(keySkills: List<String>) {
        if (keySkills.isNullOrEmpty()) {
            binding.textViewKeySkillsTitle.isVisible = false
            binding.textViewKeySkillsValue.isVisible = false
        } else {
            var keySkillsText = ""
            keySkills.forEach { keySkill ->
                val line = requireContext().resources.getString(
                    R.string.tv_detail_vacancy_keySkill,
                    keySkill
                )
                keySkillsText += line
            }
            binding.textViewKeySkillsValue.text = keySkillsText
        }
    }

    private fun setContactInfo(contactName: String, email: String, phone: String, contactComment: String) {
        if (contactName.isNullOrEmpty()) {
            binding.textViewContactNameTitle.isVisible = false
            binding.textViewContactNameValue.isVisible = false
            binding.textViewContactInfoTitle.isVisible = false
        } else {
            binding.textViewContactNameValue.text = contactName
        }
        if (email.isNullOrEmpty()) {
            binding.textViewEmailTitle.isVisible = false
            binding.textViewEmailValue.isVisible = false
        } else {
            binding.textViewEmailValue.text = email
        }

        if (phone.isNullOrEmpty()) {
            binding.textViewPhoneTitle.isVisible = false
            binding.textViewPhoneValue.isVisible = false
        } else {
            binding.textViewPhoneValue.text = phone
        }

        if (contactComment.isNullOrEmpty()) {
            binding.textViewCommentTitle.isVisible = false
            binding.textViewCommentValue.isVisible = false
        } else {
            binding.textViewCommentValue.text = contactComment
        }
    }
}
