package ru.practicum.android.diploma.ui.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.practicum.android.diploma.databinding.FragmentCountryBinding

class CountryFragment : Fragment() {
    private lateinit var binding: FragmentCountryBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCountryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vacancyToolbar.setOnClickListener {
            findNavController().navigateUp()
        }

        val countries = ArrayList<RecyclerItem>()
        countries.add(RecyclerItem("Россия"))
        countries.add(RecyclerItem("Украина"))
        countries.add(RecyclerItem("Казахстан"))
        countries.add(RecyclerItem("Азербайджан"))
        countries.add(RecyclerItem("Беларусь"))
        countries.add(RecyclerItem("Грузия"))
        countries.add(RecyclerItem("Кыргыстан"))
        countries.add(RecyclerItem("Узбекистан"))
        countries.add(RecyclerItem("Другие регионы"))
        val adapter = CountryAdapter(countries)
        adapter.itemClickListener = { _, item ->
            val bundle = Bundle()
            bundle.putString("key", item.name)
            setFragmentResult("requestKey", bundle)
            findNavController().popBackStack()
        }
        binding.countryRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.countryRecycler.adapter = adapter

    }
}
