package ru.practicum.android.diploma.filter.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentMainFilterBinding
import ru.practicum.android.diploma.filter.domain.models.SelectedFilter
import ru.practicum.android.diploma.filter.ui.models.BaseFilterScreenState
import ru.practicum.android.diploma.filter.ui.view_models.BaseFilterViewModel
import ru.practicum.android.diploma.root.Debouncer
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.root.debounceClickListener
import ru.practicum.android.diploma.util.thisName
import ru.practicum.android.diploma.util.viewBinding

class BaseFilterFragment : Fragment(R.layout.fragment_main_filter) {

    private val debouncer = Debouncer()
    private val args by navArgs<BaseFilterFragmentArgs>()
    private val binding by viewBinding<FragmentMainFilterBinding>()
    private val viewModel: BaseFilterViewModel by viewModels { (activity as RootActivity).viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.handleData(args.selectedFilter)
        initListeners()
        initViewModelObserver()
    }
    
    private fun initListeners() {
        with(binding) {
            area.debounceClickListener(debouncer) {
                findNavController().navigate(
                    BaseFilterFragmentDirections
                        .actionBaseFilterToWorkPlaceFilter(viewModel.selectedFilter)
                )
            }
            industry.debounceClickListener(debouncer) {
                findNavController().navigate(
                    BaseFilterFragmentDirections
                        .actionBaseFilterToDepartmentFragment(viewModel.selectedFilter)
                )
            }
            workPlaceText.doOnTextChanged { text, _, _, _ ->
                renderEditTextColor(workPlace, text)
            }
            industryText.doOnTextChanged { text, _, _, _ ->
                renderEditTextColor(department, text)
            }
            salary.doOnTextChanged { text, _, _, _ ->
                renderSalaryTextColor(amountTextLayout, text)
                changeTextInputLayoutEndIconMode(text)
            }
            applyBtn.debounceClickListener(debouncer) {
                viewModel.saveFilterSettings()
                findNavController().navigateUp()
            }
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            areaIcon.debounceClickListener(debouncer) {
                onAreaIconPush(workPlaceText)
            }
            industryIcon.debounceClickListener(debouncer) {
                onIndustryIconPush(industryText)
            }
            showSalaryText.setOnClickListener {
                checkbox.isChecked = !checkbox.isChecked
            }
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                viewModel.changeCheckbox(isChecked)
            }
            clearBtn.debounceClickListener(debouncer) {
                viewModel.cancelFilterBtnClicked()
                showApplyBtn(SelectedFilter.empty)
            }
        }
    }

    private fun onAreaIconPush(view: EditText) {
        if (view.text.isEmpty()) {
            findNavController().navigate(
                BaseFilterFragmentDirections
                    .actionBaseFilterToWorkPlaceFilter(viewModel.selectedFilter)
            )
        } else {
            view.setText("")
            viewModel.changeArea()
            changeIcon(binding.workPlaceText, binding.areaIcon)
        }
    }

    private fun onIndustryIconPush(view: EditText) {
        if (view.text.isEmpty()) {
            findNavController().navigate(
                BaseFilterFragmentDirections
                    .actionBaseFilterToDepartmentFragment(viewModel.selectedFilter)
            )
        } else {
            view.setText("")
            viewModel.changeIndustry()
            changeIcon(binding.industryText, binding.industryIcon)
        }
    }

    private fun initViewModelObserver() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewModel.uiState.collect { state ->
                viewModel.log("BaseFilterFragment", "uiState.collect { state -> ${state.thisName}")
                when (state) {
                    is BaseFilterScreenState.Content -> showContent(state.selectedFilter)
                    is BaseFilterScreenState.Empty -> showEmpty()
                }
            }
        }
    }
    
    private fun showContent(selectedFilter: SelectedFilter) {
        with(binding) {
            showAreaField(selectedFilter)
            industryText.setText(selectedFilter.industry?.name ?: "")
            changeIcon(industryText, industryIcon)
            salary.setText(selectedFilter.salary ?: "")
            checkbox.isChecked = selectedFilter.onlyWithSalary
            showApplyBtn(selectedFilter)
        }
    }

    private fun showApplyBtn(selectedFilter: SelectedFilter) {
        if (selectedFilter != SelectedFilter.empty) {
            binding.btnGroup.visibility = View.VISIBLE
        }
    }


    private fun showAreaField(selectedFilter: SelectedFilter) {
        val workPlace = StringBuilder()
        workPlace.append(selectedFilter.country?.name ?: "")
        if (!selectedFilter.region?.name.isNullOrEmpty()) workPlace.append(", ")
        workPlace.append(selectedFilter.region?.name ?: "")
        with(binding) {
            workPlaceText.setText(workPlace)
            changeIcon(workPlaceText, areaIcon)
        }
    }

    private fun changeIcon(editText: EditText, view: ImageView) {
        if (editText.text.isEmpty())
            view.setImageResource(R.drawable.leading_icon)
        else
            view.setImageResource(R.drawable.ic_clear)
    }


    private fun showEmpty() {
        binding.btnGroup.visibility = View.GONE
    }
    
    private fun changeTextInputLayoutEndIconMode(text: CharSequence?) {
        with(binding) {
            if (text.isNullOrEmpty()) {
                viewModel.changeSalary(null)
                amountTextLayout.endIconMode = TextInputLayout.END_ICON_NONE
            } else {
                viewModel.changeSalary(text.toString())
                amountTextLayout.endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
                amountTextLayout.endIconDrawable =
                    AppCompatResources.getDrawable(requireContext(), R.drawable.close_btn)
            }
        }
    }
    
    private fun renderEditTextColor(view: TextInputLayout, text: CharSequence?) {
        if (!text.isNullOrEmpty()) {
            view.defaultHintTextColor = ContextCompat.getColorStateList(
                requireContext(), R.color.filter_text_color_enabled
            )
        } else {
            view.defaultHintTextColor = ContextCompat.getColorStateList(
                requireContext(), R.color.filter_text_color
            )
        }
    }
    private fun renderSalaryTextColor(view: TextInputLayout, text: CharSequence?) {
        if (!text.isNullOrEmpty()) {
            view.defaultHintTextColor = ContextCompat.getColorStateList(
                requireContext(), R.color.hint_text_color_enabled
            )
        } else {
            view.defaultHintTextColor = ContextCompat.getColorStateList(
                requireContext(), R.color.hint_text_color
            )
        }
    }
}