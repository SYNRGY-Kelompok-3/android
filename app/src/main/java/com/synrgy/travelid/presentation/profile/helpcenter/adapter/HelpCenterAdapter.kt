package com.synrgy.travelid.presentation.profile.helpcenter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gouravkhunger.accolib.widget.Accordion
import com.synrgy.travelid.R
import com.synrgy.travelid.domain.model.main.HelpCenterItem

class HelpCenterAdapter(private val items: List<HelpCenterItem>) :
    RecyclerView.Adapter<HelpCenterAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_help_center, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val accordion: Accordion = itemView.findViewById(R.id.accordion)

        fun bind(helpCenterItem: HelpCenterItem) {
            accordion.title = helpCenterItem.title
            accordion.text = helpCenterItem.content.toString()
        }
    }
}
