package ru.practicum.android.diploma.ui.details

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
            viewModel.call(requireContext())
        }
        binding.favoriteIcon.setOnClickListener{
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
                binding.progressBar.isVisible = true
                binding.scrollView.isVisible = false
            }

            is DetailsViewState.Content -> {
                binding.vacancyTitleTextView.text = state.name
                setTextOrHide(state.salary, binding.salaryTextView)
                setTextOrHide(state.companyName, binding.companyTitleTextView)
                setTextOrHide(state.city, binding.companyCityTextView)
                setTextOrHide(state.experience, binding.experienceTextView, binding.experienceLinearLayout)
                setTextOrHide(state.employment, binding.employmentTypeTextView)
                setTextOrHide(state.contactName, binding.contactNameTextView, binding.contactNameContainerLinearLayout)
                setTextOrHide(state.contactEmail, binding.emailTextView, binding.emailContainerLinearLayout)
                setTextOrHide(
                    state.contactsPhones?.first(),
                    binding.contactPhoneTextView,
                    binding.contactPhoneContainerLinearLayout
                )

                binding.contactsTitleTextView.isVisible = !(
                    state.contactName.isNullOrEmpty()
                        && state.contactEmail.isNullOrEmpty()
                        && state.contactsPhones.isNullOrEmpty())

                binding.vacancyDescriptionTextView.text = Html.fromHtml(state.description)

                val cornerRadius = requireContext().resources.getDimensionPixelSize(R.dimen.s_margin)
                Glide.with(binding.companyImageView)
                    .load(state.companyLogo)
                    .transform(RoundedCorners(cornerRadius))
                    .placeholder(R.drawable.spiral)
                    .into(binding.companyImageView)

                binding.progressBar.isVisible = false
                binding.scrollView.isVisible = true

                checkIsVacancyFavorite()
            }

            is DetailsViewState.IsVacancyFavorite -> {
                if(state.isFavorite){
                    binding.iconIsFavorite.visibility = View.VISIBLE
                    binding.iconIsNotFavorite.visibility = View.INVISIBLE
                }else{
                    binding.iconIsFavorite.visibility = View.INVISIBLE
                    binding.iconIsNotFavorite.visibility = View.VISIBLE
                }
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

    private fun checkIsVacancyFavorite(){
        viewModel.isVacancyFavorite()
    }


    companion object {
        const val vacancyIdKey = "vacancyDetails.id.key"
    }
}
