package com.synrgy.travelid.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.synrgy.travelid.R
import com.synrgy.travelid.domain.model.main.Travel

class SearchAdapter(
    private val dateList: List<Pair<String, String>>,
    private val flightList: List<Travel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_DATE = 1
    private val VIEW_TYPE_FLIGHT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_DATE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_date, parent, false)
                DateViewHolder(view)
            }
            VIEW_TYPE_FLIGHT -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_travel, parent, false)
                FlightViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DateViewHolder -> {
                val (date, price) = dateList[position]
                holder.bind(date, price)
            }
            is FlightViewHolder -> {
                val flight = flightList[position - dateList.size]
                holder.bind(flight)
            }
        }
    }

    override fun getItemCount(): Int {
        return dateList.size + flightList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < dateList.size) {
            VIEW_TYPE_DATE
        } else {
            VIEW_TYPE_FLIGHT
        }
    }

    class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDate: TextView = itemView.findViewById(R.id.tv_date)
        private val tvPrice: TextView = itemView.findViewById(R.id.tv_price)

        fun bind(date: String, price: String) {
            tvDate.text = date
            tvPrice.text = price
        }
    }

    class FlightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvAirlineName: TextView = itemView.findViewById(R.id.tv_airline_name)
        private val tvPrice: TextView = itemView.findViewById(R.id.tv_travel_price)
        private val tvDepartureDate: TextView = itemView.findViewById(R.id.tv_day_start)
        private val tvArrivalDate: TextView = itemView.findViewById(R.id.tv_day_end)
        private val tvDepartureTime: TextView = itemView.findViewById(R.id.tv_time_end)
        private val tvArrivalTime: TextView = itemView.findViewById(R.id.tv_time_start)
        private val tvDepartureAirport: TextView = itemView.findViewById(R.id.tv_destination_airport)
        private val tvArrivalAirport: TextView = itemView.findViewById(R.id.tv_origin_airport)
        private val tvFlightDuration: TextView = itemView.findViewById(R.id.tv_flight_time)

        fun bind(flight: Travel) {
            tvAirlineName.text = flight.airlineName
            tvPrice.text = flight.price
            tvDepartureDate.text = flight.departureDate
            tvArrivalDate.text = flight.arrivalDate
            tvDepartureTime.text = flight.departureTime
            tvArrivalTime.text = flight.arrivalTime
            tvDepartureAirport.text = flight.departureAirport
            tvArrivalAirport.text = flight.arrivalAirport
            tvFlightDuration.text = flight.flightDuration
        }
    }
}
