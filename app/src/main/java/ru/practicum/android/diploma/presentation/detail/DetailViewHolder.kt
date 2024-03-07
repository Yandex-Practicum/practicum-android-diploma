package ru.practicum.android.diploma.presentation.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.detail.VacancyPhoneAndComment
import ru.practicum.android.diploma.util.checkIfNotNull

class DetailViewHolder(
    parent: ViewGroup,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.group_phone_and_comment_item, parent, false)
) {

    private val contactPhone: TextView = itemView.findViewById(R.id.tvcontact_phone)
    private val contactPhoneLabel: TextView = itemView.findViewById(R.id.tvcontact_phone_label)
    private val contactComment: TextView = itemView.findViewById(R.id.tvcontact_comment)
    private val contactCommentLabel: TextView = itemView.findViewById(R.id.tvcontact_comment_label)

    fun bind(phoneAndComment: VacancyPhoneAndComment) {
        contactPhone.text = phoneAndComment.contactPhone
        contactComment.text = phoneAndComment.contactComment

        if (checkIfNotNull(phoneAndComment.contactPhone, contactPhone)) {
            contactPhoneLabel.visibility = View.VISIBLE
        } else contactPhoneLabel.visibility = View.GONE

        if (checkIfNotNull(phoneAndComment.contactComment, contactComment)) {
            contactCommentLabel.visibility = View.VISIBLE
        } else contactCommentLabel.visibility = View.GONE

        contactPhone.setOnClickListener {
            openAppForCalling(phoneAndComment.contactPhone, it.context)
        }
    }

    private fun openAppForCalling(phone: String?, context: Context) {
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.setData(Uri.parse("tel:$phone"))
        val chooserIntent = Intent.createChooser(callIntent, null)
        chooserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(chooserIntent)
    }

}

