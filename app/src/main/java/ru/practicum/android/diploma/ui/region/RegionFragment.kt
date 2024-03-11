package ru.practicum.android.diploma.ui.region

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.converters.AreaConverter.mapToCountry
import ru.practicum.android.diploma.databinding.FragmentRegionBinding
import ru.practicum.android.diploma.ui.country.CountryAdapter
import ru.practicum.android.diploma.ui.country.CountryFragment
import ru.practicum.android.diploma.ui.workplace.WorkplaceFragment

class RegionFragment : Fragment() {

    private val viewModel by viewModel<RegionViewModel>()
    private var _binding: FragmentRegionBinding? = null
    private val binding get() = _binding!!

    private var regionId: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPrefs = context?.getSharedPreferences(CountryFragment.COUNTRY_PREFERENCES, Context.MODE_PRIVATE)
        regionId = sharedPrefs?.getString(WorkplaceFragment.COUNTRY_ID, "")

        binding.vacancyToolbar.setOnClickListener {
            findNavController().navigateUp()
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (binding.edit.text.isNotEmpty()) {
                    val newDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.close_icon)
                    binding.edit.setCompoundDrawablesWithIntrinsicBounds(null, null, newDrawable, null)
                } else {
                    val newDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_search)
                    binding.edit.setCompoundDrawablesWithIntrinsicBounds(null, null, newDrawable, null)
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.edit.text.isNotEmpty()) {
                    val newDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.close_icon)
                    binding.edit.setCompoundDrawablesWithIntrinsicBounds(null, null, newDrawable, null)
                } else {
                    val newDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_search)
                    binding.edit.setCompoundDrawablesWithIntrinsicBounds(null, null, newDrawable, null)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (binding.edit.text.isNotEmpty()) {
                    val newDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.close_icon)
                    binding.edit.setCompoundDrawablesWithIntrinsicBounds(null, null, newDrawable, null)
                } else {
                    val newDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_search)
                    binding.edit.setCompoundDrawablesWithIntrinsicBounds(null, null, newDrawable, null)
                }
            }
        }
        binding.edit.addTextChangedListener(textWatcher)

        binding.click.setOnClickListener {
            binding.edit.setText("")
        }

        val adapter = CountryAdapter()
        adapter.itemClickListener = { _, item ->
            val bundle = Bundle()
            bundle.putString("keyRegion", item.name)
            setFragmentResult("requestKeyRegion", bundle)
            sharedPrefs?.edit()?.putString(REGION_TEXT, item.name)?.apply()
            sharedPrefs?.edit()?.putString(REGION_ID, item.id)?.apply()
            findNavController().popBackStack()
        }

        binding.regionRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.regionRecycler.adapter = adapter

        Log.d("RegionState", "Прокидываем во фрагменте ID = $regionId")
        viewModel.loadRegion(regionId ?: "")

        viewModel.observeState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is RegionState.Content -> {
                    binding.regionRecycler.visibility = View.VISIBLE
                    binding.regionProgressBar.visibility = View.GONE
                    adapter.countryList.clear()
                    adapter.countryList.addAll(state.regionId.areas.map { it.mapToCountry() }.sortedBy { it.name })
                    adapter.notifyDataSetChanged()
                }

                is RegionState.Error -> ""
                is RegionState.Loading -> showLoading()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showContent(){

    }

    private fun showLoading() {
        binding.regionRecycler.visibility = View.GONE
        binding.regionProgressBar.visibility = View.VISIBLE
    }

    companion object {
        const val REGION_TEXT = "region_text"
        const val REGION_ID = "region_id"
    }
}
