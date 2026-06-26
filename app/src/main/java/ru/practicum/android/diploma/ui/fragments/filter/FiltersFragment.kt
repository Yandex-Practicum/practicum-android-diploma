package ru.practicum.android.diploma.ui.fragments.filter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltersBinding
import ru.practicum.android.diploma.domain.models.FilterSettings
import ru.practicum.android.diploma.presentation.viewmodels.FiltersState
import ru.practicum.android.diploma.presentation.viewmodels.FiltersViewModel

class FiltersFragment : Fragment() {
    private var _binding: FragmentFiltersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FiltersViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFiltersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner) { state ->
            render(state)
        }

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.workPlaceContainer.setOnClickListener {
            findNavController().navigate(R.id.action_filtersFragment_to_workPlaceSelectionFragment)
        }

        binding.industryContainer.setOnClickListener {
            findNavController().navigate(R.id.action_filtersFragment_to_industrySelectionFragment)
        }

        binding.workPlaceClear.setOnClickListener {
            viewModel.clearWorkPlace()
        }

        binding.industryClear.setOnClickListener {
            viewModel.clearIndustry()
        }

        binding.salaryEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setSalary(s?.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.noSalaryCheckbox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setNotShowWithoutSalary(isChecked)
        }

        binding.applyButton.setOnClickListener {
            viewModel.applyFilters()
            findNavController().navigateUp()
        }

        binding.resetButton.setOnClickListener {
            viewModel.resetFilters()
        }

        viewModel.loadSettings()
    }

    private fun render(state: FiltersState) {
        when (state) {
            is FiltersState.Loading -> {
                // Можно добавить ProgressBar если нужно
            }

            is FiltersState.Content -> {
                renderContent(state.settings, state.canApply, state.canReset)
            }
        }
    }

    private fun renderContent(settings: FilterSettings, canApply: Boolean, canReset: Boolean) {
        // Место работы
        if (settings.countryName != null) {
            binding.workPlaceValue.isVisible = true
            binding.workPlaceValue.text = if (settings.regionName != null) {
                "${settings.countryName}, ${settings.regionName}"
            } else {
                settings.countryName
            }
            binding.workPlaceLabel.setTextColor(resources.getColor(R.color.color_text_secondary, null))
            binding.workPlaceArrow.isVisible = false
            binding.workPlaceClear.isVisible = true
        } else {
            binding.workPlaceValue.isVisible = false
            binding.workPlaceLabel.text = getString(R.string.work_place)
            binding.workPlaceLabel.setTextColor(resources.getColor(R.color.color_text_secondary, null))
            binding.workPlaceArrow.isVisible = true
            binding.workPlaceClear.isVisible = false
        }

        // Отрасль
        if (settings.industryName != null) {
            binding.industryValue.isVisible = true
            binding.industryValue.text = settings.industryName
            binding.industryLabel.setTextColor(resources.getColor(R.color.color_text_secondary, null))
            binding.industryArrow.isVisible = false
            binding.industryClear.isVisible = true
        } else {
            binding.industryValue.isVisible = false
            binding.industryLabel.text = getString(R.string.industry)
            binding.industryLabel.setTextColor(resources.getColor(R.color.color_text_secondary, null))
            binding.industryArrow.isVisible = true
            binding.industryClear.isVisible = false
        }

        // Зарплата
        if (binding.salaryEditText.text.toString() != (settings.expectedSalary?.toString() ?: "")) {
            binding.salaryEditText.setText(settings.expectedSalary?.toString() ?: "")
        }

        // Чекбокс
        if (binding.noSalaryCheckbox.isChecked != settings.notShowWithoutSalary) {
            binding.noSalaryCheckbox.isChecked = settings.notShowWithoutSalary
        }

        // Кнопки
        binding.applyButton.isVisible = canApply
        binding.resetButton.isVisible = canReset
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
