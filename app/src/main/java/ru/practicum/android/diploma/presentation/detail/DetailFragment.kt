package ru.practicum.android.diploma.presentation.detail

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.domain.DetailState
import ru.practicum.android.diploma.domain.models.Phone
import ru.practicum.android.diploma.domain.models.detail.FullVacancy
import ru.practicum.android.diploma.presentation.SalaryPresenter
import ru.practicum.android.diploma.presentation.detail.adapter.PhoneAdapter
import ru.practicum.android.diploma.util.CLICK_DEBOUNCE_DELAY_MILLIS
import ru.practicum.android.diploma.util.debounce


class DetailFragment : Fragment() {
    private val viewModel by viewModel<DetailViewModel>()
    private val salaryPresenter: SalaryPresenter by inject()

    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!

    private var fullVacancy: FullVacancy? = null
    lateinit var onItemClickDebounce: (Phone) -> Unit

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
        val vacancyId = arguments?.getString("vacancyId") ?: ""
        viewModel.getVacancy(vacancyId)
        viewModel.getStatus(vacancyId)
        viewModel.observeState().observe(viewLifecycleOwner) {state ->
            render(state)
            hideEmptyContactFields(state)
        }
        viewModel.observedFavouriteState().observe(viewLifecycleOwner) {
            showFavouriteStatus(it)
        }
        binding.toolbarInclude.favourite.setOnClickListener {
            fullVacancy?.let { it1 -> viewModel.changedFavourite(it1) }
        }
        binding.searchButton.setOnClickListener {
            val bundle = bundleOf("vacancyId" to vacancyId)
            findNavController().navigate(
                R.id.action_detailFragment_to_similarVacanciesFragment,
                bundle
            )
        }

        val shareButton = view.findViewById<ImageView>(R.id.share)
        shareButton.setOnClickListener {
            fullVacancy?.alternate_url?.let { url ->
                viewModel.shareVacancyUrl(url)
            }
        }

        binding.toolbarInclude.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun render(state: DetailState) {
        when (state) {
            is DetailState.Loading -> showLoading()
            is DetailState.Content -> showContent(state.vacancy)
            is DetailState.Error -> showError(state.errorMessage)
        }
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
        binding.content.isVisible = false
        binding.placeholderMessage.isVisible = false
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun showContent(vacancy: FullVacancy) {
        binding.progressBar.isVisible = false
        binding.content.isVisible = true
        binding.placeholderMessage.isVisible = false
        fullVacancy = vacancy
        binding.jobNameTv.text = vacancy.name
        binding.jobPaymentTv.text = salaryPresenter.showSalary(vacancy.salary)
        binding.experienceTv.text = vacancy.experience
        binding.employerNameTv.text = vacancy.employerName
        Glide.with(requireContext())
            .load(vacancy.employerLogoUrl)
            .placeholder(R.drawable.item_placeholder)
            .centerCrop()
            .transform(RoundedCorners(requireContext().resources.getDimensionPixelSize(R.dimen.logo_corner_radius)))
            .into(binding.logo)
        binding.locationTv.text = vacancy.city
        binding.contactPersonName.text = vacancy.contacts?.name ?: ""
        binding.emailAddress.text = vacancy.contacts?.email ?: ""
        binding.phone.layoutManager =
            GridLayoutManager(requireContext(), 1, GridLayoutManager.VERTICAL, false)
        binding.phone.adapter = vacancy.contacts?.let {
            PhoneAdapter(it.phones) { phone ->
                onItemClickDebounce(phone)
            }
        }
        onItemClickDebounce = debounce(
            CLICK_DEBOUNCE_DELAY_MILLIS,
            viewLifecycleOwner.lifecycleScope,
            false
        ) { phone ->
            viewModel.sharePhone(phone)

        }
        binding.emailAddress.setOnClickListener {
            if (vacancy.contacts?.email != null)
                viewModel.shareEmail(vacancy.contacts.email)
        }
        val descriptionHtml = vacancy.description
        binding.vacancyDescriptionTv.setBackgroundColor(Color.TRANSPARENT)
        if (vacancy.skills.isNullOrEmpty()) {
            binding.skills.isVisible = false
        } else {
            binding.skills.isVisible = true
            binding.skillsTv.text = vacancy.skills
        }
        binding.employmentTv.text = vacancy.employment

        if (descriptionHtml != null) {
            val formattedDescription =
                HtmlCompat.fromHtml(vacancy.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
            binding.vacancyDescriptionTv.text = formattedDescription
        }
    }

    private fun showError(errorMessage: String) {
        binding.progressBar.isVisible = false
        binding.content.isVisible = false
        binding.placeholderMessage.isVisible = true
        binding.placeholderMessageImage.setImageResource(R.drawable.server_error)
        binding.placeholderMessageText.text = errorMessage
    }

    private fun showFavouriteStatus(isFavorite: Boolean) {
        if (isFavorite) binding.toolbarInclude.favourite.setImageResource(R.drawable.ic_favourite_on) else binding.toolbarInclude.favourite.setImageResource(
            R.drawable.ic_favourites
        )
    }


    private fun hideEmptyContactFields(state: DetailState) {
        when (state) {
            is DetailState.Content -> {
                val vacancy = state.vacancy
                val hasContacts = vacancy.contacts != null
                val hasPhones = !vacancy.contacts?.phones.isNullOrEmpty()
                val hasEmail = !vacancy.contacts?.email.isNullOrEmpty()
                val hasComments = vacancy.contacts?.phones?.
                any { phone -> !phone.comment.isNullOrEmpty() } ?: false

                binding.contactPerson.isVisible = hasContacts
                binding.contactPersonName.isVisible = hasContacts && !vacancy.contacts?.name.isNullOrEmpty()

                binding.emailTitle.isVisible = hasEmail
                binding.emailAddress.isVisible = hasEmail

                binding.contact.isVisible = hasPhones
                binding.contactPerson.isVisible = hasPhones

                binding.phoneTitle.isVisible = hasPhones
                binding.phone.isVisible = hasPhones

                binding.commentTitle.isVisible = hasComments
                binding.comment.isVisible = hasComments
            }
            else -> {

            }
        }
    }

}
