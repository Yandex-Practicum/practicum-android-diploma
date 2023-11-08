package ru.practicum.android.diploma.presentation.detail.viewholder

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.data.dto.detail.Phone
import ru.practicum.android.diploma.databinding.PhoneTvBinding

class PhoneViewHolder(private val binding: PhoneTvBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Phone) {
        binding.phoneNumber.text = "+${item.country} (${item.city}) ${item.number}"
    }
}





