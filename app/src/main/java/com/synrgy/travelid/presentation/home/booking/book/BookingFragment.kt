package com.synrgy.travelid.presentation.home.booking.book

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.synrgy.travelid.R
import com.synrgy.travelid.common.formatPriceOrderHistory
import com.synrgy.travelid.databinding.FragmentBookingBinding
import com.synrgy.travelid.domain.model.main.BookFlightAway
import com.synrgy.travelid.domain.model.main.BookFlightAwayRequest
import com.synrgy.travelid.domain.model.main.BookingDetail
import com.synrgy.travelid.domain.model.main.Customer
import com.synrgy.travelid.domain.model.main.Flight
import com.synrgy.travelid.domain.model.main.UserProfile
import com.synrgy.travelid.presentation.home.search.DetailFlightFragment.Companion.FLIGHT_ID_DETAIL
import com.synrgy.travelid.presentation.home.search.DetailFlightFragment.Companion.FLIGHT_PRICE_BASE
import com.synrgy.travelid.presentation.home.search.DetailFlightFragment.Companion.FLIGHT_PRICE_DETAIL
import com.synrgy.travelid.presentation.home.search.DetailFlightFragment.Companion.JUMLAH_PENUMPANG_DETAIL
import com.synrgy.travelid.presentation.home.search.DetailFlightFragment.Companion.KURSI_ANAK_DETAIL_TO_BOOK
import com.synrgy.travelid.presentation.home.search.DetailFlightFragment.Companion.KURSI_BAYI_DETAIL_TO_BOOK
import com.synrgy.travelid.presentation.home.search.DetailFlightFragment.Companion.KURSI_DEWASA_DETAIL_TO_BOOK
import com.synrgy.travelid.presentation.notification.NotificationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingFragment : Fragment(), PilihSeatEkonomiFragment.SeatSelectionListener {
    companion object{
        const val ID_BOOKING = "IdBooking"
        const val FLIGHT_ID_DETAIL_BOOKING = "FlightIdDetailBooking"
        const val FLIGHT_PRICE_DETAIL_BOOKING = "FlightPriceDetailBooking"
        const val JUMLAH_PENUMPANG_BOOKING = "JumlahPenumpangBookingToSeat"
    }
    private lateinit var binding: FragmentBookingBinding
    private val viewModel: BookingViewModel by viewModels()
    private var id: Int = 0
    private var idBook: Int = 0
    private var sortedSeatsArray: List<String> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.userProfile()
        setStatusBarColor()
        bindView()
        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.showUser.observe(viewLifecycleOwner, ::handleShowUser)
        viewModel.bookFlightAway.observe(viewLifecycleOwner, ::handleDataBook)
    }

    private fun bindView() {
        val priceFromDetail = arguments?.getInt(FLIGHT_PRICE_DETAIL)
        val jumlahPenumpangFromSearch = arguments?.getString(JUMLAH_PENUMPANG_DETAIL)
        binding.tvTravelPrice.text = formatPriceOrderHistory(priceFromDetail!!)

        binding.cardSelectSeat.setOnClickListener {
            val flightDetailId = arguments?.getInt(FLIGHT_ID_DETAIL)

            val bundle = Bundle()
            bundle.putInt(FLIGHT_ID_DETAIL_BOOKING, flightDetailId!!)
            bundle.putInt(FLIGHT_PRICE_DETAIL_BOOKING, priceFromDetail)
            bundle.putString(JUMLAH_PENUMPANG_BOOKING, jumlahPenumpangFromSearch)
            val pilihSeatEkonomi = PilihSeatEkonomiFragment()
            pilihSeatEkonomi.arguments = bundle
            pilihSeatEkonomi.setSeatSelectionListener(this)
            pilihSeatEkonomi.setTotalPriceListener(this)
            pilihSeatEkonomi.show(childFragmentManager, pilihSeatEkonomi.tag)
        }

        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }

        setupViewDataPenumpangDewasa()
        setupViewDataPenumpangAnak()
        setupViewDataPenumpangBayi()

        binding.btnSelanjutnya.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val phoneNumber = binding.etNoHandphone.text.toString()
            val flightDetailId = arguments?.getInt(FLIGHT_ID_DETAIL)
            val basePrice = arguments?.getInt(FLIGHT_PRICE_BASE)

            val jumlahKursiDewasa = arguments?.getInt(KURSI_DEWASA_DETAIL_TO_BOOK) ?: 0
            val jumlahKursiAnak = arguments?.getInt(KURSI_ANAK_DETAIL_TO_BOOK) ?: 0
            val jumlahKursiBayi = arguments?.getInt(KURSI_BAYI_DETAIL_TO_BOOK) ?: 0

            val bookingDetails = mutableListOf<BookingDetail>()

            var seatIndex = 0

            for (i in 1..jumlahKursiDewasa) {
                val category = "adult"
                val customerName = "${binding.etNamaDepanPenumpang(i).text} ${binding.etNamaBelakangPenumpang(i).text}"
                val bookingDetail = BookingDetail(
                    customerName = customerName,
                    phoneNumber = phoneNumber,
                    seatNumber = "",
                    totalSeatPrice = 0,
                    category = category
                )
                bookingDetails.add(bookingDetail)

                if (seatIndex < sortedSeatsArray.size) {
                    bookingDetail.seatNumber = sortedSeatsArray[seatIndex]
                    bookingDetail.totalSeatPrice = calculateSeatPrice(bookingDetail.seatNumber!!, basePrice!!)
                    seatIndex++
                }
            }

            for (i in 1..jumlahKursiAnak) {
                val category = "child"
                val customerName = "${binding.etNamaDepanPenumpangAnak(i).text} ${binding.etNamaBelakangPenumpangAnak(i).text}"
                val bookingDetail = BookingDetail(
                    customerName = customerName,
                    phoneNumber = phoneNumber,
                    seatNumber = "",
                    totalSeatPrice = 0,
                    category = category
                )
                bookingDetails.add(bookingDetail)

                if (seatIndex < sortedSeatsArray.size) {
                    bookingDetail.seatNumber = sortedSeatsArray[seatIndex]
                    bookingDetail.totalSeatPrice = calculateSeatPrice(bookingDetail.seatNumber!!, basePrice!!)
                    seatIndex++
                }
            }

            for (i in 1..jumlahKursiBayi) {
                val category = "infant"
                val customerName = "${binding.etNamaDepanPenumpangBayi(i).text} ${binding.etNamaBelakangPenumpangBayi(i).text}"
                val bookingDetail = BookingDetail(
                    customerName = customerName,
                    category = category
                )
                bookingDetails.add(bookingDetail)
            }

            val request = BookFlightAwayRequest(
                customer = Customer(id),
                flight = Flight(flightDetailId!!),
                email = email,
                phoneNumber = phoneNumber,
                listBookingDetail = bookingDetails
            )

            viewModel.bookFlightAway(request)
        }
    }

    private fun calculateSeatPrice(seatNumber: String, basePrice: Int): Int {
        return if (seatNumber[0] == 'A' || seatNumber[0] == 'C' || seatNumber[0] == 'D' || seatNumber[0] == 'F') {
            basePrice + 85000
        } else {
            basePrice + 75000
        }
    }

    private fun handleDataBook(bookFlightAway: BookFlightAway) {
//        idBook = bookFlightAway.id
        val bundle = Bundle()
        bundle.putInt(ID_BOOKING, bookFlightAway.id)
        Log.d("idBook2", idBook.toString())
        findNavController().navigate(R.id.action_bookingFragment_to_paymentFragment, bundle)
    }

    private fun handleShowUser(userProfile: UserProfile) {
        id = userProfile.id
    }

    override fun onSeatSelected(selectedSeat: String) {
        val seatsArray = selectedSeat.split(",")
        sortedSeatsArray = seatsArray.sortedWith(compareBy({ it[0] }, { it.substring(1).toInt() }))
        val sortedSeatsString = sortedSeatsArray.joinToString(", ")
        binding.tvSubtitle.text = "Kursi yang kamu pilih : $sortedSeatsString"
    }

    override fun onTotalPrice(totalPrice: Int) {
        binding.tvTravelPrice.text = formatPriceOrderHistory(totalPrice)
    }

    private fun FragmentBookingBinding.etNamaDepanPenumpang(index: Int): EditText {
        return when (index) {
            1 -> etNamaDepanPenumpang1
            2 -> etNamaDepanPenumpang2
            3 -> etNamaDepanPenumpang3
            4 -> etNamaDepanPenumpang4
            5 -> etNamaDepanPenumpang5
            else -> etNamaDepanPenumpang1
        }
    }

    private fun FragmentBookingBinding.etNamaBelakangPenumpang(index: Int): EditText {
        return when (index) {
            1 -> etNamaBelakangPenumpang1
            2 -> etNamaBelakangPenumpang2
            3 -> etNamaBelakangPenumpang3
            4 -> etNamaBelakangPenumpang4
            5 -> etNamaBelakangPenumpang5
            else -> etNamaBelakangPenumpang1
        }
    }

    private fun FragmentBookingBinding.etNamaDepanPenumpangAnak(index: Int): EditText {
        return when (index) {
            1 -> etNamaDepanPenumpangAnak1
            2 -> etNamaDepanPenumpangAnak2
            3 -> etNamaDepanPenumpangAnak3
            4 -> etNamaDepanPenumpangAnak4
            5 -> etNamaDepanPenumpangAnak5
            else -> etNamaDepanPenumpangAnak1
        }
    }

    private fun FragmentBookingBinding.etNamaBelakangPenumpangAnak(index: Int): EditText {
        return when (index) {
            1 -> etNamaBelakangPenumpangAnak1
            2 -> etNamaBelakangPenumpangAnak2
            3 -> etNamaBelakangPenumpangAnak3
            4 -> etNamaBelakangPenumpangAnak4
            5 -> etNamaBelakangPenumpangAnak5
            else -> etNamaBelakangPenumpangAnak1
        }
    }

    private fun FragmentBookingBinding.etNamaDepanPenumpangBayi(index: Int): EditText {
        return when (index) {
            1 -> etNamaDepanPenumpangBayi1
            2 -> etNamaDepanPenumpangBayi2
            3 -> etNamaDepanPenumpangBayi3
            4 -> etNamaDepanPenumpangBayi4
            5 -> etNamaDepanPenumpangBayi5
            else -> etNamaDepanPenumpangBayi1
        }
    }

    private fun FragmentBookingBinding.etNamaBelakangPenumpangBayi(index: Int): EditText {
        return when (index) {
            1 -> etNamaBelakangPenumpangBayi1
            2 -> etNamaBelakangPenumpangBayi2
            3 -> etNamaBelakangPenumpangBayi3
            4 -> etNamaBelakangPenumpangBayi4
            5 -> etNamaBelakangPenumpangBayi5
            else -> etNamaBelakangPenumpangBayi1
        }
    }

    private fun setupViewDataPenumpangDewasa(){
        when (arguments?.getInt(KURSI_DEWASA_DETAIL_TO_BOOK)) {
            0 -> {
                binding.tvDetailPenumpang1.visibility = View.GONE
                binding.tvSubtitlePenumpang1.visibility = View.GONE
                binding.tilNamaDepanPenumpang1.visibility = View.GONE
                binding.tilNamaBelakangPenumpang1.visibility = View.GONE
                binding.tilTitelPenumpang1.visibility = View.GONE
                binding.tvNamaDepan1.visibility = View.GONE
                binding.tvNamaBelakang1.visibility = View.GONE
                binding.tvTitel1.visibility = View.GONE
            }
            1 -> {
                binding.tvDetailPenumpang1.visibility = View.VISIBLE
                binding.tvSubtitlePenumpang1.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpang1.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpang1.visibility = View.VISIBLE
                binding.tilTitelPenumpang1.visibility = View.VISIBLE
                binding.tvNamaDepan1.visibility = View.VISIBLE
                binding.tvNamaBelakang1.visibility = View.VISIBLE
                binding.tvTitel1.visibility = View.VISIBLE
            }
            2 -> {
                binding.tvDetailPenumpang1.visibility = View.VISIBLE
                binding.tvSubtitlePenumpang1.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpang1.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpang1.visibility = View.VISIBLE
                binding.tilTitelPenumpang1.visibility = View.VISIBLE
                binding.tvNamaDepan1.visibility = View.VISIBLE
                binding.tvNamaBelakang1.visibility = View.VISIBLE
                binding.tvTitel1.visibility = View.VISIBLE

                binding.tvDetailPenumpang2.visibility = View.VISIBLE
                binding.tvSubtitlePenumpang2.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpang2.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpang2.visibility = View.VISIBLE
                binding.tilTitelPenumpang2.visibility = View.VISIBLE
                binding.tvNamaDepan2.visibility = View.VISIBLE
                binding.tvNamaBelakang2.visibility = View.VISIBLE
                binding.tvTitel2.visibility = View.VISIBLE
            }
            3 -> {
                binding.tvDetailPenumpang1.visibility = View.VISIBLE
                binding.tvSubtitlePenumpang1.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpang1.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpang1.visibility = View.VISIBLE
                binding.tilTitelPenumpang1.visibility = View.VISIBLE
                binding.tvNamaDepan1.visibility = View.VISIBLE
                binding.tvNamaBelakang1.visibility = View.VISIBLE
                binding.tvTitel1.visibility = View.VISIBLE

                binding.tvDetailPenumpang2.visibility = View.VISIBLE
                binding.tvSubtitlePenumpang2.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpang2.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpang2.visibility = View.VISIBLE
                binding.tilTitelPenumpang2.visibility = View.VISIBLE
                binding.tvNamaDepan2.visibility = View.VISIBLE
                binding.tvNamaBelakang2.visibility = View.VISIBLE
                binding.tvTitel2.visibility = View.VISIBLE

                binding.tvDetailPenumpang3.visibility = View.VISIBLE
                binding.tvSubtitlePenumpang3.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpang3.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpang3.visibility = View.VISIBLE
                binding.tilTitelPenumpang3.visibility = View.VISIBLE
                binding.tvNamaDepan3.visibility = View.VISIBLE
                binding.tvNamaBelakang3.visibility = View.VISIBLE
                binding.tvTitel3.visibility = View.VISIBLE
            }
            4 -> {
                binding.tvDetailPenumpang1.visibility = View.VISIBLE
                binding.tvSubtitlePenumpang1.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpang1.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpang1.visibility = View.VISIBLE
                binding.tilTitelPenumpang1.visibility = View.VISIBLE
                binding.tvNamaDepan1.visibility = View.VISIBLE
                binding.tvNamaBelakang1.visibility = View.VISIBLE
                binding.tvTitel1.visibility = View.VISIBLE

                binding.tvDetailPenumpang2.visibility = View.VISIBLE
                binding.tvSubtitlePenumpang2.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpang2.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpang2.visibility = View.VISIBLE
                binding.tilTitelPenumpang2.visibility = View.VISIBLE
                binding.tvNamaDepan2.visibility = View.VISIBLE
                binding.tvNamaBelakang2.visibility = View.VISIBLE
                binding.tvTitel2.visibility = View.VISIBLE

                binding.tvDetailPenumpang3.visibility = View.VISIBLE
                binding.tvSubtitlePenumpang3.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpang3.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpang3.visibility = View.VISIBLE
                binding.tilTitelPenumpang3.visibility = View.VISIBLE
                binding.tvNamaDepan3.visibility = View.VISIBLE
                binding.tvNamaBelakang3.visibility = View.VISIBLE
                binding.tvTitel3.visibility = View.VISIBLE

                binding.tvDetailPenumpang4.visibility = View.VISIBLE
                binding.tvSubtitlePenumpang4.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpang4.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpang4.visibility = View.VISIBLE
                binding.tilTitelPenumpang4.visibility = View.VISIBLE
                binding.tvNamaDepan4.visibility = View.VISIBLE
                binding.tvNamaBelakang4.visibility = View.VISIBLE
                binding.tvTitel4.visibility = View.VISIBLE
            }
            5 -> {
                binding.tvDetailPenumpang1.visibility = View.VISIBLE
                binding.tvSubtitlePenumpang1.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpang1.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpang1.visibility = View.VISIBLE
                binding.tilTitelPenumpang1.visibility = View.VISIBLE
                binding.tvNamaDepan1.visibility = View.VISIBLE
                binding.tvNamaBelakang1.visibility = View.VISIBLE
                binding.tvTitel1.visibility = View.VISIBLE

                binding.tvDetailPenumpang2.visibility = View.VISIBLE
                binding.tvSubtitlePenumpang2.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpang2.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpang2.visibility = View.VISIBLE
                binding.tilTitelPenumpang2.visibility = View.VISIBLE
                binding.tvNamaDepan2.visibility = View.VISIBLE
                binding.tvNamaBelakang2.visibility = View.VISIBLE
                binding.tvTitel2.visibility = View.VISIBLE

                binding.tvDetailPenumpang3.visibility = View.VISIBLE
                binding.tvSubtitlePenumpang3.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpang3.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpang3.visibility = View.VISIBLE
                binding.tilTitelPenumpang3.visibility = View.VISIBLE
                binding.tvNamaDepan3.visibility = View.VISIBLE
                binding.tvNamaBelakang3.visibility = View.VISIBLE
                binding.tvTitel3.visibility = View.VISIBLE

                binding.tvDetailPenumpang4.visibility = View.VISIBLE
                binding.tvSubtitlePenumpang4.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpang4.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpang4.visibility = View.VISIBLE
                binding.tilTitelPenumpang4.visibility = View.VISIBLE
                binding.tvNamaDepan4.visibility = View.VISIBLE
                binding.tvNamaBelakang4.visibility = View.VISIBLE
                binding.tvTitel4.visibility = View.VISIBLE

                binding.tvDetailPenumpang5.visibility = View.VISIBLE
                binding.tvSubtitlePenumpang5.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpang5.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpang5.visibility = View.VISIBLE
                binding.tilTitelPenumpang5.visibility = View.VISIBLE
                binding.tvNamaDepan5.visibility = View.VISIBLE
                binding.tvNamaBelakang5.visibility = View.VISIBLE
                binding.tvTitel5.visibility = View.VISIBLE
            }
        }
    }

    private fun setupViewDataPenumpangAnak(){
        when (arguments?.getInt(KURSI_ANAK_DETAIL_TO_BOOK)) {
            0 -> {
                binding.tvDetailPenumpangAnak1.visibility = View.GONE
                binding.tvSubtitlePenumpangAnak1.visibility = View.GONE
                binding.tilNamaDepanPenumpangAnak1.visibility = View.GONE
                binding.tilNamaBelakangPenumpangAnak1.visibility = View.GONE
                binding.tvNamaDepanAnak1.visibility = View.GONE
                binding.tvNamaBelakangAnak1.visibility = View.GONE
            }
            1 -> {
                binding.tvDetailPenumpangAnak1.visibility = View.VISIBLE
                binding.tvSubtitlePenumpangAnak1.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpangAnak1.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpangAnak1.visibility = View.VISIBLE
                binding.tvNamaDepanAnak1.visibility = View.VISIBLE
                binding.tvNamaBelakangAnak1.visibility = View.VISIBLE
            }
            2 -> {
                binding.tvDetailPenumpangAnak1.visibility = View.VISIBLE
                binding.tvSubtitlePenumpangAnak1.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpangAnak1.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpangAnak1.visibility = View.VISIBLE
                binding.tvNamaDepanAnak1.visibility = View.VISIBLE
                binding.tvNamaBelakangAnak1.visibility = View.VISIBLE

                binding.tvDetailPenumpangAnak2.visibility = View.VISIBLE
                binding.tvSubtitlePenumpangAnak2.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpangAnak2.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpangAnak2.visibility = View.VISIBLE
                binding.tvNamaDepanAnak2.visibility = View.VISIBLE
                binding.tvNamaBelakangAnak2.visibility = View.VISIBLE
            }
            3 -> {
                binding.tvDetailPenumpangAnak1.visibility = View.VISIBLE
                binding.tvSubtitlePenumpangAnak1.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpangAnak1.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpangAnak1.visibility = View.VISIBLE
                binding.tvNamaDepanAnak1.visibility = View.VISIBLE
                binding.tvNamaBelakangAnak1.visibility = View.VISIBLE

                binding.tvDetailPenumpangAnak2.visibility = View.VISIBLE
                binding.tvSubtitlePenumpangAnak2.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpangAnak2.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpangAnak2.visibility = View.VISIBLE
                binding.tvNamaDepanAnak2.visibility = View.VISIBLE
                binding.tvNamaBelakangAnak2.visibility = View.VISIBLE

                binding.tvDetailPenumpangAnak3.visibility = View.VISIBLE
                binding.tvSubtitlePenumpangAnak3.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpangAnak3.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpangAnak3.visibility = View.VISIBLE
                binding.tvNamaDepanAnak3.visibility = View.VISIBLE
                binding.tvNamaBelakangAnak3.visibility = View.VISIBLE
            }
            4 -> {
                binding.tvDetailPenumpangAnak1.visibility = View.VISIBLE
                binding.tvSubtitlePenumpangAnak1.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpangAnak1.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpangAnak1.visibility = View.VISIBLE
                binding.tvNamaDepanAnak1.visibility = View.VISIBLE
                binding.tvNamaBelakangAnak1.visibility = View.VISIBLE

                binding.tvDetailPenumpangAnak2.visibility = View.VISIBLE
                binding.tvSubtitlePenumpangAnak2.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpangAnak2.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpangAnak2.visibility = View.VISIBLE
                binding.tvNamaDepanAnak2.visibility = View.VISIBLE
                binding.tvNamaBelakangAnak2.visibility = View.VISIBLE

                binding.tvDetailPenumpangAnak3.visibility = View.VISIBLE
                binding.tvSubtitlePenumpangAnak3.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpangAnak3.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpangAnak3.visibility = View.VISIBLE
                binding.tvNamaDepanAnak3.visibility = View.VISIBLE
                binding.tvNamaBelakangAnak3.visibility = View.VISIBLE

                binding.tvDetailPenumpangAnak4.visibility = View.VISIBLE
                binding.tvSubtitlePenumpangAnak4.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpangAnak4.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpangAnak4.visibility = View.VISIBLE
                binding.tvNamaDepanAnak4.visibility = View.VISIBLE
                binding.tvNamaBelakangAnak4.visibility = View.VISIBLE
            }
            5 -> {
                binding.tvDetailPenumpangAnak1.visibility = View.VISIBLE
                binding.tvSubtitlePenumpangAnak1.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpangAnak1.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpangAnak1.visibility = View.VISIBLE
                binding.tvNamaDepanAnak1.visibility = View.VISIBLE
                binding.tvNamaBelakangAnak1.visibility = View.VISIBLE

                binding.tvDetailPenumpangAnak2.visibility = View.VISIBLE
                binding.tvSubtitlePenumpangAnak2.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpangAnak2.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpangAnak2.visibility = View.VISIBLE
                binding.tvNamaDepanAnak2.visibility = View.VISIBLE
                binding.tvNamaBelakangAnak2.visibility = View.VISIBLE

                binding.tvDetailPenumpangAnak3.visibility = View.VISIBLE
                binding.tvSubtitlePenumpangAnak3.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpangAnak3.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpangAnak3.visibility = View.VISIBLE
                binding.tvNamaDepanAnak3.visibility = View.VISIBLE
                binding.tvNamaBelakangAnak3.visibility = View.VISIBLE

                binding.tvDetailPenumpangAnak4.visibility = View.VISIBLE
                binding.tvSubtitlePenumpangAnak4.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpangAnak4.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpangAnak4.visibility = View.VISIBLE
                binding.tvNamaDepanAnak4.visibility = View.VISIBLE
                binding.tvNamaBelakangAnak4.visibility = View.VISIBLE

                binding.tvDetailPenumpangAnak5.visibility = View.VISIBLE
                binding.tvSubtitlePenumpangAnak5.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpangAnak5.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpangAnak5.visibility = View.VISIBLE
                binding.tvNamaDepanAnak5.visibility = View.VISIBLE
                binding.tvNamaBelakangAnak5.visibility = View.VISIBLE
            }
        }
    }

    private fun setupViewDataPenumpangBayi(){
        when (arguments?.getInt(KURSI_BAYI_DETAIL_TO_BOOK)) {
            0 -> {
                binding.tvDetailPenumpangBayi1.visibility = View.GONE
                binding.tvSubtitlePenumpangBayi1.visibility = View.GONE
                binding.tilNamaDepanPenumpangBayi1.visibility = View.GONE
                binding.tilNamaBelakangPenumpangBayi1.visibility = View.GONE
                binding.tvNamaDepanBayi1.visibility = View.GONE
                binding.tvNamaBelakangBayi1.visibility = View.GONE
            }
            1 -> {
                binding.tvDetailPenumpangBayi1.visibility = View.VISIBLE
                binding.tvSubtitlePenumpangBayi1.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpangBayi1.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpangBayi1.visibility = View.VISIBLE
                binding.tvNamaDepanBayi1.visibility = View.VISIBLE
                binding.tvNamaBelakangBayi1.visibility = View.VISIBLE
            }
            2 -> {
                binding.tvDetailPenumpangBayi1.visibility = View.VISIBLE
                binding.tvSubtitlePenumpangBayi1.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpangBayi1.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpangBayi1.visibility = View.VISIBLE
                binding.tvNamaDepanBayi1.visibility = View.VISIBLE
                binding.tvNamaBelakangBayi1.visibility = View.VISIBLE

                binding.tvDetailPenumpangBayi2.visibility = View.VISIBLE
                binding.tvSubtitlePenumpangBayi2.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpangBayi2.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpangBayi2.visibility = View.VISIBLE
                binding.tvNamaDepanBayi2.visibility = View.VISIBLE
                binding.tvNamaBelakangBayi2.visibility = View.VISIBLE
            }
            3 -> {
                binding.tvDetailPenumpangBayi1.visibility = View.VISIBLE
                binding.tvSubtitlePenumpangBayi1.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpangBayi1.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpangBayi1.visibility = View.VISIBLE
                binding.tvNamaDepanBayi1.visibility = View.VISIBLE
                binding.tvNamaBelakangBayi1.visibility = View.VISIBLE

                binding.tvDetailPenumpangBayi2.visibility = View.VISIBLE
                binding.tvSubtitlePenumpangBayi2.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpangBayi2.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpangBayi2.visibility = View.VISIBLE
                binding.tvNamaDepanBayi2.visibility = View.VISIBLE
                binding.tvNamaBelakangBayi2.visibility = View.VISIBLE

                binding.tvDetailPenumpangBayi3.visibility = View.VISIBLE
                binding.tvSubtitlePenumpangBayi3.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpangBayi3.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpangBayi3.visibility = View.VISIBLE
                binding.tvNamaDepanBayi3.visibility = View.VISIBLE
                binding.tvNamaBelakangBayi3.visibility = View.VISIBLE
            }
            4 -> {
                binding.tvDetailPenumpangBayi1.visibility = View.VISIBLE
                binding.tvSubtitlePenumpangBayi1.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpangBayi1.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpangBayi1.visibility = View.VISIBLE
                binding.tvNamaDepanBayi1.visibility = View.VISIBLE
                binding.tvNamaBelakangBayi1.visibility = View.VISIBLE

                binding.tvDetailPenumpangBayi2.visibility = View.VISIBLE
                binding.tvSubtitlePenumpangBayi2.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpangBayi2.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpangBayi2.visibility = View.VISIBLE
                binding.tvNamaDepanBayi2.visibility = View.VISIBLE
                binding.tvNamaBelakangBayi2.visibility = View.VISIBLE

                binding.tvDetailPenumpangBayi3.visibility = View.VISIBLE
                binding.tvSubtitlePenumpangBayi3.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpangBayi3.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpangBayi3.visibility = View.VISIBLE
                binding.tvNamaDepanBayi3.visibility = View.VISIBLE
                binding.tvNamaBelakangBayi3.visibility = View.VISIBLE

                binding.tvDetailPenumpangBayi4.visibility = View.VISIBLE
                binding.tvSubtitlePenumpangBayi4.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpangBayi4.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpangBayi4.visibility = View.VISIBLE
                binding.tvNamaDepanBayi4.visibility = View.VISIBLE
                binding.tvNamaBelakangBayi4.visibility = View.VISIBLE
            }
            5 -> {
                binding.tvDetailPenumpangBayi1.visibility = View.VISIBLE
                binding.tvSubtitlePenumpangBayi1.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpangBayi1.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpangBayi1.visibility = View.VISIBLE
                binding.tvNamaDepanBayi1.visibility = View.VISIBLE
                binding.tvNamaBelakangBayi1.visibility = View.VISIBLE

                binding.tvDetailPenumpangBayi2.visibility = View.VISIBLE
                binding.tvSubtitlePenumpangBayi2.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpangBayi2.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpangBayi2.visibility = View.VISIBLE
                binding.tvNamaDepanBayi2.visibility = View.VISIBLE
                binding.tvNamaBelakangBayi2.visibility = View.VISIBLE

                binding.tvDetailPenumpangBayi3.visibility = View.VISIBLE
                binding.tvSubtitlePenumpangBayi3.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpangBayi3.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpangBayi3.visibility = View.VISIBLE
                binding.tvNamaDepanBayi3.visibility = View.VISIBLE
                binding.tvNamaBelakangBayi3.visibility = View.VISIBLE

                binding.tvDetailPenumpangBayi4.visibility = View.VISIBLE
                binding.tvSubtitlePenumpangBayi4.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpangBayi4.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpangBayi4.visibility = View.VISIBLE
                binding.tvNamaDepanBayi4.visibility = View.VISIBLE
                binding.tvNamaBelakangBayi4.visibility = View.VISIBLE

                binding.tvDetailPenumpangBayi5.visibility = View.VISIBLE
                binding.tvSubtitlePenumpangBayi5.visibility = View.VISIBLE
                binding.tilNamaDepanPenumpangBayi5.visibility = View.VISIBLE
                binding.tilNamaBelakangPenumpangBayi5.visibility = View.VISIBLE
                binding.tvNamaDepanBayi5.visibility = View.VISIBLE
                binding.tvNamaBelakangBayi5.visibility = View.VISIBLE
            }
        }
    }

    private fun setStatusBarColor() {
        requireActivity().window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}