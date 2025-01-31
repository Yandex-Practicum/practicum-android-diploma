package ru.practicum.android.diploma.filter.ui

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
import ru.practicum.android.diploma.databinding.FragmentFilterIndustryBinding
import ru.practicum.android.diploma.filter.domain.model.Industry
import ru.practicum.android.diploma.filter.domain.model.IndustryViewState
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterScreenViewModel
import ru.practicum.android.diploma.filter.ui.adapters.IndustryAdapter

class FilterIndustryFragment : Fragment() {
    private var _binding: FragmentFilterIndustryBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<FilterScreenViewModel>()
    private var listAdapter = IndustryAdapter { clickOnIndustry(it) }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFilterIndustryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
            }
        }
        textWatcher.let { binding.textInput.addTextChangedListener(it) }



        binding.topBar.setOnClickListener {
            findNavController().popBackStack()
        }
        viewModel.observeState().observe(viewLifecycleOwner) {
                 render(it)
        }
        viewModel.getIndustries("") //<- запрос отраслей

        binding.rvIndustryList.adapter = listAdapter

    }
    private fun render(state: IndustryViewState){
        when (state) {
            is IndustryViewState.Success -> listAdapter.setIndustries(state.industryList)
            else ->{}
        }
    }


    private fun clickOnIndustry(industry: Industry) {
        binding.btnSelectIndustry.isVisible = true
        viewModel.setIndustry(industry)
    }

}
