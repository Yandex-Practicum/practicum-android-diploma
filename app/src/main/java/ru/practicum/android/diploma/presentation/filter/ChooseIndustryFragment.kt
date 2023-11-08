package ru.practicum.android.diploma.presentation.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentChooseIndustryBinding
import ru.practicum.android.diploma.domain.models.filter.Industry
import ru.practicum.android.diploma.presentation.filter.chooseArea.adaptor.FilterAdapter
import ru.practicum.android.diploma.presentation.filter.chooseArea.state.IndustriesState

class ChooseIndustryFragment: Fragment() {
    private var _binding: FragmentChooseIndustryBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<ChooseIndustryViewModel>()

    private var industriesAdapter: FilterAdapter<Industry>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseIndustryBinding.inflate(inflater, container, false)
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
    }

    private fun displayIndustries(industries: ArrayList<Industry>) {
        binding.apply {
            chooseIndustryListRecycleView.visibility = View.VISIBLE
            errorIndustryLayout.visibility = View.GONE
        }
        if (industriesAdapter == null) {
            industriesAdapter =
                FilterAdapter(industries) { industry, position, notifyItemChanged, setPositionChecked ->
                    viewModel.onIndustryClicked(industry as Industry)
                    industries[position] = industry.copy(isChecked = !industry.isChecked)
                    notifyItemChanged.invoke()
                    setPositionChecked.invoke(industries[position].isChecked)
                }
            binding.chooseIndustryListRecycleView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = industriesAdapter
            }
        } else {
            //todo
        }
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