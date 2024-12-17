package ru.practicum.android.diploma.ui.search

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.ui.search.view_model.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.domain.models.Vacancy

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputEditText = binding.etSearchVacancy
        val clearButton = binding.ibClearQuery
        val foundedVacanciesRecyclerView = binding.rvFoundedVacancies

        inputEditText.addTextChangedListener(onTextChanged = { s: CharSequence?, _, _, _ ->
            clearButton.isVisible = !s.isNullOrEmpty()
            binding.ibClearSearch.isVisible = s.isNullOrEmpty()
        })

        clearButton.setOnClickListener {
            inputEditText.setText(CLEAR_TEXT)
            inputEditText.requestFocus()
            val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(inputEditText, InputMethodManager.SHOW_IMPLICIT)
        }

        val onItemClickListener: (Vacancy) -> Unit = {
            // Логика для выполнения по обычному нажатию на элемент
        }
        val onItemLongClickListener: (Vacancy) -> Unit = {
            // Логика для выполнения по долгому нажатию на элемент
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val CLEAR_TEXT = ""
    }

}
