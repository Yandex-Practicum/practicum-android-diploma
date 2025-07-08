package ru.practicum.android.diploma.ui.searchfilters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.databinding.SearchFiltersFragmentBinding

class SearchFiltersFragment : Fragment() {

    private var binding: SearchFiltersFragmentBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = SearchFiltersFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

}
