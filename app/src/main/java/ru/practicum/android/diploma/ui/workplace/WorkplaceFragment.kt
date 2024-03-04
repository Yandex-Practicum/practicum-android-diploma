package ru.practicum.android.diploma.ui.workplace

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentWorkplaceBinding

class WorkplaceFragment : Fragment() {

    private lateinit var binding: FragmentWorkplaceBinding
    private val viewModel by viewModel<WorkplaceViewModel>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View    {
        binding = FragmentWorkplaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vacancyToolbar.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.country.setOnClickListener {
            findNavController().navigate(
                R.id.action_workplaceFragment_to_countryFragment,
            )
        }

        binding.region.setOnClickListener {
            findNavController().navigate(
                R.id.action_workplaceFragment_to_regionFragment,
            )
        }


//        viewModel.observeState().observe(viewLifecycleOwner) {
//            binding.countryName.text = it
//            binding.countryName.setTextColor(ContextCompat.getColor(requireContext(), R.color.YP_Black))
//        }

        setFragmentResultListener("requestKey") { _, result ->
            val data = result.getString("key")
            binding.countryName.text = data
            binding.countryName.setTextColor(ContextCompat.getColor(requireContext(), R.color.YP_Black))
        }

        setFragmentResultListener("requestKeyRegion") { _, result ->
            val data = result.getString("keyRegion")
            binding.regionName.text = data
            binding.regionName.setTextColor(ContextCompat.getColor(requireContext(), R.color.YP_Black))
        }

        binding.button.setOnClickListener{
            val country = binding.countryName.text
            val region = binding.regionName.text
            val bundle = Bundle()
            if (region!="Регион")
                bundle.putString("keyPlace", "$country, $region")
            else
                bundle.putString("keyPlace", "$country")
            setFragmentResult("requestKeyPlace", bundle)
            findNavController().navigateUp()
        }

    }
}
