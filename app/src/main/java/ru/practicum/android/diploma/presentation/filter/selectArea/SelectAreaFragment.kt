package ru.practicum.android.diploma.presentation.filter.selectArea

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
import ru.practicum.android.diploma.databinding.FragmentSelectAreaBinding
import ru.practicum.android.diploma.domain.models.filter.Area
import ru.practicum.android.diploma.presentation.filter.selectArea.adaptor.AreaAdapter
import ru.practicum.android.diploma.presentation.filter.selectArea.state.AreasState

class SelectAreaFragment : Fragment() {


    private var _binding: FragmentSelectAreaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SelectAreaViewModel by viewModel()
    private var areasAdapter: AreaAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectAreaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeAreasState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is AreasState.DisplayAreas -> displayAreas(state.areas)
                is AreasState.Error -> displayError(state.errorText)
            }
        }

        binding.chooseAreaBackArrowImageview.setOnClickListener {
            findNavController().popBackStack()
        }

        setupSearchInput()


        viewModel.initScreen()
        viewModel.loadSelectedArea()
    }

    private fun setupSearchInput() {
        binding.chooseAreaEnterFieldEdittext.addTextChangedListener(object : TextWatcher {
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
                    viewModel.filterAreas(query)
                }
            }
        })
    }

    private fun displayAreas(areas: ArrayList<Area>) {
        binding.apply {
            chooseAreaListRecycleView.visibility = View.VISIBLE
            errorAreasLayout.visibility = View.GONE
        }

        if (areasAdapter == null) {
            areasAdapter = AreaAdapter(areas) { area ->
                viewModel.onAreaClicked(area)
                val position = areas.indexOf(area)
                areas[position] = area.copy(isChecked = !area.isChecked)
                viewModel.onAreaClicked(area)
                findNavController().popBackStack()
                areasAdapter?.notifyItemChanged(position)
            }

            binding.chooseAreaListRecycleView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = areasAdapter
            }
        } else {
            // TODO:
        }
    }


    private fun displayError(errorText: String) {
        binding.apply {
            chooseAreaListRecycleView.visibility = View.INVISIBLE
            errorAreasLayout.visibility = View.VISIBLE
            areasErrorText.text = errorText
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
