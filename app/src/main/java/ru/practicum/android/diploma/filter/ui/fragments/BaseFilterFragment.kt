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
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterBaseBinding
import ru.practicum.android.diploma.filter.ui.models.BaseFilterScreenState
import ru.practicum.android.diploma.filter.ui.view_models.BaseFilterViewModel
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.util.thisName
import ru.practicum.android.diploma.util.viewBinding

class BaseFilterFragment : Fragment(R.layout.fragment_filter_base) {
    
    private val binding by viewBinding<FragmentFilterBaseBinding>()
    private val viewModel: BaseFilterViewModel by viewModels { (activity as RootActivity).viewModelFactory }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.handleData()
        initListeners()
        initViewModelObserver()
    }
    
    private fun initListeners() {
        with(binding) {
            filterToolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            workPlaceText.setOnFocusChangeListener { _, isFocus ->
                if (isFocus) {
                    findNavController().navigate(
                        BaseFilterFragmentDirections.actionFilterBaseFragmentToWorkPlaceFilterFragment()
                    )
                }
            }
            departmentText.setOnFocusChangeListener { _, isFocus ->
                if (isFocus) {
                    findNavController().navigate(
                        BaseFilterFragmentDirections.actionFilterBaseFragmentToDepartmentFragment()
                    )
                }
            }

            amountText.doOnTextChanged { text, _, _, _ ->
                if (text.isNullOrEmpty()) {
                    viewModel.saveSalary(text.toString())
                    amountTextLayout.endIconMode = TextInputLayout.END_ICON_NONE
                } else {
                    amountTextLayout.endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
                    amountTextLayout.endIconDrawable =
                        AppCompatResources.getDrawable(requireContext(), R.drawable.close_btn)
                }
            }

            amountText.setOnEditorActionListener { view, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    viewModel.saveSalary(view.text.toString())
                }
                return@setOnEditorActionListener false
            }
            
            applyFilterBtn.setOnClickListener {
                findNavController().navigateUp()
            }
            
            cancelFilterBtn.setOnClickListener {
                viewModel.cancelFilterBtnClicked()
            }
        }
    }

    private fun initViewModelObserver() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewModel.uiState.collect { state ->
                viewModel.log("BaseFilterFragment", "uiState.collect { state -> ${state.thisName}")
                when (state) {
                    is BaseFilterScreenState.Content -> state.render(binding)
                    is BaseFilterScreenState.Empty -> state.render(binding)
                }

            }
        }
    }
}