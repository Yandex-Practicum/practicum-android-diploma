package ru.practicum.android.diploma.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.search.adapter.PageVacancyAdapter

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        val text = view.findViewById<EditText>(R.id.editText)
        val vacancyList = view.findViewById<RecyclerView>(R.id.vacancy_list)
        vacancyList.adapter = PageVacancyAdapter(context!!)
        vacancyList.layoutManager = LinearLayoutManager(context)

        lifecycleScope.launch {
            viewModel.stateVacancyData.collectLatest {
                (vacancyList.adapter as? PageVacancyAdapter)?.submitData(it)
            }
        }
        text.addTextChangedListener {
            viewModel.search(it?.toString() ?: "")
        }
    }
}
