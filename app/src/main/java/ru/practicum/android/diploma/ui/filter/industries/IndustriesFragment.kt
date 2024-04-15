package ru.practicum.android.diploma.ui.filter.industries

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentIndustriesBinding

class IndustriesFragment : Fragment() {

    private val viewModel by viewModel<IndustriesViewModel>()
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

    private var filterJob: Job? = null

    private fun resetFilter() {
        filterJob?.cancel()
        showIndustriesWithoutSelectButton()

        filteredData.clear()
        filteredData.addAll(originalData)

        recyclerView?.adapter?.notifyDataSetChanged()
        recyclerView?.scrollToPosition(0)

    }

    private fun filterTextInputDebounced() {
        filterJob?.cancel()
        filterJob = viewLifecycleOwner.lifecycleScope.launch {
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
            if (filteredData.size == 0) {
                showEmpty(getString(R.string.no_such_industry))
            }
            recyclerView?.adapter?.notifyDataSetChanged()
        } else {
            resetFilter()
        }
    }

    private fun setTextInputIconSearch() {
        textInputLayout!!.endIconMode = TextInputLayout.END_ICON_NONE
        textInputLayout!!.endIconDrawable =
            AppCompatResources.getDrawable(requireContext(), R.drawable.ic_search_24px)
    }

    private fun setTextInputIconRemove() {
        textInputLayout!!.endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
        textInputLayout!!.endIconDrawable =
            AppCompatResources.getDrawable(requireContext(), R.drawable.ic_close_24px)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIndustriesBinding.inflate(layoutInflater)
        return binding.root
    }

    @Suppress("detekt:LongMethod")
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
                    setTextInputIconSearch()
                    clearSelection()
                    resetFilter()
                } else {
                    setTextInputIconRemove()
                    clearSelection()
                    filterTextInputDebounced()
                }
            }
        )

        textInput!!.requestFocus()

        // не устанавливаются иконки ??? fix=принудительно запускать addTextChangedListener
        textInput!!.setText(" ")
        textInput!!.setText("")
        // не устанавливаются иконки ??? fix=принудительно запускать addTextChangedListener

        recyclerView = binding.recyclerView
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = IndustriesRecyclerViewAdapter(filteredData).apply {
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

        viewModel.getState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is IndustriesState.IndustriesList -> {
                    originalData.clear()
                    originalData.addAll(state.industries)

                    filteredData.clear()
                    filteredData.addAll(originalData)

                    recyclerView?.adapter?.notifyDataSetChanged()

                    showIndustriesWithoutSelectButton()

                    viewModel.applyFilters()
                }

                is IndustriesState.FilteredIndustry -> {
                    textInput!!.requestFocus()
                    textInput!!.setText(state.industry.name)
                    textInput!!.setSelection(state.industry.name.length)
                    (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
                        ?.showSoftInput(textInput, 0)
                }

                is IndustriesState.Empty -> showEmpty(getString(state.message))
                is IndustriesState.Error -> showError(getString(state.errorMessage))
                is IndustriesState.Loading -> showLoading()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showIndustriesWithoutSelectButton() {
        binding.recyclerView.visibility = View.VISIBLE
        binding.industriesFilterApply.visibility = View.GONE
        binding.errorContainer.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
    }

    private fun showIndustriesWithSelectButton() {
        binding.recyclerView.visibility = View.VISIBLE
        binding.industriesFilterApply.visibility = View.VISIBLE
        binding.errorContainer.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
    }

    private fun showLoading() {
        binding.recyclerView.visibility = View.GONE
        binding.errorContainer.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showError(errorMessage: String) {
        binding.recyclerView.visibility = View.GONE
        binding.industriesFilterApply.visibility = View.GONE
        binding.errorContainer.visibility = View.VISIBLE
        binding.errorImageView.setImageResource(R.drawable.state_image_error_get_list)
        binding.errorTextView.text = errorMessage
        binding.progressBar.visibility = View.GONE
    }

    private fun showEmpty(message: String) {
        binding.recyclerView.visibility = View.GONE
        binding.industriesFilterApply.visibility = View.GONE
        binding.errorContainer.visibility = View.VISIBLE
        binding.errorImageView.setImageResource(R.drawable.state_image_nothing_found)
        binding.errorTextView.text = message
        binding.progressBar.visibility = View.GONE
    }

    private companion object {
        const val FILTER_DEBOUNCE_DELAY_MILLIS = 400L
        const val FILTER_TEXT_MIN_LENGTH_INT = 1
    }
}
