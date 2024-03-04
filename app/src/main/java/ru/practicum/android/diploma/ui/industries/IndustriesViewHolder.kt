package ru.practicum.android.diploma.ui.industries

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.response.Industries
import ru.practicum.android.diploma.databinding.IndustryItemBinding

class IndustriesViewHolder(private val binding: IndustryItemBinding) : RecyclerView.ViewHolder(binding.root) {


    fun bind(recyclerItem: Industries) {
        binding.textView.text = recyclerItem.name
        val textColor = ContextCompat.getColor(itemView.context, R.color.black)
        binding.textView.setTextColor(textColor)
    }
}
