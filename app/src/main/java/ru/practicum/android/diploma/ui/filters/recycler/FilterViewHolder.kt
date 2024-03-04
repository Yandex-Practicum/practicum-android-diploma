package ru.practicum.android.diploma.ui.filters.recycler

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.CountryBinding
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.ConvertSalary

class FilterViewHolder(private val binding: CountryBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Country) {
        binding.country.text = item.name
    }
}
