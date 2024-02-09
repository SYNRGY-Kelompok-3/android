package com.synrgy.travelid.presentation.home.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.synrgy.travelid.R
import com.synrgy.travelid.common.formatDateListFlight
import com.synrgy.travelid.common.formatDateListFlightEndDate
import com.synrgy.travelid.databinding.FragmentSearchBinding
import com.synrgy.travelid.domain.model.main.ListFlight
import com.synrgy.travelid.domain.model.main.OrderHistory
import com.synrgy.travelid.presentation.home.HomeFragment.Companion.JUMLAH_PENUMPANG
import com.synrgy.travelid.presentation.home.HomeFragment.Companion.KELAS_PENERBANGAN
import com.synrgy.travelid.presentation.home.HomeFragment.Companion.KURSI_BAYI
import com.synrgy.travelid.presentation.home.HomeFragment.Companion.KURSI_DEWASA
import com.synrgy.travelid.presentation.home.HomeFragment.Companion.LOKASI_AWAL
import com.synrgy.travelid.presentation.home.HomeFragment.Companion.LOKASI_TUJUAN
import com.synrgy.travelid.presentation.home.HomeFragment.Companion.TANGGAL_PERGI
import com.synrgy.travelid.presentation.home.HomeFragment.Companion.TANGGAL_PULANG
import com.synrgy.travelid.presentation.home.PilihKelasPenerbanganFragment
import com.synrgy.travelid.presentation.home.search.adapter.ListFlightAdapter
import com.synrgy.travelid.presentation.profile.orderhistory.OrderHistoryFragment
import com.synrgy.travelid.presentation.profile.orderhistory.OrderHistoryViewModel
import com.synrgy.travelid.presentation.profile.orderhistory.adapter.OrderHistoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class SearchFragment : Fragment() {
    companion object{
        const val FLIGHT_ID = "FlightId"
        const val FLIGHT_PRICE = "FlightPrice"
        const val KURSI_DEWASA_DETAIL = "KursiDewasaDetail"
        const val KURSI_BAYI_DETAIL = "KursiBayiDetail"
    }
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var listFlightAdapter: ListFlightAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val page = 0
        val size = 10
        val originCity = arguments?.getString(LOKASI_AWAL)
        val destinationCity = arguments?.getString(LOKASI_TUJUAN)
        val startDate = arguments?.getString(TANGGAL_PERGI)
        val endDate = if(arguments?.getString(TANGGAL_PULANG) == ""){
            formatDateListFlightEndDate(startDate.toString())
        } else {
            formatDateListFlightEndDate(arguments?.getString(TANGGAL_PULANG).toString())
        }
        Log.d("EndDate", endDate)
        val passengerClass = when (arguments?.getString(KELAS_PENERBANGAN)) {
            "Ekonomi" -> "economy"
            "Bisnis" -> "business"
            else -> ""
        }

        viewModel.listFlight(
            page,
            size,
            originCity.toString(),
            destinationCity.toString(),
            formatDateListFlight(startDate.toString()),
            endDate,
            passengerClass
        )


        bindView()
        observeViewModel()
        bindAdapter()
    }

    private fun bindView() {
        binding.txtArrival.text = arguments?.getString(LOKASI_AWAL)
        binding.txtDeparture.text = arguments?.getString(LOKASI_TUJUAN)
        binding.txtDate.text = arguments?.getString(TANGGAL_PERGI)
        binding.txtSeat.text = arguments?.getString(JUMLAH_PENUMPANG) + " kursi"
        binding.txtSeatClass.text = arguments?.getString(KELAS_PENERBANGAN)
        binding.btnBackHome.setOnClickListener { findNavController().popBackStack() }
    }

    private fun observeViewModel(){
        viewModel.showEmpty.observe(viewLifecycleOwner, ::handleEmptyData)
        viewModel.showListFlight.observe(viewLifecycleOwner, ::handleDataFlight)
    }

    private fun bindAdapter() {
        val kursiDewasa = arguments?.getString(KURSI_DEWASA)?.toIntOrNull() ?: 0
        val kursiBayi = arguments?.getString(KURSI_BAYI)?.toIntOrNull() ?: 0
        listFlightAdapter = ListFlightAdapter(kursiDewasa, kursiBayi, object : ListFlightAdapter.OnClickListener {
            override fun onClickItem(data: ListFlight) {
                val bundle = Bundle()
                bundle.putInt(FLIGHT_ID, data.id)
                bundle.putString(KURSI_DEWASA_DETAIL, kursiDewasa.toString())
                bundle.putString(KURSI_BAYI_DETAIL, kursiBayi.toString())
                val detailFlight = DetailFlightFragment()
                detailFlight.arguments = bundle
                detailFlight.show(childFragmentManager, detailFlight.tag)
            }
        })
        binding.rvListTravel.adapter = listFlightAdapter
    }

    private fun handleDataFlight(listFlights: List<ListFlight>) {
        listFlightAdapter.submitData(listFlights)
    }

    private fun handleEmptyData(isEmpty: Boolean) {
        binding.emptyState.visibility = if(isEmpty) View.VISIBLE else View.GONE
    }
}