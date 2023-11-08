package ru.practicum.android.diploma.presentation.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.data.dto.detail.Phone
import ru.practicum.android.diploma.databinding.PhoneTvBinding
import ru.practicum.android.diploma.presentation.detail.viewholder.PhoneViewHolder

class PhoneAdapter(
    private val data: List<Phone>,
    private val clickListener: (Phone) -> Unit
) :
    RecyclerView.Adapter<PhoneViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneViewHolder {

        val layoutInspector = LayoutInflater.from(parent.context)
        return PhoneViewHolder(PhoneTvBinding.inflate(layoutInspector, parent, false))
    }

    override fun onBindViewHolder(holder: PhoneViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener {
            clickListener.invoke(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
