package ru.practicum.android.diploma.details.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.details.domain.models.VacancyDetails
import ru.practicum.android.diploma.details.presentation.VacancyState
import ru.practicum.android.diploma.details.presentation.VacancyViewModel
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.BindingFragment

class VacancyFragment : BindingFragment<FragmentVacancyBinding>() {

    private val viewModel by viewModel<VacancyViewModel>()

    private lateinit var vacancy: Vacancy
    private lateinit var vacancyDetails: VacancyDetails

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentVacancyBinding {
        return FragmentVacancyBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBasicVacancyInfo()

        viewModel.loadVacancyDetails(vacancy.id)

        viewModel.state.observe(viewLifecycleOwner){ state ->
            when(state){
                is VacancyState.Content -> {
                    vacancyDetails = state.vacancyDetails
                    binding.detailsData.visibility = View.VISIBLE
                    binding.refreshButton.visibility = View.GONE
                    initVacancyDetails()
                }
                is VacancyState.Error -> {
                    showToast(state.errorMessage)
                    binding.detailsData.visibility = View.GONE
                    binding.refreshButton.visibility = View.VISIBLE
                }
            }
        }

        binding.refreshButton.setOnClickListener{
            viewModel.loadVacancyDetails(vacancy.id)
        }

        binding.similarVacanciesButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_vacancyFragment_to_similarVacancyFragment,
                bundleOf(SimilarVacancyFragment.VACANCY_ID to vacancy.id)
            )
        }

        binding.backIcon.setOnClickListener{
            findNavController().navigateUp()
        }
    }

    private fun initBasicVacancyInfo(){
        val jsonVacancy = requireArguments().getString(VACANCY)
        vacancy = Gson().fromJson(jsonVacancy, Vacancy::class.java)
        binding.vacancyName.text = vacancy.name
        initSalary()
        binding.employerName.width = (resources.displayMetrics.widthPixels*0.7).toInt()
        binding.city.width = (resources.displayMetrics.widthPixels*0.7).toInt()
        binding.employerName.text = vacancy.employerName
        binding.city.text = vacancy.city
    }

    private fun initSalary(){
        var salaryText = ""
        if(vacancy.salaryFrom != null) salaryText = "от ${vacancy.salaryFrom} "
        if(vacancy.salaryTo != null) salaryText += "до ${vacancy.salaryTo}"
        if(vacancy.salaryCurrency != null) salaryText += " ${vacancy.salaryCurrency}"
        if(vacancy.salaryFrom == null && vacancy.salaryTo == null && vacancy.salaryCurrency == null) salaryText = getString(R.string.no_data)
        binding.salary.text = salaryText
    }

    private fun initVacancyDetails(){
        val radius = resources.getDimensionPixelSize(R.dimen.margin_12)
        Glide.with(requireContext())
            .load(vacancyDetails.employer?.logo_urls?.original)
            .placeholder(R.drawable.placeholder)
            .transform(RoundedCorners(radius))
            .into(binding.employerImage)

        val experience = vacancyDetails.experience?.name
        val schedule = vacancyDetails.schedule?.name
        val keySkills = vacancyDetails.key_skills
        val nameContact = vacancyDetails.contacts?.name
        val emailContact = vacancyDetails.contacts?.email
        val phoneContactList = vacancyDetails.contacts?.phones
        val firstPhoneContact = phoneContactList?.get(0)
        val formattedPhoneContact = "+${firstPhoneContact?.country}(${firstPhoneContact?.city})${firstPhoneContact?.number?.dropLast(4)}-${firstPhoneContact?.number?.drop(3)?.dropLast(2)}-${firstPhoneContact?.number?.drop(5)}"
        val noData = getString(R.string.no_data)

        binding.requiredExperienceValue.text = if(experience != null) experience else noData
        binding.scheduleValue.text = if(schedule != null) schedule else noData
        binding.vacancyDescriptionValue.text = vacancyDetails.description
        binding.vacancyKeySkillsValue.text = if(keySkills.isNotEmpty()) keySkills.joinToString { it.name } else noData
        binding.vacancyContactPersonValue.text = if(nameContact != null) nameContact else noData
        binding.vacancyContactEmailValue.text = if(emailContact != null) emailContact else noData
        binding.vacancyContactPhoneValue.text = if(phoneContactList != null) formattedPhoneContact else noData

    }

    private fun showToast(message: String){
        Toast.makeText(requireActivity().applicationContext, message, Toast.LENGTH_LONG)
            .show()
    }

    companion object {
        const val VACANCY = "vacancy"

        fun createArgs(jsonVacancy: String): Bundle = bundleOf(VACANCY to jsonVacancy)
    }
}