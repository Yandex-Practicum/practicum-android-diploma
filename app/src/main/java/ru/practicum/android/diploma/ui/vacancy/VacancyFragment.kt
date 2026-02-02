package ru.practicum.android.diploma.ui.vacancy

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.ui.search.SearchViewHolder.Companion.CORNER_RADIUS

class VacancyFragment : Fragment() {
    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!
    private val args: VacancyFragmentArgs by navArgs()
    private val vacancyId: String by lazy { args.vacancyId }
    private val viewModel: VacancyViewModel by viewModel {
        parametersOf(vacancyId)
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

        binding.includeToolbar.btnBack.visibility = View.VISIBLE
        binding.includeToolbar.toolbar.titleMarginStart =
            resources.getDimensionPixelSize(R.dimen.indent_56)

        binding.includeToolbar.toolbar.title = getString(R.string.vacancy)

        binding.includeToolbar.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonLike.setOnClickListener {
            viewModel.onFavoriteClicked()
        }

        binding.buttonShare.setOnClickListener {
            viewModel.state.value?.let { state ->
                if (state is VacancyState.Content) {
                    shareContent(state.vacancy.url)
                }
            }
        }

        viewModel.load()
        observeState()
    }

    private fun observeState() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is VacancyState.Loading -> binding.showLoading()
                is VacancyState.Content -> {
                    bindDetails(state)
                    binding.showContent(!state.skillsText.isNullOrEmpty())
                    setupContacts(state)
                    binding.renderFavorite(state.isFavorite)
                }

                is VacancyState.Error -> binding.showError(state.error)
            }
        }
    }

    private fun bindDetails(state: VacancyState.Content) {
        val vacancy = state.vacancy
        val skillsText = state.skillsText?.joinToString("\n") { "   •   $it" } ?: ""
        val logoUrl = vacancy.logoUrl
        if (logoUrl != null && logoUrl.isNotEmpty()) {
            Glide.with(this)
                .load(logoUrl)
                .placeholder(R.drawable.ic_company_placeholder_48)
                .error(R.drawable.ic_company_placeholder_48)
                .centerCrop()
                .transform(RoundedCorners(dpToPixel(CORNER_RADIUS)))
                .into(binding.companyImage)
        } else {
            binding.companyImage.setImageResource(R.drawable.ic_company_placeholder_48)
        }
        binding.name.text = vacancy.name
        binding.salary.text = state.salaryFormatted
        binding.industryName.text = vacancy.industryName
        binding.areaName.text = state.employerAddress
        binding.experienceDescription.text = vacancy.experience ?: ""
        binding.schedule.text = vacancy.schedule ?: ""
        binding.requirements.text = vacancy.description

        binding.skillsText.text = skillsText
    }

    private fun shareContent(url: String) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }

        startActivity(Intent.createChooser(shareIntent, "Поделиться"))
    }

    private fun setupContacts(state: VacancyState.Content) {
        val vacancy = state.vacancy
        var hasAnyContact = false

        vacancy.contactName?.let { name ->
            binding.contactName.text = name
            binding.contactName.isVisible = true
            hasAnyContact = true
        } ?: run {
            binding.contactName.isVisible = false
        }

        vacancy.email?.takeIf { it.isNotBlank() }?.let { email ->
            binding.contactEmail.text = email
            binding.contactEmail.isVisible = true
            binding.contactEmail.setOnClickListener { openEmailClient(email) }
            hasAnyContact = true
        } ?: run {
            binding.contactEmail.isVisible = false
        }

        val phones = vacancy.phones
        if (!phones.isNullOrEmpty()) {
            binding.contactPhone.text = phones.joinToString("\n")
            binding.contactPhone.isVisible = true

            phones.firstOrNull()?.let { firstPhone ->
                val cleanPhone = firstPhone.replace(Regex("[^+\\d]"), "")
                binding.contactPhone.setOnClickListener {
                    openDialer(cleanPhone)
                }
            }

            hasAnyContact = true
        } else {
            binding.contactPhone.isVisible = false
        }

        binding.contactsTitle.isVisible = hasAnyContact
    }

    private fun openEmailClient(email: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$email")
        }
        startActivity(intent)
    }

    private fun openDialer(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phone")
        }
        startActivity(intent)
    }

    private fun dpToPixel(dp: Float): Int {
        val density = this.resources.displayMetrics.density
        return (dp * density).toInt()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
