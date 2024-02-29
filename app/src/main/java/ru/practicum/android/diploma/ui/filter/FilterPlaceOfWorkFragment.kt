package ru.practicum.android.diploma.ui.filter

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.databinding.FragmentFilterChoosePlaceOfWorkBinding
import ru.practicum.android.diploma.databinding.FragmentFiltersBinding
import ru.practicum.android.diploma.ui.search.gone
import ru.practicum.android.diploma.ui.search.visible

class FilterPlaceOfWorkFragment : Fragment() {

    private var _binding: FragmentFilterChoosePlaceOfWorkBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterChoosePlaceOfWorkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}
