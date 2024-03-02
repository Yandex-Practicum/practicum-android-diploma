package ru.practicum.android.diploma.ui.vacancy

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.domain.models.DetailVacancy
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.ConvertSalary


class VacancyFragment : Fragment() {

    private var vacancyId: String? = null
    private var _vacancy: DetailVacancy? = null
    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!
    private val viewModel: VacancyViewModel by viewModel<VacancyViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)
        binding.vacancyToolbars.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vacancyId = requireArguments().getString(ARGS_VACANCY)
        vacancyId = arguments?.getParcelable<Vacancy>("vacancyId")!!.id
        Log.d("id", "$vacancyId")
        viewModel.getVacancyDetail(vacancyId!!)
        viewModel.vacancyState.observe(viewLifecycleOwner) { state ->
            render(state)
        }
        binding.buttonSimilar.setOnClickListener {
            val vacancyId = it.id.toString()
            //findNavController().navigate(R.id.action_vacancyFragment_to_similarVacanciesFragment, SimilarVacanciesFragment.createArgs(vacancyId))
        }
        if (vacancyId == null) {
            onDestroy()
        }

        viewModel.onLikedCheck(vacancyId!!).observe(requireActivity()) { likeIndicator ->
            if (!likeIndicator) {
                binding.buttonAddToFavorites.visibility = VISIBLE
                binding.buttonDeleteFromFavorites.visibility = GONE
                _vacancy?.isFavorite?.isFavorite = false
                binding.buttonAddToFavorites.setOnClickListener {
                    Log.d("FragmentVacancy", "Press on like :)")
                    viewModel.clickOnButton()
                }
            } else {
                binding.buttonAddToFavorites.visibility = GONE
                binding.buttonDeleteFromFavorites.visibility = VISIBLE
                _vacancy?.isFavorite?.isFavorite = true
                binding.buttonDeleteFromFavorites.setOnClickListener {
                    Log.d("FragmentVacancy", "Press on dislike :(")
                    viewModel.clickOnButton()
                }
            }
        }
        binding.buttonShare.setOnClickListener {
            viewModel.shareVacancy()
        }
    }

    private fun reRender() {
        viewModel.vacancyState.observe(viewLifecycleOwner) { state ->
            render(state)
        }
    }

    private fun render(stateLiveData: VacancyState) {
        when (stateLiveData) {
            is VacancyState.Loading -> loading()
            is VacancyState.Content -> content(stateLiveData.vacancy)
            is VacancyState.Error -> connectionError()
            is VacancyState.EmptyScreen -> defaultSearch()
        }
    }

    private fun initViews(vacancy: DetailVacancy) {
        _vacancy = vacancy
        with(binding) {
            jobName.text = vacancy.name
            jobSalary.text =
                ConvertSalary().formatSalaryWithCurrency(
                    vacancy.salaryFrom,
                    vacancy.salaryTo,
                    vacancy.salaryCurrency
                )

            Glide.with(requireContext())
                .load(vacancy.areaUrl)
                .placeholder(R.drawable.ic_toast)
                .fitCenter()
                .transform(RoundedCorners(requireContext().resources.getDimensionPixelSize(R.dimen.margin_8)))
                .into(ivCompany)
            companyName.text = vacancy.employerName
            companyCity.text = vacancy.areaName
            jobTime.text = vacancy.scheduleName
            if (vacancy.experienceName == null) {
                neededExperience.visibility = GONE
                yearsOfExperience.visibility = GONE
            } else {
                neededExperience.visibility = VISIBLE
                yearsOfExperience.visibility = VISIBLE
                yearsOfExperience.text = vacancy.experienceName

            }
            createDiscription(vacancy.description)
            if (!vacancy.keySkillsNames.isNullOrEmpty()) {
                createKeySkills(vacancy.keySkillsNames)
            }
            createContacts(vacancy)
            if (vacancy.isFavorite.isFavorite) {
                binding.buttonAddToFavorites.visibility = GONE
                binding.buttonDeleteFromFavorites.visibility = VISIBLE
            }
        }
    }

    fun createContacts(vacancy: DetailVacancy) {
        with(binding) {
            if (vacancy.contactsName != null) {
                contactPersonData.text = vacancy.contactsName
            } else {
                contactPerson.visibility = GONE
                contactPersonData.visibility = GONE
            }
            if (vacancy.contactsEmail != null) {
                contactPersonEmailData.text = vacancy.contactsEmail
                contactPersonEmailData.setOnClickListener {
                    Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:" + "${vacancy.contactsEmail}")
                    }
                }
            } else {
                contactPersonEmail.visibility = GONE
                contactPersonEmailData.visibility = GONE
            }
            if (vacancy.contactsPhones != null) {
                var phones = ""
                vacancy.contactsPhones.forEach { phone ->
                    phones += " ${phone}\n"
                }
                contactPersonPhoneData.text = phones
                contactPersonPhoneData.setOnClickListener {
                    Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:" + "$phones")
                    }
                }
            } else {
                contactPersonPhoneData.visibility = GONE
                contactPersonPhone.visibility = GONE
            }
            if (vacancy.contactsName.isNullOrEmpty() and vacancy.contactsEmail.isNullOrEmpty() and vacancy.contactsPhones.isNullOrEmpty()) {
                contactInformation.visibility = GONE
            }
        }
    }

    private fun createDiscription(description: String?) {
        binding.tvDescription.text = HtmlCompat.fromHtml(
            description?.replace(Regex("<li>\\s<p>|<li>"), "<li>\u00A0") ?: "",
            HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM
        )
    }

    private fun createKeySkills(keySkills: List<String?>?) {
        with(binding) {
            if (keySkills.isNullOrEmpty()) {
                keySkillsRecyclerView.visibility = GONE
                binding.keySkills.visibility = GONE
            } else {
                keySkillsRecyclerView.visibility = VISIBLE
                binding.keySkills.visibility = VISIBLE
                var skills = ""
                keySkills.forEach { skill ->
                    if (!(skill.isNullOrEmpty() || skill == "")) {
                        skills += "• ${skill}\n"
                    }
                }
                keySkillsRecyclerView.text = skills
            }
        }
    }

    private fun loading() {
        binding.progressBar.visibility = VISIBLE
        binding.fragmentNotifications.visibility = GONE
    }

    private fun content(data: DetailVacancy) {
        binding.progressBar.visibility = GONE
        initViews(data)
        binding.fragmentNotifications.visibility = VISIBLE

        Log.d("Vacancy Details:", "$data")
    }


    private fun defaultSearch() {
        binding.progressBar.visibility = GONE
        binding.fragmentNotifications.visibility = GONE
    }

    private fun connectionError() {
        with(binding) {
            progressBar.visibility = GONE
            fragmentNotifications.visibility = GONE
            tvServerError.visibility = VISIBLE
            ivServerError.visibility = VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ARGS_VACANCY = "vacancyId"
    }
}
