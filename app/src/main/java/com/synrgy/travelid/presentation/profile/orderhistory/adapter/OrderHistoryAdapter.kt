package com.synrgy.travelid.presentation.profile.orderhistory.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.synrgy.travelid.common.BadgePreferencesHelper
import com.synrgy.travelid.common.formatDate
import com.synrgy.travelid.common.formatDateOrderHistory
import com.synrgy.travelid.common.formatPriceOrderHistory
import com.synrgy.travelid.databinding.ArticleRvSemuaBinding
import com.synrgy.travelid.databinding.ListItemNotificationBinding
import com.synrgy.travelid.databinding.ListItemOrderHistoryBinding
import com.synrgy.travelid.domain.model.main.Article
import com.synrgy.travelid.domain.model.main.Notification
import com.synrgy.travelid.domain.model.main.OrderHistory
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

class OrderHistoryAdapter(private val onClick: OnClickListener): RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<OrderHistory>() {
        override fun areItemsTheSame(oldItem: OrderHistory, newItem: OrderHistory): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: OrderHistory, newItem: OrderHistory): Boolean {
            return oldItem.id == newItem.id
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)
    fun submitData(value: List<OrderHistory>?) = differ.submitList(value)

    interface OnClickListener {
        fun onClickItem(data: OrderHistory)
    }

    inner class ViewHolder(private val binding: ListItemOrderHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: OrderHistory) {
            binding.tvDerpature.text = "${data.originCity} - ${data.destinationCity}"
            binding.tvIdDate.text = data.flightNumber + " • " + formatDateOrderHistory(data.flightTime)
            if (data.paid == "true") {
                binding.tvSuccess.text = "Berhasil"
            }
            binding.tvTotalPrice.text = formatPriceOrderHistory(data.totalPrice)
            binding.root.setOnClickListener {
                onClick.onClickItem(data)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderHistoryAdapter.ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return ViewHolder(ListItemOrderHistoryBinding.inflate(inflate, parent, false))
    }

    override fun onBindViewHolder(holder: OrderHistoryAdapter.ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let {
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}