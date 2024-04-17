package ru.practicum.android.diploma.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<DetailsViewModel>()

    private var contactName: String? = null
    private var contactEmail: String? = null
    private var contactPhone: String? = null
    private var contactComment: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailsBinding.inflate(layoutInflater)
        setUpListeners()
        setUpObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vacancyId = requireArguments().getString(vacancyIdKey)

        if (vacancyId == null) {
            assert(false) { "Vacancy id should be passed" }
            return
        }
        viewModel.onViewCreated(vacancyId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
        binding.scrollView.isVisible = false
        binding.errorContainer.isVisible = false
    }

    private fun showError() {
        binding.progressBar.isVisible = false
        binding.scrollView.isVisible = false
        binding.errorContainer.isVisible = true
    }

    private fun showScrollViewData() {
        binding.progressBar.isVisible = false
        binding.scrollView.isVisible = true
        binding.errorContainer.isVisible = false
    }

    private fun showFavoriteVacancyButton(isFavorite: Boolean) {
        if (isFavorite) {
            binding.iconIsFavorite.visibility = View.VISIBLE
            binding.iconIsNotFavorite.visibility = View.INVISIBLE
        } else {
            binding.iconIsFavorite.visibility = View.INVISIBLE
            binding.iconIsNotFavorite.visibility = View.VISIBLE
        }
    }

    private fun setTextFields(data: DetailsViewState.Content) {
        contactName = data.contactName ?: requireArguments().getString(vacancyNameKey)
        contactEmail = data.contactEmail ?: requireArguments().getString(vacancyEmailKey)
        contactPhone = data.contactPhone ?: requireArguments().getString(vacancyPhoneKey)
        contactComment = data.contactComment ?: requireArguments().getString(vacancyCommentKey)

        binding.vacancyTitleTextView.text = data.name
        setSalary(data.salaryFrom, data.salaryTo, data.currency)
        setTextOrHide(data.companyName, binding.companyTitleTextView)
        if (data.fullAddress.isNullOrEmpty()) {
            binding.companyCityTextView.text = data.areaName
        } else {
            binding.companyCityTextView.text = data.fullAddress
        }
        setTextOrHide(data.experience, binding.experienceTextView, binding.experienceLinearLayout)
        setTextOrHide(data.employment, binding.employmentTypeTextView)
        setTextOrHide(
            data.keySkills,
            binding.keySkillsTextView,
            binding.keySkillsContainerLinearLayout
        )
        setTextOrHide(contactName, binding.contactNameTextView,
            binding.contactNameContainerLinearLayout)
        setTextOrHide(contactEmail, binding.emailTextView,
            binding.emailContainerLinearLayout)
        setTextOrHide(contactPhone,
            binding.contactPhoneTextView,
            binding.contactPhoneContainerLinearLayout
        )
        setTextOrHide(contactComment,
            binding.contactCommentTextView,
            binding.contactCommentContainerLinearLayout
        )
        binding.contactsTitleTextView.isVisible = !(
            contactName.isNullOrEmpty()
                && contactEmail.isNullOrEmpty()
                && contactPhone.isNullOrEmpty()
                && contactComment.isNullOrEmpty()
            )
        binding.vacancyDescriptionTextView.text = HtmlCompat.fromHtml(
            data.description.replace(Regex("<li>\\s<p>|<li>"), "<li> "),
            HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM
        )
    }

    private fun setUpListeners() {
        binding.backImageView.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.shareImageView.setOnClickListener {
            viewModel.shareVacancy()
        }
        binding.emailTextView.setOnClickListener {
            viewModel.writeEmail(contactEmail)
        }
        binding.contactPhoneTextView.setOnClickListener {
            viewModel.call(contactPhone)
        }
        binding.favoriteIcon.setOnClickListener {
            viewModel.favoriteIconClicked(
                contactEmail,
                contactName,
                contactPhone,
                contactComment
            )
        }
    }

    private fun setUpObservers() {
        viewModel.observeState().observe(viewLifecycleOwner) {
            renderState(it)
        }
    }

    private fun renderState(state: DetailsViewState) {
        when (state) {
            is DetailsViewState.Loading -> {
                showLoading()
            }

            is DetailsViewState.Error -> {
                showError()
            }

            is DetailsViewState.Content -> {
                setTextFields(state)
                val cornerRadius = requireContext().resources.getDimensionPixelSize(R.dimen.s_margin)

                Glide.with(binding.companyImageView)
                    .load(state.companyLogo)
                    .transform(RoundedCorners(cornerRadius))
                    .placeholder(R.drawable.logo_placeholder)
                    .into(binding.companyImageView)

                showScrollViewData()
                checkIfVacancyFavorite()
            }

            is DetailsViewState.IsVacancyFavorite -> {
                showFavoriteVacancyButton(state.isFavorite)
            }

            is DetailsViewState.ToastPermissionDenied -> {
                Toast.makeText(
                    context,
                    getString(R.string.call_permission_text),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setTextOrHide(text: String?, textView: TextView, container: View? = null) {
        if (text.isNullOrEmpty()) {
            textView.isVisible = false
            container?.isVisible = false
        } else {
            textView.text = text
            textView.isVisible = true
            container?.isVisible = true
        }
    }

    private fun setSalary(from: String?, to: String?, currency: String?) {
        var text = ""
        if (from != null) {
            text += "${requireContext().getString(R.string.from)} $from "
        }
        if (to != null) {
            text += "${requireContext().getString(R.string.to)} $to "
        }
        if (text.isNotEmpty()) {
            if (currency != null) {
                text += currency
            }
            binding.salaryTextView.text = text
            binding.salaryTextView.isVisible = true
        } else {
            binding.salaryTextView.isVisible = false
        }
    }

    private fun checkIfVacancyFavorite() {
        viewModel.isVacancyFavorite()
    }

    companion object {
        const val vacancyIdKey = "vacancyDetails.id.key"
        const val vacancyNameKey = "vacancyDetails.name.key"
        const val vacancyEmailKey = "vacancyDetails.email.key"
        const val vacancyPhoneKey = "vacancyDetails.phone.key"
        const val vacancyCommentKey = "vacancyDetails.comment.key"
    }
}
