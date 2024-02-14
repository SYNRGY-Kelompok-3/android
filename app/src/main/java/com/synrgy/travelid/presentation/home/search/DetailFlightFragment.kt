package com.synrgy.travelid.presentation.home.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
import com.synrgy.travelid.presentation.home.search.SearchFragment.Companion.JUMLAH_PENUMPANG_SEARCH
import com.synrgy.travelid.presentation.home.search.SearchFragment.Companion.KURSI_ANAK_DETAIL
import com.synrgy.travelid.presentation.home.search.SearchFragment.Companion.KURSI_BAYI_DETAIL
import com.synrgy.travelid.presentation.home.search.SearchFragment.Companion.KURSI_DEWASA_DETAIL
import com.synrgy.travelid.presentation.profile.orderhistory.OrderHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFlightFragment : BottomSheetDialogFragment() {
    companion object{
        const val FLIGHT_ID_DETAIL = "FlightIdDetail"
        const val FLIGHT_PRICE_DETAIL = "FlightPriceDetail"
        const val FLIGHT_PRICE_BASE = "FlightPriceBase"
        const val JUMLAH_PENUMPANG_DETAIL = "JumlahPenumpangDetail"
        const val KURSI_DEWASA_DETAIL_TO_BOOK = "KursiDewasaDetailToBook"
        const val KURSI_ANAK_DETAIL_TO_BOOK = "KursiAnakDetailToBook"
        const val KURSI_BAYI_DETAIL_TO_BOOK = "KursiBayiDetailToBook"
    }
    private lateinit var binding: FragmentDetailFlightBinding
    private val viewModel: SearchViewModel by viewModels()
    private var flightIdDetail: Int = 0
    private var flightPriceDetail: Int = 0
    private var flightPriceBase: Int = 0

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
        binding.btnPesanTiket.setOnClickListener {
            val jumlahPenumpang = arguments?.getString(JUMLAH_PENUMPANG_SEARCH)
            val kursiDewasa = requireArguments().getString(KURSI_DEWASA_DETAIL)!!.toInt()
            val kursiAnak = requireArguments().getString(KURSI_ANAK_DETAIL)!!.toInt()
            val kursiBayi = requireArguments().getString(KURSI_BAYI_DETAIL)!!.toInt()

            val bundle = Bundle()
            bundle.putInt(FLIGHT_ID_DETAIL, flightIdDetail)
            bundle.putInt(FLIGHT_PRICE_DETAIL, flightPriceDetail)
            bundle.putInt(FLIGHT_PRICE_BASE, flightPriceBase)
            bundle.putInt(KURSI_DEWASA_DETAIL_TO_BOOK, kursiDewasa)
            bundle.putInt(KURSI_ANAK_DETAIL_TO_BOOK, kursiAnak)
            bundle.putInt(KURSI_BAYI_DETAIL_TO_BOOK, kursiBayi)
            bundle.putString(JUMLAH_PENUMPANG_DETAIL, jumlahPenumpang)
            findNavController().navigate(R.id.action_searchFragment_to_bookingFragment, bundle)
        }
    }

    private fun observeLiveData() {
        viewModel.showFlightyById.observe(viewLifecycleOwner, ::handleDataFlightById)
    }

    private fun handleDataFlightById(data: FlightById) {
        flightIdDetail = data.id
        flightPriceBase = data.discountPrice
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
        val kursiAnak = requireArguments().getString(KURSI_ANAK_DETAIL)!!.toInt()
        val kursiBayi = requireArguments().getString(KURSI_BAYI_DETAIL)!!.toInt()
        val priceBayi = (data.discountPrice * 10/100)
        flightPriceDetail = ((data.discountPrice * kursiDewasa) + (data.discountPrice * kursiAnak) + (kursiBayi * priceBayi))
        binding.tvTravelPrice.text = formatPriceOrderHistory(flightPriceDetail)
    }
}