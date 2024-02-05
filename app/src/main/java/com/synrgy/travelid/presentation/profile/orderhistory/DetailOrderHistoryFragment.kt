package com.synrgy.travelid.presentation.profile.orderhistory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.avatarfirst.avatargenlib.AvatarGenerator
import com.bumptech.glide.Glide
import com.synrgy.travelid.R
import com.synrgy.travelid.common.formatDateOrderHistory
import com.synrgy.travelid.common.formatDateTimeDetail
import com.synrgy.travelid.common.formatPriceOrderHistory
import com.synrgy.travelid.databinding.FragmentDetailOrderHistoryBinding
import com.synrgy.travelid.domain.model.main.OrderHistoryById
import com.synrgy.travelid.presentation.profile.orderhistory.OrderHistoryFragment.Companion.ORDER_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailOrderHistoryFragment : Fragment() {
    private lateinit var binding: FragmentDetailOrderHistoryBinding
    private val viewModel: OrderHistoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailOrderHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        val orderId = bundle!!.getInt(ORDER_ID)
        viewModel.orderHistoryById(orderId)
        observeLiveData()
        bindView()
    }

    private fun bindView(){
        binding.icBack.setOnClickListener { findNavController().popBackStack() }
    }

    private fun observeLiveData() {
        viewModel.showOrderHistoryById.observe(viewLifecycleOwner, ::handleDataOrderHistoryById)
    }

    private fun handleDataOrderHistoryById(data: OrderHistoryById) {
        binding.tvDerpature.text = "${data.originCity} - ${data.destinationCity}"
        binding.tvIdDate.text = data.flightNumber + " • " + formatDateOrderHistory(data.flightTime)
        if (data.paid == "true") {
            binding.tvSuccess.text = "Berhasil"
        }
        binding.tvTotalPrice.text = formatPriceOrderHistory(data.totalPrice)

        binding.tvNamaPenumpang.text = "Tn. " + data.name

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
        if (data.freeMeal == "true"){
            binding.tvGratis.text = "Sudah termasuk gratis makan"
        }
    }
}