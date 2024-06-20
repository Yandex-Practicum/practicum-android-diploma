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
            viewModel.currentDomainVacancy?.let { vacancy ->
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
        binding.eMail.setOnClickListener {
            viewModel.currentDomainVacancy?.contactEmail?.let { email ->
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
        binding.phone.setOnClickListener {
            viewModel.currentDomainVacancy?.contactPhoneNumbers?.firstOrNull()?.let { phone ->
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
        setFavoriteButton()
        setContactDetails(vacancy)
    }

    private fun setJobDetails(vacancy: DomainVacancy) {
        binding.jobTitle.text = vacancy.name
        binding.experience.text = vacancy.experience

        binding.jobSalaryAmount.text = when {
            vacancy.salaryFrom != null && vacancy.salaryTo != null ->
                "от ${vacancy.salaryFrom} до ${vacancy.salaryTo}"

            vacancy.salaryFrom != null -> "от ${vacancy.salaryFrom}"
            vacancy.salaryTo != null -> "до ${vacancy.salaryTo}"
            else -> "зарплата не указана"
        }

        binding.jobSalaryCurrency.text = vacancy.salaryCurrency
    }

    private fun setCompanyDetails(domainVacancy: DomainVacancy) {
        binding.companyName.text = domainVacancy.employerName
        binding.companyLocation.text = domainVacancy.city ?: domainVacancy.area

        Glide.with(requireContext())
            .load(domainVacancy.employerLogoUrl)
            .centerCrop()
            .placeholder(R.drawable.placeholder_logo)
            .into(binding.companyLogo)
    }

    private fun setJobDescriptionAndSkills(domainVacancy: DomainVacancy) {
        binding.jobDescription.text = Html.fromHtml(domainVacancy.description, Html.FROM_HTML_MODE_LEGACY)
        binding.keySkills.text = domainVacancy.skills.joinToString("\n")
    }

    private fun setFavoriteButton() {
        // binding.favoriteButtonOff.setOnClickListener { viewModel?.insertFavoriteVacancy() }
        // binding.favoriteButtonOn.setOnClickListener { viewModel?.deleteFavoriteVacancy() }
    }

    private fun setContactDetails(vacancy: DomainVacancy) {

        if (vacancy.contactEmail.isNullOrEmpty() &&
            vacancy.contactPhoneNumbers.isNullOrEmpty() &&
            vacancy.contactName.isNullOrEmpty()
        ) {
            binding.contacts.isVisible = false
        }

        binding.eMail.apply {
            text = vacancy.contactEmail
            visibility = if (vacancy.contactEmail != null) View.VISIBLE else View.GONE
        }

        binding.phone.apply {
            text = vacancy.contactPhoneNumbers.firstOrNull()
            visibility = if (vacancy.contactPhoneNumbers.isNotEmpty()) View.VISIBLE else View.GONE
        }

        binding.contactDetails.apply {
            text = vacancy.contactComment.firstOrNull()
            visibility = if (vacancy.contactComment.isNotEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun showFavoriteState(isFavorite: Boolean) {
        if (isFavorite) {
            binding.favoriteButtonOn.visibility = View.VISIBLE
            binding.favoriteButtonOff.visibility = View.GONE
        } else {
            binding.favoriteButtonOn.visibility = View.GONE
            binding.favoriteButtonOff.visibility = View.VISIBLE
        }
//        if (vacancy != null) {
//            // vacancyViewModel.setVacancy(vacancy)
//        }
        prepareViews()
    }

    private fun prepareViews() {
        binding.favoriteButtonOff.setOnClickListener {
            // vacancyViewModel.insertFavoriteVacancy()
        }
        binding.favoriteButtonOn.setOnClickListener {
            // vacancyViewModel.deleteFavoriteVacancy()
        }
    }

    companion object {
        private const val VACANCY_KEY = "vacancy_key"
    }
}
