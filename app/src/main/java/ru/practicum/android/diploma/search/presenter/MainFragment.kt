package ru.practicum.android.diploma.search.presenter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentMainBinding
import ru.practicum.android.diploma.search.domain.model.VacancyPreview

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy {
        VacanciesAdapter(requireContext(), mutableListOf(), ::onVacancyClick)
    }
    private val recyclerView: RecyclerView get() = binding.vacanciesRvId

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()


        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateSearchIcon(s)
            }

            override fun afterTextChanged(s: Editable?) {

            }

        }

        binding.editTextId.addTextChangedListener(textWatcher)
        clearEditText()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onVacancyClick(preview: VacancyPreview) {

    }

    private fun initRv() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun updateSearchIcon(text: CharSequence?) {
        if (text.isNullOrBlank()) {
            binding.searchIcon.setImageResource(R.drawable.search_24px)
            binding.searchIcon.tag = R.drawable.search_24px
        } else {
            binding.searchIcon.setImageResource(R.drawable.cross_light)
            binding.searchIcon.tag = R.drawable.cross_light
        }
    }


    private fun clearEditText() {
        binding.searchIcon.setOnClickListener {
            when {
                binding.searchIcon.tag == R.drawable.cross_light -> {
                    binding.editTextId.text.clear()
                }
            }
        }
    }

}
