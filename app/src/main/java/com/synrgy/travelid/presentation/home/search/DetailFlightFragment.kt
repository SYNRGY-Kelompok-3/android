package com.synrgy.travelid.presentation.home.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.synrgy.travelid.R
import com.synrgy.travelid.common.formatDateTimeDetail
import com.synrgy.travelid.common.formatPriceOrderHistory
import com.synrgy.travelid.databinding.FragmentDetailFlightBinding
import com.synrgy.travelid.databinding.FragmentDetailOrderHistoryBinding
import com.synrgy.travelid.domain.model.main.FlightById
import com.synrgy.travelid.presentation.home.search.SearchFragment.Companion.FLIGHT_ID
import com.synrgy.travelid.presentation.home.search.SearchFragment.Companion.FLIGHT_PRICE
import com.synrgy.travelid.presentation.home.search.SearchFragment.Companion.KURSI_BAYI_DETAIL
import com.synrgy.travelid.presentation.home.search.SearchFragment.Companion.KURSI_DEWASA_DETAIL
import com.synrgy.travelid.presentation.profile.orderhistory.OrderHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFlightFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentDetailFlightBinding
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailFlightBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val flightId = requireArguments().getInt(FLIGHT_ID)
        viewModel.flightById(flightId)
        observeLiveData()
        bindView()
    }

    private fun bindView(){
//        val totalPrice = requireArguments().getInt(FLIGHT_PRICE)
//        Log.d("total price", totalPrice.toString())
//        binding.tvTravelPrice.text = formatPriceOrderHistory(totalPrice)
    }

    private fun observeLiveData() {
        viewModel.showFlightyById.observe(viewLifecycleOwner, ::handleDataFlightById)
    }

    private fun handleDataFlightById(data: FlightById) {
        binding.tvFlightTime.text = formatDateTimeDetail(data.flightTime)
        binding.tvOriginCityAirport.text = data.originCity + " - " + data.originAirport
        Glide.with(requireContext())
            .load("https://travelid-backend-java-dev.up.railway.app/showFile/${data.pathLogo}")
            .into(binding.ivFlight)
        binding.tvFlight.text = data.airline
        binding.tvDetailFlight.text = data.flightNumber + " • " + data.passengerClass + " • " + data.duration
        binding.tvArrivedTime.text = formatDateTimeDetail(data.arrivedTime)
        binding.tvDestinationCityAirport.text = data.destinationCity + " - " + data.destinationAirport
        binding.tvMaxKabin.text = "Maksimal kabin dan bagasi " + data.luggage
        when(data.freeMeal){
            "true" -> {
                binding.container7.visibility = View.VISIBLE
                binding.appCompatImageView9.visibility = View.VISIBLE
                binding.tvGratis.text = "Sudah termasuk gratis makan"
            }
            "false" -> {
                binding.container7.visibility = View.GONE
                binding.appCompatImageView9.visibility = View.GONE
            }
        }
        val kursiDewasa = requireArguments().getString(KURSI_DEWASA_DETAIL)!!.toInt()
        val kursiBayi = requireArguments().getString(KURSI_BAYI_DETAIL)!!.toInt()
        val priceBayi = (data.discountPrice * 10/100)
        val totalPrice = ((data.discountPrice * kursiDewasa) + (kursiBayi * priceBayi))
        binding.tvTravelPrice.text = formatPriceOrderHistory(totalPrice)
    }
}