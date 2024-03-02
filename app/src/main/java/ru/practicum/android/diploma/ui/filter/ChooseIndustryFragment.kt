package ru.practicum.android.diploma.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentFilterChooseIndustryBinding

class ChooseIndustryFragment: Fragment() {
    private var _binding: FragmentFilterChooseIndustryBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<ChooseIndustryViewModel>()
    private var adapter = IndustriesAdapter {
        binding.textView2.isVisible = it != -1
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFilterChooseIndustryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getIndustries()
        binding.industryList.adapter = this.adapter
        binding.expectedSalary1.doOnTextChanged { text, _, _, _ ->
            viewModel.filterIndustries(text.toString())
        }
        viewModel.industriesState.observe(viewLifecycleOwner) { state ->
            when(state) {
                IndustriesState.Error -> {}
                IndustriesState.Initial -> Unit
                IndustriesState.Loading -> {}
                is IndustriesState.Success -> {
                    adapter.updateList(state.data)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
