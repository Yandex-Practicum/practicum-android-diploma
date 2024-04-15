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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailsBinding.inflate(layoutInflater)
        setUpListeners()
        setUpObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onViewCreated(this)
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
        binding.vacancyTitleTextView.text = data.name
        setTextOrHide(data.salary, binding.salaryTextView)
        setTextOrHide(data.companyName, binding.companyTitleTextView)
        if (data.fullAddress.isNullOrEmpty()) {
            binding.companyCityTextView.text = data.areaName
        } else {
            binding.companyCityTextView.text = data.fullAddress
        }
        setTextOrHide(data.experience, binding.experienceTextView, binding.experienceLinearLayout)
        setTextOrHide(data.employment, binding.employmentTypeTextView)
        setTextOrHide(data.contactName, binding.contactNameTextView, binding.contactNameContainerLinearLayout)
        setTextOrHide(data.contactEmail, binding.emailTextView, binding.emailContainerLinearLayout)
        setTextOrHide(
            data.contactsPhones?.first(),
            binding.contactPhoneTextView,
            binding.contactPhoneContainerLinearLayout
        )
        setTextOrHide(
            data.keySkills,
            binding.keySkillsTextView,
            binding.keySkillsContainerLinearLayout
        )
        binding.contactsTitleTextView.isVisible = !(
            data.contactName.isNullOrEmpty()
                && data.contactEmail.isNullOrEmpty()
                && data.contactsPhones.isNullOrEmpty())
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
            viewModel.writeEmail()
        }
        binding.contactPhoneTextView.setOnClickListener {
            viewModel.call()
        }
        binding.favoriteIcon.setOnClickListener {
            viewModel.favoriteIconClicked()
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

    private fun checkIfVacancyFavorite() {
        viewModel.isVacancyFavorite()
    }

    companion object {
        const val vacancyIdKey = "vacancyDetails.id.key"
    }
}
