package ru.practicum.android.diploma.ui.searchFilters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.databinding.FavouriteVacanciesFragmentBinding
import ru.practicum.android.diploma.databinding.SearchFiltersFragmentBinding

class SearchFiltersFragment : Fragment() {

    private lateinit var binding: SearchFiltersFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = SearchFiltersFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

}
