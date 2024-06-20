package ru.practicum.android.diploma.presentation.vacancy

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.domain.search.models.DomainVacancy
import ru.practicum.android.diploma.domain.favorites.VacancyViewState

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
                is VacancyViewState.VacancyDataDetail -> showVacancyDetails(state.domainVacancy)
                is VacancyViewState.VacancyIsFavorite -> showFavoriteState(true)
                is VacancyViewState.VacancyIsNotFavorite -> showFavoriteState(false)
            }
        }

        binding.loader.visibility = View.VISIBLE
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

    private fun showVacancyDetails(domainVacancy: DomainVacancy) {
        binding.loader.visibility = View.GONE
        Log.i("123", domainVacancy.skills.toString())

        setJobDetails(domainVacancy)
        setCompanyDetails(domainVacancy)
        setJobDescriptionAndSkills(domainVacancy)
        setFavoriteButton()
        setContactDetails(domainVacancy)
    }

    private fun setJobDetails(domainVacancy: DomainVacancy) {
        binding.jobTitle.text = domainVacancy.name

        binding.jobSalaryAmount.text = when {
            domainVacancy.salaryFrom != null && domainVacancy.salaryTo != null ->
                "от ${domainVacancy.salaryFrom} до ${domainVacancy.salaryTo}"
            domainVacancy.salaryFrom != null -> "от ${domainVacancy.salaryFrom}"
            domainVacancy.salaryTo != null -> "до ${domainVacancy.salaryTo}"
            else -> "зарплата не указана"
        }

        binding.jobSalaryCurrency.text = domainVacancy.salaryCurrency
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

    private fun setContactDetails(domainVacancy: DomainVacancy) {
        binding.eMail.apply {
            text = domainVacancy.contactEmail
            visibility = if (domainVacancy.contactEmail != null) View.VISIBLE else View.GONE
        }

        binding.phone.apply {
            text = domainVacancy.contactPhoneNumbers.firstOrNull()
            visibility = if (domainVacancy.contactPhoneNumbers.isNotEmpty()) View.VISIBLE else View.GONE
        }

        binding.contactDetails.apply {
            text = domainVacancy.contactComment.firstOrNull()
            visibility = if (domainVacancy.contactComment.isNotEmpty()) View.VISIBLE else View.GONE
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
