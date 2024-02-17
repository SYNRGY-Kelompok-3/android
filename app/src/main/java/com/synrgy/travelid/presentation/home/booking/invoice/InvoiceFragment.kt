package com.synrgy.travelid.presentation.home.booking.invoice

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.synrgy.travelid.R
import com.synrgy.travelid.common.formatPriceOrderHistory
import com.synrgy.travelid.databinding.FragmentInvoiceBinding
import com.synrgy.travelid.domain.model.main.InvoiceBooking
import com.synrgy.travelid.presentation.home.booking.payment.PaymentFragment.Companion.BOOK_FLIGHT_ID_PAYMENT
import com.synrgy.travelid.presentation.profile.orderhistory.OrderHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class InvoiceFragment : Fragment() {
    private lateinit var binding: FragmentInvoiceBinding
    private val viewModel: InvoiceViewModel by viewModels()
    private var flightNumber: String = ""
    private var createdDate: String = ""
    private var bankPembayaran: String = ""
    private var namaRekening: String = ""
    private var nomorRekening: String = ""
    private var name: String = ""
    private var phoneNumber: String = ""
    private var email: String = ""
    private var customerName: String = ""
    private var airline: String = ""
    private var travelPrice: Int = 0
    private var serviceFee: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentInvoiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bookId = arguments?.getInt(BOOK_FLIGHT_ID_PAYMENT)
        viewModel.invoiceBooking(bookId!!)
        Log.d("GET BOOK ID FROM PAYMENT", bookId.toString())
        bindView()
        observeLiveData()
    }

    private fun bindView(){
        binding.icBack.setOnClickListener {
            findNavController().navigate(R.id.action_invoiceFragment_to_homeFragment)
        }

        binding.btnLihatEtiket.setOnClickListener {
            val url = "https://staging-booking-travel.vercel.app/andro-invoice/$flightNumber/$createdDate/$bankPembayaran/$namaRekening/$nomorRekening/$name/$phoneNumber/$email/$customerName/$travelPrice/$serviceFee/$travelPrice"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    private fun observeLiveData() {
        viewModel.showInvoice.observe(viewLifecycleOwner, ::handleDataInvoice)
    }

    private fun handleDataInvoice(data: InvoiceBooking) {
        val totalPrice = (data.totalPrice - data.serviceFee)
        binding.tvTanggalInvoice.text = formatDateTimeDetail(data.createdDate)
        binding.idInvoice.text = data.flightNumber
        binding.tvNamaBank.text = data.bankPembayaran
        binding.tvNamaRekening.text = data.namaRekening
        binding.tvNomorRekening.text = data.nomorRekening
        binding.tvNamaLengkap.text = data.name
        binding.tvNoHp.text = data.phoneNumber
        binding.tvEmailPemesan.text = data.email
        binding.tvNamaPenumpang.text = data.customerName
        binding.tvPesawatPlusSeat.text = data.airline
        binding.tvTotalPrice.text = formatPriceOrderHistory(totalPrice)
        binding.tvFeeService.text = formatPriceOrderHistory(data.serviceFee)
        binding.tvTravelPrice.text = formatPriceOrderHistory(data.totalPrice)

        createdDate = formatToEtiket(data.createdDate)
        flightNumber = data.flightNumber
        bankPembayaran = data.bankPembayaran
        namaRekening = data.namaRekening
        nomorRekening = data.nomorRekening
        name = data.name
        phoneNumber = data.phoneNumber
        email = data.email
        customerName = data.customerName
        airline = data.airline
        serviceFee = data.serviceFee
        travelPrice = data.totalPrice
    }

    private fun formatDateTimeDetail(inputDateTime: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))

        try {
            val date = inputFormat.parse(inputDateTime)
            return outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return inputDateTime
    }

    private fun formatToEtiket(inputDateTime: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        try {
            val date = inputFormat.parse(inputDateTime)
            return outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return inputDateTime
    }
}