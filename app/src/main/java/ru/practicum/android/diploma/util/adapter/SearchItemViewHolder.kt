package ru.practicum.android.diploma.util.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.SearchItemViewBinding
import ru.practicum.android.diploma.domain.models.Vacancy

class SearchItemViewHolder(
    private val binding: SearchItemViewBinding,
    private val onClick: (Vacancy) -> Unit,
    private val onLongClick: (Vacancy) -> Unit
) : RecyclerView.ViewHolder(binding.root) {


}
