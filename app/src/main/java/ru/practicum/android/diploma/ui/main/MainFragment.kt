package ru.practicum.android.diploma.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.databinding.FragmentMainBinding
import ru.practicum.android.diploma.R
import androidx.navigation.fragment.findNavController

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUiToolbar()
        // системная кн назад
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            backPressedCallback
        )
    }

    private fun initUiToolbar() {
        // настройка кастомного топбара
        val toolbar = binding.toolbar
        toolbar.setupToolbarForSearchScreen()
        toolbar.setToolbarTitle(getString(R.string.vacancy_search))
        toolbar.setOnToolbarFilterClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_filterFragment)
        }
        /*
        * !!! после того, как настроены все фильтры
        * применить toolbar.setFilterState(true) для изменения иконки кнопки
        */

    }

    //  callback для системной кн назад - выход из приложения
    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
