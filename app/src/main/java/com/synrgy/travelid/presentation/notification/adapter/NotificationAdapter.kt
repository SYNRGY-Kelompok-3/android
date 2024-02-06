package com.synrgy.travelid.presentation.notification.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.synrgy.travelid.common.BadgePreferencesHelper
import com.synrgy.travelid.common.formatDate
import com.synrgy.travelid.databinding.ListItemNotificationBinding
import com.synrgy.travelid.domain.model.main.Notification

class NotificationAdapter(private val onClick: OnClickListener) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    var notifications: List<Notification> = emptyList()

    fun submitData(value: List<Notification>?) {
        notifications = value?.sortedByDescending { it.id } ?: emptyList()
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onClickItem(data: Notification)
    }

    inner class ViewHolder(private val binding: ListItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Notification) {
            data.id
            when (data.message) {
                "Booking berhasil! Segera lakukan pembayaran sebelum 2 jam dari waktu booking!" -> {
                    binding.tvTitle.text = "Selesaikan pembayaranmu"
                }
                "Pembayaran berhasil! Tiket Anda sudah dikonfirmasi." -> {
                    binding.tvTitle.text = "Pembelian tiket berhasil"
                }
                "booking tiket anda kadaluarsa karena melebihi batas waktu pembayaran." -> {
                    binding.tvTitle.text = "Pembelian tiket kadaluarsa"
                }
            }
            binding.tvBody.text = data.message
            binding.tvTimestamp.text = formatDate(data.timestamp)
            val isBadgeRead = BadgePreferencesHelper.isBadgeRead(itemView.context, data.id)
            binding.icBadge.visibility = if (isBadgeRead) View.GONE else View.VISIBLE
            binding.root.setOnClickListener {
                onClick.onClickItem(data)
                BadgePreferencesHelper.markBadgeAsRead(itemView.context, data.id)
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationAdapter.ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return ViewHolder(ListItemNotificationBinding.inflate(inflate, parent, false))
    }

    override fun onBindViewHolder(holder: NotificationAdapter.ViewHolder, position: Int) {
        holder.bind(notifications[position])
    }

    override fun getItemCount(): Int {
        return notifications.size
    }
}
