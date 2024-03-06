package ru.practicum.android.diploma.ui.country

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
import ru.practicum.android.diploma.domain.country.Country
import ru.practicum.android.diploma.ui.search.viewmodel.SearchViewModel

class CountryFragment : Fragment() {

    private val viewModel by viewModel<SearchViewModel>()
    private var _binding: FragmentCountryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCountryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vacancyToolbar.setOnClickListener {
            findNavController().navigateUp()
        }

//        val countries = ArrayList<RecyclerItem>()
//        countries.add(RecyclerItem("Россия"))
//        countries.add(RecyclerItem("Украина"))
//        countries.add(RecyclerItem("Казахстан"))
//        countries.add(RecyclerItem("Азербайджан"))
//        countries.add(RecyclerItem("Беларусь"))
//        countries.add(RecyclerItem("Грузия"))
//        countries.add(RecyclerItem("Кыргыстан"))
//        countries.add(RecyclerItem("Узбекистан"))
//        countries.add(RecyclerItem("Другие регионы"))
        val adapter = CountryAdapter()
        adapter.itemClickListener = { _, item ->
            val bundle = Bundle()
            bundle.putString("key", item.name)
            setFragmentResult("requestKey", bundle)
            findNavController().popBackStack()
        }

        binding.countryRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.countryRecycler.adapter = adapter

        adapter.countryList.addAll(
            arrayListOf(
                Country("7584", "113", "Россия"),
                Country("7332", "112", "Украина"),
                Country("7123", "111", "Казахстан"),
                Country("2134", "110", "Азербайджан"),
                Country("1231", "114", "Беларусь"),
                Country("7612", "115", "Грузия"),
                Country("7543", "116", "Кыргыстан"),
                Country("7213", "123", "Узбекистан"),
                Country("2211", "119", "Другие регионы")
            ),
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
