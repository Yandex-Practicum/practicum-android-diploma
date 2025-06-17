package ru.practicum.android.diploma.ui.vacancy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
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
        binding.vacancyName.text = "Загрузка..."
        // Позже можно добавить прогресс-бар
    }

    private fun showError() {
        binding.vacancyName.text = "Ошибка загрузки данных"
        // изображение ошибки
    }

    private fun showVacancyDetails(vacancy: VacancyDetailsVO) {
        val text = buildString {
            appendLine(vacancy.title)
            vacancy.salary?.let { appendLine(it) }
            vacancy.experience?.let { appendLine(it) }
            vacancy.employment?.let { appendLine(it) }
            vacancy.schedule?.let { appendLine(it) }
            appendLine()
            vacancy.addressOrRegion.let { appendLine(it) }
            appendLine()
            vacancy.description?.let { appendLine(it) }
        }

        binding.vacancyName.text = text
    }
}
