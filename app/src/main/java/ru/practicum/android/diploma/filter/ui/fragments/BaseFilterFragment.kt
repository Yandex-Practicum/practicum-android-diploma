package ru.practicum.android.diploma.filter.ui.fragments

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.content.res.AppCompatResources
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
import ru.practicum.android.diploma.databinding.FragmentFilterBaseBinding
import ru.practicum.android.diploma.filter.domain.models.SelectedFilter
import ru.practicum.android.diploma.filter.ui.models.BaseFilterScreenState
import ru.practicum.android.diploma.filter.ui.view_models.BaseFilterViewModel
import ru.practicum.android.diploma.root.Debouncer
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.root.debounceClickListener
import ru.practicum.android.diploma.util.thisName
import ru.practicum.android.diploma.util.viewBinding
import javax.inject.Inject

class BaseFilterFragment : Fragment(R.layout.fragment_filter_base) {

    private val debouncer = Debouncer()
    private val args by navArgs<BaseFilterFragmentArgs>()
    private val binding by viewBinding<FragmentFilterBaseBinding>()
    private val viewModel: BaseFilterViewModel by viewModels { (activity as RootActivity).viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.handleData(args.selectedFilter)
        initListeners()
        initViewModelObserver()
    }
    
    private fun initListeners() {
        with(binding) {
            filterToolbar.setNavigationOnClickListener {
                viewModel.saveFilterSettings()
                findNavController().navigateUp()
            }
            workPlaceText.setOnFocusChangeListener { _, isFocus ->
                if (isFocus) {
                    findNavController().navigate(
                        BaseFilterFragmentDirections
                            .actionBaseFilterToWorkPlaceFilter(viewModel.selectedFilter)
                    )
                }
            }
            departmentText.debounceClickListener(debouncer) {
                findNavController().navigate(
                    BaseFilterFragmentDirections
                        .actionBaseFilterToDepartmentFragment(viewModel.selectedFilter)
                )
            }
            amountText.doOnTextChanged { text, _, _, _ ->
                if (text.isNullOrEmpty()) {
                    viewModel.changeSalary(null)
                    amountTextLayout.endIconMode = TextInputLayout.END_ICON_NONE
                } else {
                    viewModel.changeSalary(null)
                    amountTextLayout.endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
                    amountTextLayout.endIconDrawable =
                        AppCompatResources.getDrawable(requireContext(), R.drawable.close_btn)
                }
            }
            applyFilterBtn.debounceClickListener(debouncer) {
                viewModel.saveFilterSettings()
                findNavController().navigateUp()
            }
            cancelFilterBtn.debounceClickListener(debouncer) {
                viewModel.cancelFilterBtnClicked()
            }
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
            val workPlace = StringBuilder()
            workPlace.append(selectedFilter.country?.name ?: "")
            if (!selectedFilter.region?.name.isNullOrEmpty()) workPlace.append(", ")
            workPlace.append(selectedFilter.region?.name ?: "")
            
            workPlaceText.setText(workPlace)
            departmentText.setText(selectedFilter.industry?.name ?: "")
            changeTextInputLayoutEndIconMode()
            
        }
    }
    
    private fun showEmpty() {
        with(binding) {
            chooseBaseFilterBtn.visibility = View.GONE
            bottomContainerToApply.visibility = View.GONE
        }
    }
    
    private fun changeTextInputLayoutEndIconMode() {
        if (binding.departmentText.text.isNullOrEmpty()) {
            binding.departmentContainer.endIconMode = TextInputLayout.END_ICON_CUSTOM
            binding.departmentContainer.endIconDrawable =
                AppCompatResources.getDrawable(requireContext(), R.drawable.leading_icon)
        } else {
            binding.departmentContainer.requestFocus()
            binding.departmentContainer.endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
            binding.departmentContainer.endIconDrawable =
                AppCompatResources.getDrawable(requireContext(), R.drawable.ic_clear)
        }
    }
}