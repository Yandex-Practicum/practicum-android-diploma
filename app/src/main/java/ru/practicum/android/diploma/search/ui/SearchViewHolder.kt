package ru.practicum.android.diploma.search.ui

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ItemDescriptionBinding
import ru.practicum.android.diploma.search.domain.Vacancy

class SearchViewHolder(
    private val binding: ItemDescriptionBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(vacancy: Vacancy) {
        binding.title.text = vacancy.title
        binding.company.text = vacancy.company
        binding.value.text = vacancy.value
        Glide.with(itemView.context)
            .load(vacancy.iconUri)
            .transition(DrawableTransitionOptions.withCrossFade())
            .placeholder(R.drawable.ic_placeholder_company)
            .centerCrop()
            .into(binding.image)
    }
}