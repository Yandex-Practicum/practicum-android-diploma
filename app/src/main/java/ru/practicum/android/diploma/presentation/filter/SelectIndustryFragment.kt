package ru.practicum.android.diploma.presentation.filter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentSelectIndustryBinding
import ru.practicum.android.diploma.domain.models.filter.Industry
import ru.practicum.android.diploma.presentation.filter.chooseArea.state.IndustriesState
import ru.practicum.android.diploma.presentation.filter.selectArea.adaptor.IndystryAdapter

class SelectIndustryFragment : Fragment() {
    private var _binding: FragmentSelectIndustryBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<SelectIndustryViewModel>()
    private val listIndustry = mutableListOf<Industry>()
    private var industriesAdapter: IndystryAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectIndustryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeIndustriesState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is IndustriesState.DisplayIndustries -> displayIndustries(state.industries)
                is IndustriesState.Error -> displayError(state.errorText)
            }
        }

        binding.chooseIndustryBackArrowImageview.setOnClickListener {
            findNavController().popBackStack()
        }

        setupSearchInput()


        viewModel.initScreen()
        viewModel.loadSelectedIndustry()

    }

    private fun setupSearchInput() {
        binding.chooseIndustryEnterFieldEdittext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // No implementation needed
            }

            override fun onTextChanged(
                charSequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                // No implementation needed
            }

            override fun afterTextChanged(editable: Editable?) {
                editable?.toString()?.let { query ->
                    viewModel.filterIndustries(query)
                }
            }
        })
    }


    private fun displayIndustries(industries: ArrayList<Industry>) {
        binding.apply {
            chooseIndustryListRecycleView.visibility = View.VISIBLE
            errorIndustryLayout.visibility = View.GONE
        }
        if (industriesAdapter == null) {
            industriesAdapter = IndystryAdapter(listIndustry) { industry ->
                viewModel.onIndustryClicked(industry)
                val position = industries.indexOf(industry)
                industries[position] = industry.copy(isChecked = !industry.isChecked)
                viewModel.onIndustryClicked(industry)
                findNavController().popBackStack()
                industriesAdapter?.notifyItemChanged(position)
            }
            binding.chooseIndustryListRecycleView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = industriesAdapter
            }
        }
        listIndustry.clear()
        listIndustry.addAll(industries)
        industriesAdapter!!.notifyDataSetChanged()
    }

    private fun displayError(errorText: String) {
        binding.apply {
            chooseIndustryListRecycleView.visibility = View.INVISIBLE
            errorIndustryLayout.visibility = View.VISIBLE
            industryErrorText.text = errorText
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}