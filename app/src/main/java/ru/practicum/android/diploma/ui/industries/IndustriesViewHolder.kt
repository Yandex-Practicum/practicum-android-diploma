package ru.practicum.android.diploma.ui.industries

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.IndustryItemBinding
import ru.practicum.android.diploma.domain.industries.IndustriesAllDeal

class IndustriesViewHolder(private val binding: IndustryItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(industries: IndustriesAllDeal) {
        binding.textView.text = industries.name
        val textColor = ContextCompat.getColor(itemView.context, R.color.black)
        binding.textView.setTextColor(textColor)
    }
}
