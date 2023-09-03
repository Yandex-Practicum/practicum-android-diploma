package ru.practicum.android.diploma.search.ui.fragment

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ItemDescriptionBinding
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.setImage

class SearchViewHolder(
    private val binding: ItemDescriptionBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(vacancy: Vacancy) {
    
        val titleAndArea: String =
            if (vacancy.area.isNotEmpty()) "${vacancy.title}, ${vacancy.area}"
            else vacancy.title
    
        val cornerRadius = itemView.resources.getDimensionPixelSize(R.dimen.size_12dp)
    
        binding.title.text = titleAndArea
        binding.company.text = vacancy.company
        binding.value.text = vacancy.salary
        binding.image.setImage(
            url = vacancy.iconUri,
            placeholder = R.drawable.ic_placeholder_company,
            cornerRadius = cornerRadius
        )
    }
}