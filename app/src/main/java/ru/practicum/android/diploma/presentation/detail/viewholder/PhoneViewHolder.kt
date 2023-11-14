package ru.practicum.android.diploma.presentation.detail.viewholder

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.PhoneTvBinding
import ru.practicum.android.diploma.domain.models.Phone

class PhoneViewHolder(private val binding: PhoneTvBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Phone) {
        binding.phoneNumber.text = String.format("+%s (%s) %s",item.country,item.city,item.number)
    }
}





