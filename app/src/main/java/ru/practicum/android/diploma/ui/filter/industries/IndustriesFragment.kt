package ru.practicum.android.diploma.ui.filter.industries

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentIndustriesBinding
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R

class IndustriesFragment : Fragment() {

    private val viewModel by viewModel<IndustriesFragmentViewModel>()
    private var _binding: FragmentIndustriesBinding? = null
    private val binding get() = _binding!!

    private val originalData = ArrayList<ChildIndustryWithSelection>()
    private val filteredData = ArrayList<ChildIndustryWithSelection>()
    private var oldSelectedItem: Int = -1

    private var recyclerView: RecyclerView? = null
    private var textInputLayout: TextInputLayout? = null
    private var textInput: TextInputEditText? = null
    private var applyButton: TextView? = null
    private var toolbar: MaterialToolbar? = null

    private fun showIndustriesWithoutSelectButton() {
        binding.industriesRecyclerView.visibility = View.VISIBLE
        binding.industriesFilterApply.visibility = View.GONE
        binding.getIndustriesErrorFrame.visibility = View.GONE
    }

    private fun showIndustriesWithSelectButton() {
        binding.industriesRecyclerView.visibility = View.VISIBLE
        binding.industriesFilterApply.visibility = View.VISIBLE
        binding.getIndustriesErrorFrame.visibility = View.GONE
    }

    private fun showGetIndustriesError() {
        binding.industriesRecyclerView.visibility = View.GONE
        binding.industriesFilterApply.visibility = View.GONE
        binding.getIndustriesErrorFrame.visibility = View.VISIBLE
    }

    private fun resetFilter() {
        showIndustriesWithoutSelectButton()

        filteredData.clear()
        filteredData.addAll(originalData)

        recyclerView?.adapter?.notifyDataSetChanged()
        recyclerView?.scrollToPosition(0)
    }

    private fun filterTextInputDebounced() {
        viewLifecycleOwner.lifecycleScope.launch {
            delay(FILTER_DEBOUNCE_DELAY_MILLIS)
            filterTextInput()
        }
    }

    private fun clearSelection() {
        if (oldSelectedItem >= 0) {
            filteredData[oldSelectedItem].selected = false
        }
        oldSelectedItem = -1
        showIndustriesWithoutSelectButton()
    }

    private fun filterTextInput() {
        clearSelection()

        if (textInput?.text.toString().length >= FILTER_TEXT_MIN_LENGTH_INT) {
            filteredData.clear()
            filteredData.addAll(originalData.filter { it.name.contains(textInput?.text.toString(), true) })
            if (filteredData.size == 1) {
                filteredData[0].selected = true
                oldSelectedItem = 0
                showIndustriesWithSelectButton()
            }
            recyclerView?.adapter?.notifyDataSetChanged()
        } else {
            resetFilter()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIndustriesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = binding.industriesToolbar
        toolbar!!.setOnClickListener { findNavController().popBackStack() }

        applyButton = binding.industriesFilterApply
        applyButton!!.setOnClickListener {
            viewModel.saveFilteredIndustry(filteredData[oldSelectedItem])
            findNavController().popBackStack()
        }

        textInputLayout = binding.tiSearch

        textInput = binding.search
        textInput!!.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                filterTextInputDebounced()
            }
            false
        }
        textInput!!.addTextChangedListener(
            onTextChanged = { charSequence, _, _, _ ->
                if (charSequence.isNullOrEmpty()) {
                    textInputLayout!!.endIconMode = TextInputLayout.END_ICON_NONE
                    textInputLayout!!.endIconDrawable =
                        AppCompatResources.getDrawable(requireContext(), R.drawable.ic_search_24px)

                    clearSelection()
                    resetFilter()

                } else {
                    textInputLayout!!.endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
                    textInputLayout!!.endIconDrawable =
                        AppCompatResources.getDrawable(requireContext(), R.drawable.ic_close_24px)

                    clearSelection()
                    filterTextInputDebounced()
                }
            }
        )

        recyclerView = binding.industriesRecyclerView
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = IndustriesFragmentRecyclerViewAdapter(filteredData).apply {
            industryNumberClicked = { newSelectedItem ->
                if (oldSelectedItem >= 0) {
                    filteredData[oldSelectedItem].selected = false
                }
                filteredData[newSelectedItem].selected = true

                recyclerView?.adapter?.notifyItemChanged(oldSelectedItem)
                recyclerView?.adapter?.notifyItemChanged(newSelectedItem)
                oldSelectedItem = newSelectedItem

                (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
                    ?.hideSoftInputFromWindow(
                        view.windowToken,
                        0
                    )
                showIndustriesWithSelectButton()
            }
        }

        viewModel.getState().observe(viewLifecycleOwner) {
            when (it) {
                is IndustriesFragmentUpdate.GetIndustriesError -> {
                    showGetIndustriesError()
                }

                is IndustriesFragmentUpdate.IndustriesList -> {
                    originalData.clear()
                    originalData.addAll(it.industries)

                    filteredData.clear()
                    filteredData.addAll(originalData)

                    recyclerView?.adapter?.notifyDataSetChanged()
                    showIndustriesWithoutSelectButton()

                    viewModel.applyFilters()
                }

                is IndustriesFragmentUpdate.FilteredIndustry -> {
                    textInput!!.setText(it.industry.name)
                    textInput!!.requestFocus()
                    textInput!!.setSelection(it.industry.name.length)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private companion object {
        const val FILTER_DEBOUNCE_DELAY_MILLIS = 400L
        const val FILTER_TEXT_MIN_LENGTH_INT = 1
    }
}
