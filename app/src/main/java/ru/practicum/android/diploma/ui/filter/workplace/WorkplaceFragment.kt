package ru.practicum.android.diploma.ui.filter.workplace

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
import ru.practicum.android.diploma.domain.debugLog

class WorkplaceFragment : Fragment() {

    private var _binding: FragmentWorkplaceBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<WorkplaceViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWorkplaceBinding.inflate(layoutInflater)
        return binding.root
    }

    @Suppress("detekt:LongMethod")
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.countryState.observe(viewLifecycleOwner) { country ->
            if (country != null) {
                debugLog(TAG) { "countryState: country = $country" }
                binding.workplaceTextCount.text = country.countryName
                binding.workplaceIcCount.setImageResource(R.drawable.ic_close_24px)
                binding.workplaceIcCount.isClickable = true
                binding.workplaceVisibleCount.visibility = View.VISIBLE
                binding.workplaceButtonApply.visibility = View.VISIBLE
            } else {
                binding.workplaceTextCount.hint = "Страна"
                binding.workplaceIcCount.setImageResource(R.drawable.arrow_forward_24px)
                binding.workplaceIcCount.isClickable = false
                binding.workplaceVisibleCount.visibility = View.GONE
                binding.workplaceButtonApply.visibility = View.GONE
            }

            viewModel.regionState.observe(viewLifecycleOwner) { region ->
                if (region != null && region.regionParentId == country?.countryId) {
                    debugLog(TAG) { "regionState: region = $region" }
                    binding.workplaceTextRegion.text = region.regionName
                    binding.workplaceIcRegion.setImageResource(R.drawable.ic_close_24px)
                    binding.workplaceIcRegion.isClickable = true
                    binding.workplaceVisibleRegion.visibility = View.VISIBLE
                } else {
                    binding.workplaceTextRegion.text = "Регион"
                    binding.workplaceTextRegion.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
                    binding.workplaceIcRegion.setImageResource(R.drawable.arrow_forward_24px)
                    binding.workplaceIcRegion.isClickable = false
                    binding.workplaceVisibleRegion.visibility = View.GONE
                }
            }
        }

        binding.workplaceIcCount.setOnClickListener {
            binding.workplaceTextCount.text = "Страна"
            binding.workplaceTextCount.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
            binding.workplaceIcCount.setImageResource(R.drawable.arrow_forward_24px)
            binding.workplaceIcCount.isClickable = false
            binding.workplaceVisibleCount.visibility = View.GONE
            viewModel.setCountryInfo(null)
            viewModel.setRegionInfo(null)
        }

        binding.workplaceIcRegion.setOnClickListener {
            binding.workplaceTextRegion.text = "Регион"
            binding.workplaceTextRegion.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
            binding.workplaceIcRegion.setImageResource(R.drawable.arrow_forward_24px)
            binding.workplaceIcRegion.isClickable = false
            binding.workplaceVisibleRegion.visibility = View.GONE
            viewModel.setRegionInfo(null)
        }

        binding.workplaceCount.setOnClickListener {
            findNavController().navigate(R.id.action_workplaceFragment_to_countFragment)
        }

        binding.workplaceRegion.setOnClickListener {
            findNavController().navigate(R.id.action_workplaceFragment_to_regionFragment)
        }

        binding.workplaceButtonApply.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.workplaceToolbar.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.workplaceToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "WorkplaceFragment"
    }
}
