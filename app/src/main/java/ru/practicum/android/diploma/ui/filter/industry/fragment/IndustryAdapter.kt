package ru.practicum.android.diploma.ui.filter.industry.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.practicum.android.diploma.databinding.ItemIndustryBinding
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.util.diffutils.IndustryDiffCallback

class IndustryAdapter(
    private val onIndustrySelected: (Industry) -> Unit
) : ListAdapter<Industry, IndustryViewHolder>(IndustryDiffCallback()) {

    private var selectedIndustryId: String? = null
    var industriesFull: List<Industry> = emptyList() // Полный список для поиска

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryViewHolder {
        val binding = ItemIndustryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IndustryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        val industry = getItem(position)
        val isSelected = industry.id == selectedIndustryId
        holder.bind(industry, isSelected) { selectedIndustry ->
            selectIndustry(selectedIndustry)
        }
    }

    fun selectIndustry(industry: Industry) {
        selectedIndustryId = industry.id
        submitList(currentList.toList())
    }

    fun filter(query: String) {
        val filteredList = if (query.isEmpty()) {
            industriesFull
        } else {
            industriesFull.filter { it.name?.contains(query, ignoreCase = true) == true }
        }
        submitList(filteredList)
    }

}
