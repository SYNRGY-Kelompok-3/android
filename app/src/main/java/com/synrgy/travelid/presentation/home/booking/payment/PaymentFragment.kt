package com.synrgy.travelid.presentation.home.booking.payment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.synrgy.travelid.R
import com.synrgy.travelid.databinding.FragmentPaymentBinding
import com.synrgy.travelid.domain.model.main.Booking
import com.synrgy.travelid.domain.model.main.PaymentBook
import com.synrgy.travelid.domain.model.main.PaymentBookRequest
import com.synrgy.travelid.presentation.home.booking.book.BookingFragment.Companion.ID_BOOKING
import com.synrgy.travelid.presentation.home.booking.book.BookingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : Fragment() {
    companion object{
        const val PAYMENT_ID = "PaymentId"
    }
    private lateinit var binding: FragmentPaymentBinding
    private val viewModel: PaymentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindView()
        observeLiveData()
    }

    private fun bindView() {
        binding.btnSelanjutnya.setOnClickListener {
            val idBooking = arguments?.getInt(ID_BOOKING)
            Log.d("idBooking", idBooking.toString())
            val jenisBank = binding.etBankPembayaran.text.toString()
            val nomorKartu = binding.etNomorKartu.text.toString()
            val namaKartu = binding.etNamaKartu.text.toString()
            val masaBerlaku = binding.etMasaBerlaku.text.toString()
            val nomorCvv = binding.etNoCvv.text.toString()

            val booking = Booking(idBooking ?: 0)
            val request = PaymentBookRequest(
                booking,
                jenisBank,
                namaKartu,
                nomorKartu,
                masaBerlaku,
                nomorCvv
            )

            viewModel.paymentBook(request)
        }

    }

    private fun observeLiveData(){
        viewModel.paymentBook.observe(viewLifecycleOwner, ::handlePayment)
    }

    private fun handlePayment(paymentBook: PaymentBook) {
        val bundle = Bundle()
        bundle.putInt(PAYMENT_ID, paymentBook.id)
        findNavController().navigate(R.id.action_invoiceFragment_to_homeFragment, bundle)
    }
}