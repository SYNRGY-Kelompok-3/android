package com.synrgy.travelid.presentation.home.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.synrgy.travelid.common.formatPriceOrderHistory
import com.synrgy.travelid.databinding.ItemListTravelBinding
import com.synrgy.travelid.domain.model.main.ListFlight
import java.text.SimpleDateFormat
import java.util.Locale

class ListFlightAdapter(
    private val kursiDewasa: Int,
    private val kursiAnak: Int,
    private var kursiBayi: Int,
    private val onClick: OnClickListener
): RecyclerView.Adapter<ListFlightAdapter.ViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<ListFlight>() {
        override fun areItemsTheSame(oldItem: ListFlight, newItem: ListFlight): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ListFlight, newItem: ListFlight): Boolean {
            return oldItem.id == newItem.id
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)
    fun submitData(value: List<ListFlight>?) = differ.submitList(value)

    interface OnClickListener {
        fun onClickItem(data: ListFlight)
    }

    inner class ViewHolder(private val binding: ItemListTravelBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListFlight) {
            Glide.with(binding.root)
                .load("https://travelid-backend-java-dev.up.railway.app/showFile/${data.pathLogo}")
                .into(binding.imgLogoAirline)
            binding.tvAirlineName.text = data.airline

            val priceBayi = (data.discountPrice * 10/100)
            binding.tvTravelPrice.text =
                formatPriceOrderHistory(((data.discountPrice * kursiDewasa) + (data.discountPrice * kursiAnak) + (kursiBayi * priceBayi)))
            binding.tvDayStart.text = getDayOfWeek(data.flightTime)
            binding.tvDayEnd.text = getDayOfWeek(data.arrivedTime)
            binding.tvTimeStart.text = getTimeOfDay(data.flightTime)
            binding.tvTimeEnd.text = getTimeOfDay(data.arrivedTime)
            binding.tvOriginAirport.text = data.originAirport
            binding.tvDestinationAirport.text = data.destinationAirport
            when(data.transit){
                "2 transit" -> {
                    binding.tvTransitStatus.text = "2 transit"
                }
                "1_transit" -> {
                    binding.tvTransitStatus.text = "1 transit"
                }
                "langsung" -> {
                    binding.tvTransitStatus.text = "Tanpa transit"
                }
            }
            binding.tvFlightTime.text = data.duration
            when(data.luggage){
                "true" -> {
                    binding.luggage.visibility = View.VISIBLE
                }
                "false" -> {
                    binding.luggage.visibility = View.GONE
                }
            }
            when(data.freeMeal){
                "true" -> {
                    binding.freeMeal.visibility = View.VISIBLE
                }
                "false" -> {
                    binding.freeMeal.visibility = View.GONE
                }
            }
            binding.btnDetails.setOnClickListener {
                onClick.onClickItem(data)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListFlightAdapter.ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return ViewHolder(ItemListTravelBinding.inflate(inflate, parent, false))
    }

    override fun onBindViewHolder(holder: ListFlightAdapter.ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let {
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun getDayOfWeek(dateString: String): String {
        val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val date = inputFormatter.parse(dateString) ?: return ""

        val outputFormatter = SimpleDateFormat("EEEE", Locale("id", "ID"))
        return outputFormatter.format(date)
    }

    fun getTimeOfDay(timeString: String): String {
        val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val date = inputFormatter.parse(timeString) ?: return ""

        val outputFormatter = SimpleDateFormat("HH:mm", Locale("id", "ID"))
        return outputFormatter.format(date)
    }

}