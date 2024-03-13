package ru.practicum.android.diploma.ui.workplace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
        _binding = FragmentWorkplaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCountryInfo()
        viewModel.getRegionInfo()

        viewModel.countryState.observe(viewLifecycleOwner) { country ->
            if (country != null) {
                binding.countryName.text = country.countryName
                binding.countryName.setTextColor(ContextCompat.getColor(requireContext(), R.color.black_white))
                binding.countryButton.setImageResource(R.drawable.close_icon)
                binding.countryButton.isClickable = true
                binding.coutryHint.visibility = View.VISIBLE
                binding.button.visibility = View.VISIBLE
            } else {
                binding.countryName.text = "Страна"
                binding.countryName.setTextColor(ContextCompat.getColor(requireContext(), R.color.YP_Text_Gray))
                binding.countryButton.setImageResource(R.drawable.arrow_forward)
                binding.countryButton.isClickable = false
                binding.coutryHint.visibility = View.GONE
                binding.button.visibility = View.GONE
            }

            viewModel.regionState.observe(viewLifecycleOwner) { region ->
                if (region != null && region.regionParentId == country?.countryId) {
                    binding.regionName.text = region.regionName
                    binding.regionName.setTextColor(ContextCompat.getColor(requireContext(), R.color.black_white))
                    binding.regionButton.setImageResource(R.drawable.close_icon)
                    binding.regionButton.isClickable = true
                    binding.regionHint.visibility = View.VISIBLE
                } else {
                    binding.regionName.text = "Регион"
                    binding.regionName.setTextColor(ContextCompat.getColor(requireContext(), R.color.YP_Text_Gray))
                    binding.regionButton.setImageResource(R.drawable.arrow_forward)
                    binding.regionButton.isClickable = false
                    binding.regionHint.visibility = View.GONE
                }
            }
        }

        binding.countryButton.setOnClickListener {
            binding.countryName.text = "Страна"
            binding.countryName.setTextColor(ContextCompat.getColor(requireContext(), R.color.YP_Text_Gray))
            binding.countryButton.setImageResource(R.drawable.arrow_forward)
            binding.countryButton.isClickable = false
            binding.coutryHint.visibility = View.GONE
            viewModel.setCountryInfo(null)
        }

        binding.regionButton.setOnClickListener {
            binding.regionName.text = "Регион"
            binding.regionName.setTextColor(ContextCompat.getColor(requireContext(), R.color.YP_Text_Gray))
            binding.regionButton.setImageResource(R.drawable.arrow_forward)
            binding.regionButton.isClickable = false
            binding.regionHint.visibility = View.GONE
            viewModel.setRegionInfo(null)
        }

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

        binding.button.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
