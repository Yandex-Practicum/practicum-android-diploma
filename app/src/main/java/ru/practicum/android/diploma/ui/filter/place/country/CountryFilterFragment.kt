package ru.practicum.android.diploma.ui.filter.place.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.databinding.FragmentCountryFilterBinding

class CountryFilterFragment : Fragment() {
    private var _binding: FragmentCountryFilterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountryFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /* Пока здесь ничего нет */
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
