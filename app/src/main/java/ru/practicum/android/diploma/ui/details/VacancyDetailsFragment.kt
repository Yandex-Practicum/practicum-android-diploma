package ru.practicum.android.diploma.ui.details

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyDetailsBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.root.RootActivity
import ru.practicum.android.diploma.util.SalaryFormater

class VacancyDetailsFragment : Fragment() {

    private var _binding: FragmentVacancyDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<VacancyDetailsViewModel>()
    private var paramVacancyId: String? = null
    private var currentVacancy: Vacancy? = null

    private val toolbar by lazy { (requireActivity() as RootActivity).toolbar }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentVacancyDetailsBinding.inflate(inflater, container, false)
        paramVacancyId = arguments?.getString(VACANCY_ID)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.stateLiveData.observe(viewLifecycleOwner) { state ->
            renderState(state)
        }
        toolbar.setNavigationIcon(R.drawable.arrow_back)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        toolbar.title = getString(R.string.title_vacancies)
        toolbar.menu.findItem(R.id.filters).isVisible = false
        if (paramVacancyId != null) {
            paramVacancyId?.let { viewModel.fetchVacancyDetails(it) }
        }
    }

    override fun onResume() {
        super.onResume()
        toolbar.menu.findItem(R.id.share).isVisible = true
        toolbar.menu.findItem(R.id.favorite).isVisible = true
    }

    private fun renderState(state: VacancyDetailsState) {
        when (state) {
            is VacancyDetailsState.Error -> {
                binding.apply {
                    ivPlaceholder.isVisible = true
                    tvPlaceholder.isVisible = true
                    ivPlaceholder.setImageResource(R.drawable.server_error_cat)
                    tvPlaceholder.setText(R.string.search_server_error)
                    nsvDetailsContent.isVisible = false
                }
            }
            is VacancyDetailsState.Loading -> {
                binding.apply {
                    nsvDetailsContent.isVisible = false
                    progressBar.isVisible = true
                }
            }
            is VacancyDetailsState.Content -> {
                val vacancyDetails = state.vacancy
                toolbarSetup(vacancyDetails)
                showToolbarForDetailsVacancy(state.isFavorite)
                showContent(state.vacancy, state.currencySymbol)
                binding.progressBar.isVisible = false
                binding.nsvDetailsContent.isVisible = true
            }
            is VacancyDetailsState.NoConnection -> {
                viewModel.getVacancyFromDb(state.vacancy.id)
            }
            is VacancyDetailsState.NotInDb -> {
                binding.apply {
                    ivPlaceholder.setImageResource(R.drawable.no_internet_scull)
                    tvPlaceholder.setText(R.string.search_no_connection)
                    ivPlaceholder.isVisible = true
                    tvPlaceholder.isVisible = true
                    binding.progressBar.isVisible = false
                    nsvDetailsContent.isVisible = false
                }
            }
        }
    }

    private fun showContent(vacancy: Vacancy, currencySymbol: String) {
        if (vacancy != null) {
            currentVacancy = vacancy
            vacancyJobName(vacancy, currencySymbol)
            cardLogoEmployer(vacancy)
            requiredExperience(vacancy)
            vacancyDescription(vacancy)
            keySkillsVacancy(vacancy)
            showContacts(vacancy)
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.isVacancyFavorite(vacancy.id)
            }
        }
    }

    private fun vacancyJobName(vacancy: Vacancy, currencySymbol: String) {
        binding.tvJobTitle.text = vacancy.vacancyName
        binding.tvSalary.text = SalaryFormater.formatSalary(
            context = requireContext(),
            salaryFrom = vacancy.salary?.from,
            salaryTo = vacancy.salary?.to,
            currencySymbol = currencySymbol
        )
    }

    private fun cardLogoEmployer(vacancy: Vacancy) {
        val radius = binding.root.resources.getDimensionPixelSize(R.dimen.radius_vacancy_icon)
        Glide.with(requireContext())
            .load(vacancy.logoUrl)
            .placeholder(R.drawable.vacancies_placeholder)
            .transform(RoundedCorners(radius))
            .override(binding.ivCompanyLogo.width, binding.ivCompanyLogo.height)
            .into(binding.ivCompanyLogo)
        binding.tvCompanyName.text = vacancy.companyName
        binding.tvLocation.text = if (vacancy.address.isNullOrEmpty()) vacancy.area else vacancy.address
    }

    private fun requiredExperience(vacancy: Vacancy) {
        binding.tvExperience.text = vacancy.experience ?: ""
        binding.tvSchedule.text = vacancy.schedule ?: ""
    }

    private fun vacancyDescription(vacancy: Vacancy) {
        binding.tvJobDescriptionValue.text = Html.fromHtml(vacancy.description, Html.FROM_HTML_MODE_COMPACT)
    }

    private fun keySkillsVacancy(vacancy: Vacancy) {
        if (vacancy.keySkills.isNotEmpty()) {
            binding.tvKeySkills.text = vacancy.keySkills.joinToString("\n• ", "• ", "")
        } else {
            binding.tvKeySkillsLabel.isVisible = false
            binding.tvKeySkills.isVisible = false
        }
    }

    private fun showContacts(vacancy: Vacancy) {
        if (!vacancy.comment.isNullOrEmpty()) {
            binding.tvComment.text = vacancy.comment
            binding.tvComment.isVisible = true
            binding.tvCommentLabel.isVisible = true
        } else {
            binding.tvComment.isVisible = false
            binding.tvCommentLabel.isVisible = false
        }
        if (!vacancy.contacts?.name.isNullOrEmpty() ||
            !vacancy.contacts?.email.isNullOrEmpty() ||
            vacancy.contacts?.phones?.isNotEmpty() == true
        ) {
            binding.clContactsContainer.isVisible = true
            binding.tvContactsLabel.isVisible = true
            showContactDetails(vacancy)
        } else {
            binding.clContactsContainer.isVisible = false
            binding.tvContactsLabel.isVisible = false
        }
    }

    private fun showContactDetails(vacancy: Vacancy) {
        binding.tvContacts.text = vacancy.contacts?.name ?: ""
        binding.tvContactsPersonLabel.isVisible = vacancy.contacts?.name?.isNotEmpty() == true

        binding.tvEmail.text = vacancy.contacts?.email ?: ""
        binding.tvEmailLabel.isVisible = vacancy.contacts?.email?.isNotEmpty() == true

        if (vacancy.contacts?.phones?.size != 0) {
            val phone = vacancy.contacts?.phones?.get(0)
            binding.tvPhones.text = phone?.country + " " + phone?.city + " " + phone?.number
            binding.tvPhones.isVisible = true
        } else {
            binding.tvPhones.text = ""
        }
        binding.tvTelephoneLable.isVisible = vacancy.contacts?.phones?.isNotEmpty() == true

        setOnClickListenersForContacts()
    }

    private fun setOnClickListenersForContacts() {
        binding.tvEmail.setOnClickListener {
            viewModel.eMail(binding.tvEmail.text.toString())
        }

        binding.tvPhones.setOnClickListener {
            viewModel.phoneCall(binding.tvPhones.text.toString())
        }
    }

    private fun toolbarSetup(vacancy: Vacancy) {
        toolbar.menu.findItem(R.id.share).isVisible = false
        toolbar.menu.findItem(R.id.favorite).isVisible = false
        toolbar.menu.findItem(R.id.filters).isVisible = false
        toolbar.menu.findItem(R.id.favorite).setOnMenuItemClickListener {
            viewModel.addToFavorite(vacancy)
            true
        }
        toolbar.menu.findItem(R.id.share).setOnMenuItemClickListener {
            viewModel.shareApp(vacancy.alternateUrl!!)
            true
        }
    }

    private fun showToolbarForDetailsVacancy(isFavorite: Boolean) {
        toolbar.menu.findItem(R.id.share).isVisible = true
        toolbar.menu.findItem(R.id.favorite).isVisible = true
        toolbar.menu.findItem(R.id.filters).isVisible = false
        if (isFavorite) {
            toolbar.menu.findItem(R.id.favorite).setIcon(R.drawable.heart_on_icon)
        } else {
            toolbar.menu.findItem(R.id.favorite).setIcon(R.drawable.heart_icon)
        }
    }

    override fun onStop() {
        super.onStop()
        toolbar.menu.findItem(R.id.share).isVisible = false
        toolbar.menu.findItem(R.id.favorite).isVisible = false
        toolbar.menu.findItem(R.id.filters).isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        toolbar.menu.findItem(R.id.favorite).setIcon(R.drawable.heart_icon)
        toolbar.navigationIcon = null
        _binding = null
    }

    companion object {
        internal const val VACANCY_ID = "vacancy_model"
    }
}
