package ru.practicum.android.diploma.filter.filter.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.filter.R
import ru.practicum.android.diploma.filter.databinding.FragmentFilterBinding
import ru.practicum.android.diploma.filter.filter.presentation.viewmodel.FilterViewModel

const val ARGS_INDUSTRY_ID = "industry_id"
const val ARGS_INDUSTRY_NAME = "industry_name"

internal class FilterFragment : Fragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FilterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)

        var destinationID = 0
        findNavController().addOnDestinationChangedListener { _, destination, _ ->
            destinationID = findNavController().previousBackStackEntry?.destination?.id ?: 0
        }
        receiveBundles(destinationID)

        viewModel.filterOptionsListLiveData.observe(viewLifecycleOwner) { map ->
            updateUI(map)
        }

        return binding.root
    }

    private fun updateUI(map: Map<String, String>?) {
        if (map?.get(ARGS_INDUSTRY_NAME) != null) { // ❗❗ нужны нормальные вьюхи...
            binding.workIndustry.visibility = INVISIBLE
            binding.workIndustryInfo.isVisible = true
            binding.workIndustryInfo.text = map[ARGS_INDUSTRY_NAME]
        }

        // ⬅️ add your ui logic here ❗
    }

    private fun receiveBundles(destinationID: Int) {
        when (destinationID) { // use destinationID to determine the bundle contents type

            R.id.industryFragment -> { // by comparing with fragment's nav id
                val args = getArguments()
                if (args != null) { // if we get arguments - SP is updated
                    val industryID = args.getString(ARGS_INDUSTRY_ID)
                    val industryName = args.getString(ARGS_INDUSTRY_NAME)
                    if (industryID != null) {
                        viewModel.putValue(ARGS_INDUSTRY_ID, industryID)
                    }
                    if (industryName != null) {
                        viewModel.putValue(ARGS_INDUSTRY_NAME, industryName)
                    }
                }
                viewModel.loadFilterSettings() // even in case of no arguments filter settings are loaded
            }

            // ⬅️ add your fragment branches here ❗

            else -> { viewModel.loadFilterSettings() } // if none of the sub-screens' actions was used - just
            // load the filter settings
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.workPlace.setOnClickListener {
            findNavController().navigate(R.id.action_filterFragment_to_placeFragment)
        }
        binding.workIndustry.setOnClickListener {
            findNavController().navigate(R.id.action_filterFragment_to_industryFragment)
        }
        binding.workIndustryInfo.setOnClickListener {
            findNavController().navigate(R.id.action_filterFragment_to_industryFragment)
        } // ❗❗ неверное по оформлению поле которое выходит на замену серому placeholder перекрывает
        // клик, дублирую клик на него тоже ❗
        binding.buttonLeftFilter.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
