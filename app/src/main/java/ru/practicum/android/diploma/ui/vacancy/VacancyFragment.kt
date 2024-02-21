package ru.practicum.android.diploma.ui.vacancy

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.domain.model.DetailVacancy

class VacancyFragment : Fragment() {

    private var vacancyId: String? = null
    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!
    private val viewModel: VacancyViewModel by viewModels<VacancyViewModel>()

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
        viewModel.getVacancyDetail(vacancyId!!)
        viewModel.vacancyState.observe(viewLifecycleOwner) { state ->
            render(state)
        }
        binding.buttonSimilarVacancy.setOnClickListener {
            val vacancyId = it.id.toString()
            findNavController().navigate(
                R.id.action_vacancyFragment3_to_similarVacancy,
                // SimilarVacanciesFragment.createArgs(vacancyId)
            )
        }

        viewModel.shareUrl.observe(viewLifecycleOwner) { url ->
            binding.icSharing.setOnClickListener {
                val share = Intent(Intent.ACTION_SEND)
                share.putExtra(Intent.EXTRA_TEXT, url)
                share.type = "text/plain"
                requireContext().startActivity(Intent.createChooser(share, "Поделиться ссылкой"))
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
        with(binding) {
            jobName.text = vacancy.name
            jobSalary.text = ""
            /*
            ConvertSalary().formatSalaryWithCurrency(
                vacancy.salaryFrom,
                vacancy.salaryTo,
                vacancy.salaryCurrency
            )


             */
            Glide.with(requireContext())
                .load(vacancy.areaUrl)
                .placeholder(R.drawable.ic_logo)
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
            createKeySkills(vacancy.keySkillsNames)
            createContacts(vacancy)
        }
    }

    fun createContacts(vacancy: DetailVacancy) {
        with(binding) {
            if (
                vacancy.contactsName == null ||
                vacancy.contactsEmail == null ||
                vacancy.contactsPhones == null
            ) {
                contactInformation.visibility = GONE
                contactPerson.visibility = GONE
                contactInformation.visibility = GONE
                contactPersonEmail.visibility = GONE
                contactPersonPhone.visibility = GONE
            }
            if (vacancy.contactsName != null) {
                contactPersonData.text = vacancy.contactsName
            }
            if (vacancy.contactsEmail != null) {
                contactPersonEmailData.text = vacancy.contactsEmail
            }
            if (vacancy.contactsPhones != null) {
                var phones = ""
                vacancy.contactsPhones.forEach { phone ->
                    phones += " ${phone}\n"
                }
                contactPersonPhoneData.text = phones
            }
        }
    }

    private fun createDiscription(description: String?) {
        binding.tvDescription.text = HtmlCompat.fromHtml(
            description?.replace(Regex("<li>\\s<p>|<li>"), "<li>\u00A0") ?: "",
            HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM
        )
    }

    private fun createKeySkills(keySkills: List<String?>) {
        with(binding) {
            if (keySkills.isEmpty()) {
                keySkillsRecyclerView.visibility = GONE
                binding.keySkills.visibility = GONE
            } else {
                var skills = ""
                keySkills.forEach { skill ->
                    skills += "• ${skill}\n"
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
        fun createArgs(vacancyId: String): Bundle =
            bundleOf(ARGS_VACANCY to vacancyId)

    }
}
