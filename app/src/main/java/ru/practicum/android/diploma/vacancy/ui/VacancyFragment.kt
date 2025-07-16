package ru.practicum.android.diploma.vacancy.ui

import android.annotation.SuppressLint
import android.content.Intent
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
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.vacancy.domain.model.VacancyDetails
import ru.practicum.android.diploma.vacancy.ui.viewmodel.VacancyViewModel

class VacancyFragment : Fragment() {
    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!

    private val viewModel: VacancyViewModel by viewModel {
        parametersOf(requireArguments().getString("vacancyId"))
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

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                render(state)
            }
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.likeButton.setOnClickListener {
            viewModel.onFavoriteClicked()
        }
    }

    private fun render(state: VacancyState) {
        binding.progressBar.isVisible = state is VacancyState.Loading
        binding.serverError.isVisible = state is VacancyState.Error && state.message == "server_error"
        binding.notfoundError.isVisible = state is VacancyState.Error && state.message == "not_found"

        val contentViews = listOf(
            binding.jobTitle,
            binding.jobPrice,
            binding.companyCard.root,
            binding.experienceTitle,
            binding.experienceDescription,
            binding.employmentType,
            binding.jobDescriptionTitle,
            binding.jobDescription
        )
        contentViews.forEach { it.isVisible = state is VacancyState.Content }

        if (state is VacancyState.Content) {
            fillContent(state.vacancy)
            updateFavoriteIcon(state.isFavorite)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun fillContent(vacancy: VacancyDetails) {
        with(binding) {
            jobTitle.text = vacancy.name
            jobPrice.text = vacancy.salary
            companyCard.companyName.text = vacancy.employerName
            companyCard.companyLocation.text = vacancy.area
            experienceDescription.text = vacancy.experience
            employmentType.text = "${vacancy.employment}, ${vacancy.schedule}"

            jobDescription.text = Html.fromHtml(vacancy.description, Html.FROM_HTML_MODE_COMPACT)

            Glide.with(requireContext())
                .load(vacancy.employerLogoUrl)
                .placeholder(R.drawable.vacancy_logo_placeholder)
                .error(R.drawable.vacancy_logo_placeholder)
                .centerCrop()
                .into(companyCard.companyLogo)

            binding.shareButton.setOnClickListener {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, vacancy.url)
                }
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_vacancy)))
            }
        }
    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        val iconRes = if (isFavorite) R.drawable.like_checked_icon_light else R.drawable.like_unchecked_icon_light
        binding.likeButton.setImageResource(iconRes)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
