package ru.practicum.android.diploma.presentation.vacancy

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.domain.favorites.VacancyViewState
import ru.practicum.android.diploma.domain.search.models.DomainVacancy

class VacancyFragment : Fragment() {

    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<VacancyViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vacancy: DomainVacancy? = arguments?.getParcelable(VACANCY_KEY)!!
        val vacancyId = vacancy?.vacancyId.toString()

        viewModel.vacancyScreenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is VacancyViewState.VacancyLoading -> {
                    binding.vacancyProgressBar.visibility = View.VISIBLE
                }

                is VacancyViewState.VacancyDataDetail -> showVacancyDetails(state.domainVacancy)
                is VacancyViewState.VacancyIsFavorite -> showFavoriteState(true)
                is VacancyViewState.VacancyIsNotFavorite -> showFavoriteState(false)
            }
        }

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.loadVacancyDetails(vacancyId)

        setupShareButton()
        setupEmailButton()
        setupPhoneButton()
    }

    private fun setupShareButton() {
        binding.shareButton.setOnClickListener {
            viewModel.currentVacancy?.let { vacancy ->
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "https://hh.ru/vacancy/${vacancy.vacancyId}")
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(shareIntent, "Share vacancy via"))
            }
        }
    }

    private fun setupEmailButton() {
        binding.includeContacts.eMail.setOnClickListener {
            viewModel.currentVacancy?.contactEmail?.let { email ->
                sendEmail(email)
            }
        }
    }

    private fun sendEmail(email: String) {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(emailIntent)
    }

    private fun setupPhoneButton() {
        binding.includeContacts.phone.setOnClickListener {
            viewModel.currentVacancy?.contactPhoneNumbers?.firstOrNull()?.let { phone ->
                makePhoneCall(phone)
            }
        }
    }

    private fun makePhoneCall(phone: String) {
        val phoneIntent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phone")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(phoneIntent)
    }

    private fun showVacancyDetails(vacancy: DomainVacancy) {
        binding.vacancyProgressBar.visibility = View.GONE
        binding.content.visibility = View.VISIBLE
        setJobDetails(vacancy)
        setCompanyDetails(vacancy)
        setJobDescriptionAndSkills(vacancy)
        setContactDetails(vacancy)
    }

    private fun setJobDetails(vacancy: DomainVacancy) {
        binding.jobTitle.text = vacancy.name
        binding.experience.text = vacancy.experience
        binding.employmentType.text = vacancy.employment
        binding.jobSalaryAmount.text = when {
            vacancy.salaryFrom != null && vacancy.salaryTo != null ->
                "от ${vacancy.salaryFrom} до ${vacancy.salaryTo} ${vacancy.salaryCurrency}"

            vacancy.salaryFrom != null -> "от ${vacancy.salaryFrom} ${vacancy.salaryCurrency}"
            vacancy.salaryTo != null -> "до ${vacancy.salaryTo} ${vacancy.salaryCurrency}"
            else -> "зарплата не указана"
        }
    }

    private fun setCompanyDetails(vacancy: DomainVacancy) {
        if (vacancy.contactEmail.isNullOrEmpty() &&
            vacancy.contactPhoneNumbers.isEmpty() &&
            vacancy.contactName.isNullOrEmpty()
        ) {
            binding.includeContacts.contacts.isVisible = false
        }
        binding.includeCompany.companyName.text = vacancy.employerName
        binding.includeCompany.companyLocation.text = vacancy.city ?: vacancy.area

        Glide.with(requireContext())
            .load(vacancy.employerLogoUrl)
            .centerCrop()
            .placeholder(R.drawable.placeholder_logo)
            .into(binding.includeCompany.companyLogo)
    }

    private fun setJobDescriptionAndSkills(vacancy: DomainVacancy) {
        binding.jobDescription.text = Html.fromHtml(vacancy.description, Html.FROM_HTML_MODE_LEGACY)
        binding.keySkills.text = vacancy.skills.joinToString("\n")
        binding.vacancyKeySkills.isVisible = vacancy.skills.isNotEmpty()
    }

    private fun setContactDetails(vacancy: DomainVacancy) {
        binding.includeContacts.eMail.apply {
            text = vacancy.contactEmail
            visibility = if (vacancy.contactEmail != null) View.VISIBLE else View.GONE
        }

        binding.includeContacts.phone.apply {
            text = vacancy.contactPhoneNumbers.firstOrNull()
            visibility = if (vacancy.contactPhoneNumbers.isNotEmpty()) View.VISIBLE else View.GONE
        }

        binding.includeContacts.contactDetails.apply {
            text = vacancy.contactComment.firstOrNull()
            visibility = if (vacancy.contactComment.isNotEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun showFavoriteState(isFavorite: Boolean) {
        binding.favoriteButtonOn.let {
            it.isVisible = isFavorite
            it.isClickable = isFavorite
        }
        binding.favoriteButtonOff.let {
            it.isVisible = !isFavorite
            it.isClickable = !isFavorite
        }
        prepareViews()
    }

    private fun prepareViews() {
        binding.favoriteButtonOff.setOnClickListener {
            it.isClickable = false
            viewModel.insertFavoriteVacancy()
        }
        binding.favoriteButtonOn.setOnClickListener {
            it.isClickable = false
            viewModel.deleteFavoriteVacancy()
        }
    }

    companion object {
        private const val VACANCY_KEY = "vacancy_key"
    }
}
