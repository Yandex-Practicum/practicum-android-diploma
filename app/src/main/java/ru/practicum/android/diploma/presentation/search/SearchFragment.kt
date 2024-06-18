package ru.practicum.android.diploma.presentation.search

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.models.Vacancy

class SearchFragment : Fragment(), VacancyAdapter.ItemVacancyClickInterface {

    private val vm by viewModel<SearchViewModel>()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var vacancyAdapter: VacancyAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun Fragment.hideKeyboard() {
        val inputMethodManager =
            requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = activity?.currentFocus ?: View(requireContext())
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.iconClear.setOnClickListener {
            binding.searchInput.setText("")
        }

        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Метод пустой, но обязателен к реализации
            }

            override fun afterTextChanged(p0: Editable?) {
                // Метод пустой, но обязателен к реализации
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.iconSearch.isVisible = s.isNullOrEmpty()
                binding.iconClear.isVisible = !s.isNullOrEmpty()
            }
        })

        vacancyAdapter = VacancyAdapter()
        vacancyAdapter?.setInItemVacancyClickListener(this)

        // binding.searchRecyclerView = LinearLayoutManager(context)

        binding.searchRecyclerView.adapter = vacancyAdapter

        // searchViewModel.vacancyList.observe(viewLifecycleOwner) { vacancyAdapter?.setVacancyList(it) }

        binding.filterButton.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_filtrationFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemVacancyClick(vacancy: Vacancy) {
        Log.d("search", "vacancy = ${vacancy.vacancyId}, ${vacancy.name}")
    }

    /*    fun observeViewStateValues() {
           searchViewModel.searchViewModelState.observe(viewLifecycleOwner) {
               when (it) {
                   StateNetworkError -> {
                       setStateNetworkError()
                   }

                   StateServerError -> {
                       setStateServerError()
                   }

                   StateDefault -> {
                       setStateDefault()
                   }

                   StateIsLoading -> {
                       setStateIsLoading()
                   }

                   StateEmptyResult -> {
                       setStateEmptyResult()
                   }

                   StateIsData -> {
                       setSateIsData(it)
                   }

                   else -> {
                   // это пустой метод
                   }
               }
           }
       }
   */
    private fun setStateNetworkError() {
        binding.foundResultsMessage.visibility = View.GONE
        binding.searchRecyclerView.visibility = View.GONE
        binding.searchProgressBar.visibility = View.GONE
        binding.searchPlaceholderLayout.visibility = View.VISIBLE
        Glide.with(this)
            .load(R.drawable.placeholder_skull)
            .centerCrop()
            .into(binding.searchPlaceholderImage)
        binding.searchPlaceholderMessage.text = this.getString(R.string.no_internet_connection)
    }

    private fun setStateServerError() {
        binding.foundResultsMessage.visibility = View.GONE
        binding.searchRecyclerView.visibility = View.GONE
        binding.searchProgressBar.visibility = View.GONE
        binding.searchPlaceholderLayout.visibility = View.VISIBLE
        Glide.with(this)
            .load(R.drawable.placeholder_server_error_towel)
            .centerCrop()
            .into(binding.searchPlaceholderImage)
        binding.searchPlaceholderMessage.text = this.getString(R.string.server_error)
    }

    private fun setStateDefault() {
        binding.foundResultsMessage.visibility = View.GONE
        binding.searchRecyclerView.visibility = View.GONE
        binding.searchProgressBar.visibility = View.GONE
        binding.searchPlaceholderLayout.visibility = View.VISIBLE
        Glide.with(this)
            .load(R.drawable.placeholder_man_search)
            .centerCrop()
            .into(binding.searchPlaceholderImage)
        binding.searchPlaceholderMessage.text = ""
    }

    private fun setStateIsLoading() {
        binding.foundResultsMessage.visibility = View.GONE
        binding.searchRecyclerView.visibility = View.GONE
        binding.searchProgressBar.visibility = View.VISIBLE
        binding.searchPlaceholderLayout.visibility = View.GONE
    }

    private fun setStateEmptyResult() {
        binding.foundResultsMessage.visibility = View.VISIBLE
        binding.searchRecyclerView.visibility = View.GONE
        binding.searchProgressBar.visibility = View.GONE
        binding.searchPlaceholderLayout.visibility = View.VISIBLE
        Glide.with(this)
            .load(R.drawable.placeholder_cat)
            .centerCrop()
            .into(binding.searchPlaceholderImage)
        binding.searchPlaceholderMessage.text = this.getString(R.string.count_get_list_of_vacancies)
        binding.foundResultsMessage.text = this.getString(R.string.no_such_vacancies)
    }

    private fun setSateIsData(vacancyList: List<Vacancy>) {
        binding.foundResultsMessage.visibility = View.VISIBLE
        binding.searchRecyclerView.visibility = View.VISIBLE
        binding.searchProgressBar.visibility = View.GONE
        binding.searchPlaceholderLayout.visibility = View.GONE
        Glide.with(this)
            .load(R.drawable.placeholder_cat)
            .centerCrop()
            .into(binding.searchPlaceholderImage)
        binding.searchPlaceholderMessage.text = this.getString(R.string.count_get_list_of_vacancies)
        binding.foundResultsMessage.text = java.lang.String(
            this.getString(R.string.found)
                + whitespace
                + vacancyList.size.toString()
                + whitespace
                + this.getString(R.string.vacancy)
        )
        vacancyAdapter?.setVacancyList(ArrayList(vacancyList))
    }

    companion object {
        const val whitespace = " "
    }
}
