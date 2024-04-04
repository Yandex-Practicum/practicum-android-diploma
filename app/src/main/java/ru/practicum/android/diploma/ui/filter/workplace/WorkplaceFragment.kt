package ru.practicum.android.diploma.ui.filter.workplace

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentWorkplaceBinding

class WorkplaceFragment : Fragment() {
    private var _binding: FragmentWorkplaceBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<WorkplaceViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWorkplaceBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModel.getCountryInfo()

        viewModel.countryState.observe(viewLifecycleOwner) { country ->
            Log.d("StateMyCountry", "Мы получили во фрагменте $country")

        }

        binding.workplaceCount.setOnClickListener {
            findNavController().navigate(R.id.action_workplaceFragment_to_countFragment)
        }

        binding.workplaceRegion.setOnClickListener {
            findNavController().navigate(R.id.action_workplaceFragment_to_regionFragment)
        }

        binding.workplaceToolbar.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.workplaceButtonApply.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
