package ru.practicum.android.diploma.presentation.detail

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.domain.models.detail.VacancyPhoneAndComment

class DetailAdapter(
    val context: Context
) : RecyclerView.Adapter<DetailViewHolder>() {

    var phoneAndCommentList = ArrayList<VacancyPhoneAndComment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder = DetailViewHolder(parent)

    override fun getItemCount(): Int = phoneAndCommentList.size

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val phoneAndComment = phoneAndCommentList[position]
        holder.bind(phoneAndComment)
    }
}
