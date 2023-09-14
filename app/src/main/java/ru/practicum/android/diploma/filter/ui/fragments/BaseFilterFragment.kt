package ru.practicum.android.diploma.filter.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
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
import ru.practicum.android.diploma.databinding.FragmentMainFilterBinding
import ru.practicum.android.diploma.filter.data.converter.industryDtoListToIndustryList
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
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
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

            salary.doOnTextChanged { text, _, _, _ ->
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
            applyBtn.debounceClickListener(debouncer) {
                viewModel.saveFilterSettings()
                findNavController().navigateUp()
            }
            clearBtn.debounceClickListener(debouncer) {
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
            industryText.setText(selectedFilter.industry?.name ?: "")
            salary.setText(selectedFilter.salary ?: "")
            checkbox.isChecked = selectedFilter.visibility ?: false
            binding.btnGroup.visibility = View.VISIBLE
        }
    }
    
    private fun showEmpty() {
        binding.btnGroup.visibility = View.GONE
    }

}