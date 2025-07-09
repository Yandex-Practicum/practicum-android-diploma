package ru.practicum.android.diploma.ui.vacancysearch

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
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancySearchFragmentBinding

class VacancySearchFragment : Fragment() {

    private var _binding: VacancySearchFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = VacancySearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.header.toolbarTitle.text = getString(R.string.search_vacancy)
        binding.header.iconFilter.visibility = View.VISIBLE

        binding.details.setOnClickListener {
            findNavController().navigate(R.id.action_vacancySearchFragment_to_vacancyDetailsFragment)
        }
        binding.header.iconFilter.setOnClickListener {
            findNavController().navigate(R.id.action_vacancySearchFragment_to_searchFiltersFragment)
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isNotEmpty() == true) {
                    binding.icon.setImageResource(R.drawable.close_24px)
                    binding.icon.isClickable = true
                    binding.icon.setOnClickListener {
                        binding.inputEditText.setText("")
                        val inputMethodManager =
                            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
                    }
                } else {
                    binding.icon.setImageResource(R.drawable.search_24px)
                    binding.icon.isClickable = false
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        binding.inputEditText.addTextChangedListener(simpleTextWatcher)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
