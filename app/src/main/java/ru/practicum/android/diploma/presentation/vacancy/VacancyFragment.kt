package ru.practicum.android.diploma.presentation.vacancy

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyViewState

class VacancyFragment : Fragment() {

    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!
    private var viewModel: VacancyViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(VacancyViewModel::class.java)

        val vacancyId = arguments?.getString(VACANCY_KEY) ?: return

        viewModel?.vacancyScreenState?.observe(viewLifecycleOwner) { state ->
            when (state) {
                is VacancyViewState.VacancyDataDetail -> showVacancyDetails(state.vacancy)
                is VacancyViewState.VacancyIsFavorite -> showFavoriteState(true)
                is VacancyViewState.VacancyIsNotFavorite -> showFavoriteState(false)
            }
        }

        binding.loader.visibility = View.VISIBLE
        viewModel?.loadVacancyDetails(vacancyId)

        setupShareButton()
        setupEmailButton()
        setupPhoneButton()
    }

    private fun setupShareButton() {
        binding.shareButton.setOnClickListener {
            viewModel?.currentVacancy?.let { vacancy ->
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
            viewModel?.currentVacancy?.contactEmail?.let { email ->
                sendEmail(email)
            }
        }
    }

    private fun setupPhoneButton() {
        binding.phone.setOnClickListener {
            viewModel?.currentVacancy?.contactPhoneNumbers?.firstOrNull()?.let { phone ->
                makePhoneCall(phone)
            }
        }
    }

    private fun showVacancyDetails(vacancy: Vacancy) {
        binding.loader.visibility = View.GONE

        setJobDetails(vacancy)
        setCompanyDetails(vacancy)
        setJobDescriptionAndSkills(vacancy)
        setFavoriteButton(vacancy)
        setContactDetails(vacancy)
    }

    private fun setJobDetails(vacancy: Vacancy) {
        binding.jobTitle.text = vacancy.name

        binding.jobSalaryAmount.text = when {
            vacancy.salaryFrom != null && vacancy.salaryTo != null -> "от ${vacancy.salaryFrom} до ${vacancy.salaryTo}"
            vacancy.salaryFrom != null -> "от ${vacancy.salaryFrom}"
            vacancy.salaryTo != null -> "до ${vacancy.salaryTo}"
            else -> "зарплата не указана"
        }

        binding.jobSalaryCurrency.text = vacancy.salaryCurrency
    }

    private fun setCompanyDetails(vacancy: Vacancy) {
        binding.companyName.text = vacancy.employerName
        binding.companyLocation.text = vacancy.city ?: vacancy.area

        Glide.with(requireContext())
            .load(vacancy.employerLogoUrl)
            .centerCrop()
            .placeholder(R.drawable.placeholder_logo)
            .into(binding.companyLogo)
    }

    private fun setJobDescriptionAndSkills(vacancy: Vacancy) {
        binding.jobDescription.text = Html.fromHtml(vacancy.description, Html.FROM_HTML_MODE_LEGACY)
        binding.keySkills.text = vacancy.skills.joinToString("\n")
    }

    private fun setFavoriteButton(vacancy: Vacancy) {
        binding.favoriteButtonOff.setOnClickListener { viewModel?.insertFavoriteVacancy(vacancy) }
        binding.favoriteButtonOn.setOnClickListener { viewModel?.deleteFavoriteVacancy(vacancy) }
    }

    private fun setContactDetails(vacancy: Vacancy) {
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
    }

    private fun sendEmail(email: String) {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(emailIntent)
    }

    private fun makePhoneCall(phone: String) {
        val phoneIntent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phone")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(phoneIntent)
    }

    companion object {
        private const val VACANCY_KEY = "vacancy_key"
    }
}
