package ru.practicum.android.diploma.ui.country

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentCountryBinding

class CountryFragment : Fragment() {

    private val viewModel by viewModel<CountryViewModel>()
    private var _binding: FragmentCountryBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCountryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPrefs = context?.getSharedPreferences(COUNTRY_PREFERENCES, MODE_PRIVATE)

        binding.vacancyToolbar.setOnClickListener {
            findNavController().navigateUp()
        }

        val adapter = CountryAdapter()
        adapter.itemClickListener = { _, item ->
            val bundle = Bundle()
            bundle.putString("key", item.name)
            setFragmentResult("requestKey", bundle)
            sharedPrefs?.edit()?.putString(COUNTRY_TEXT, item.name)?.apply()
            sharedPrefs?.edit()?.putString(COUNTRY_ID, item.id)?.apply()
            findNavController().popBackStack()
        }

        binding.countryRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.countryRecycler.adapter = adapter

        viewModel.observeState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is CountryState.Content -> {
                    adapter.countryList.addAll(state.region)
                    adapter.notifyDataSetChanged()
                }

                is CountryState.Error -> ""
                is CountryState.Loading -> ""
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val COUNTRY_PREFERENCES = "country_preferences"
        const val COUNTRY_TEXT = "country_text"
        const val COUNTRY_ID = "country_id"
    }
}
