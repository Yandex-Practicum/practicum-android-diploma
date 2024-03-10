package ru.practicum.android.diploma.ui.workplace

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
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

        val sharedPrefs = context?.getSharedPreferences(COUNTRY_PREFERENCES, Context.MODE_PRIVATE)

        if (sharedPrefs?.getString(COUNTRY_TEXT, "")?.isNotEmpty() == true) {
            binding.countryName.text = sharedPrefs.getString(COUNTRY_TEXT, "")
            binding.countryName.setTextColor(ContextCompat.getColor(requireContext(), R.color.YP_Black))
            binding.countryButton.setImageResource(R.drawable.close_icon)
            binding.countryButton.isClickable = true
            binding.coutryHint.visibility = View.VISIBLE
        } else {
            binding.countryName.text = "Страна"
            binding.countryName.setTextColor(ContextCompat.getColor(requireContext(), R.color.YP_Text_Gray))
            binding.countryButton.setImageResource(R.drawable.arrow_forward)
            binding.countryButton.isClickable = false
            binding.coutryHint.visibility = View.GONE
        }

        if (sharedPrefs?.getString(REGION_TEXT, "")?.isNotEmpty() == true) {
            binding.regionName.text = sharedPrefs.getString(REGION_TEXT, "")
            binding.regionName.setTextColor(ContextCompat.getColor(requireContext(), R.color.YP_Black))
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

        binding.countryButton.setOnClickListener {
            binding.countryName.text = "Страна"
            binding.countryName.setTextColor(ContextCompat.getColor(requireContext(), R.color.YP_Text_Gray))
            binding.countryButton.setImageResource(R.drawable.arrow_forward)
            binding.countryButton.isClickable = false
            sharedPrefs?.edit()?.putString(COUNTRY_TEXT, "")?.apply()
            sharedPrefs?.edit()?.putString(COUNTRY_ID, "")?.apply()
            binding.coutryHint.visibility = View.GONE
        }

        binding.regionButton.setOnClickListener {
            binding.regionName.text = "Регион"
            binding.regionName.setTextColor(ContextCompat.getColor(requireContext(), R.color.YP_Text_Gray))
            binding.regionButton.setImageResource(R.drawable.arrow_forward)
            binding.regionButton.isClickable = false
            sharedPrefs?.edit()?.putString(REGION_TEXT, "")?.apply()
            sharedPrefs?.edit()?.putString(REGION_ID, "")?.apply()
            binding.regionHint.visibility = View.GONE
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

        binding.button.setOnClickListener {
            val country = binding.countryName.text
            val region = binding.regionName.text
            val bundle = Bundle()
            if (region != "Регион") {
                bundle.putString("keyPlace", "$country, $region")
            } else {
                bundle.putString("keyPlace", "$country")
            }
            setFragmentResult("requestKeyPlace", bundle)
            findNavController().navigateUp()
        }

    }

    companion object {
        const val COUNTRY_PREFERENCES = "country_preferences"
        const val COUNTRY_TEXT = "country_text"
        const val COUNTRY_ID = "country_id"
        const val REGION_TEXT = "region_text"
        const val REGION_ID = "region_id"
    }
}
