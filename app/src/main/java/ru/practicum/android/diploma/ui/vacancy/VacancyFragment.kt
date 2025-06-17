package ru.practicum.android.diploma.ui.vacancy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.ui.root.BindingFragment

class VacancyFragment : BindingFragment<FragmentVacancyBinding>() {
    private val args: VacancyFragmentArgs by navArgs()
    private val viewModel by viewModel<VacancyViewModel>()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentVacancyBinding {
        return FragmentVacancyBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUiToolbar()

        viewModel.loadVacancyDetails(args.vacancyId)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.vacancyState.collect { state ->
                    render(state)
                }
            }
        }
    }

    private fun render(state: VacancyContentStateVO) {
        when (state) {
            is VacancyContentStateVO.Base -> showBase()
            is VacancyContentStateVO.Loading -> showLoading()
            is VacancyContentStateVO.Error -> showError()
            is VacancyContentStateVO.Success -> showVacancyDetails(state.vacancy)
        }
    }

    private fun showBase() {
        binding.vacancyName.text = ""
    }

    private fun showLoading() {
        binding.includedProgressBar.porgressBar.isVisible = true
        binding.includedErr.placeholderImage.isVisible = false
        binding.includedErr.placeholderText.isVisible = false
    }

    private fun showError() {
        binding.includedErr.placeholderImage.isVisible = true
        binding.includedErr.placeholderText.isVisible = true
    }

    private fun showVacancyDetails(vacancy: VacancyDetailsVO) {
        binding.apply {
            includedProgressBar.porgressBar.isVisible = false
            binding.includedErr.placeholderImage.isVisible = false
            binding.includedErr.placeholderText.isVisible = false
            vacancyName.text = vacancy.title
            vacancySalary.text = vacancy.salary

            if (!vacancy.logoUrl.isNullOrBlank()) {
                Glide.with(requireContext())
                    .load(vacancy.logoUrl)
                    .placeholder(R.drawable.vacancy_artwork_placeholder)
                    .error(R.drawable.vacancy_artwork_placeholder)
                    .fitCenter()
                    .into(binding.includedVacancyCard.imageVacancyCard)
            } else {
                binding.includedVacancyCard.imageVacancyCard.setImageResource(R.drawable.vacancy_artwork_placeholder)
            }

            includedVacancyCard.titleVacancyCard.text = vacancy.employerName
            includedVacancyCard.cityVacancyCard.text = vacancy.addressOrRegion
            valueExp.text = vacancy.experience
            valueWorkFormat.text = vacancy.employment
            valueDescription.text = vacancy.description
        }
    }

    private fun initUiToolbar() {
        // настройка кастомного топбара
        val toolbar = binding.toolbar
        toolbar.setupToolbarForVacancyDetailScreen()
        toolbar.setToolbarTitle(getString(R.string.vacancy))
        toolbar.setupToolbarBackButton(this)

        // Поделиться
        toolbar.setOnToolbarShareClickListener {
            /* !!! Здесь будет Intent */
        }

        // Избранное
        toolbar.setOnToolbarFavoriteClickListener {
            /* !!! Реализация добавления в избранное */
        }
    }
}
