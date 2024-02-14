package com.synrgy.travelid.presentation.home.booking.book

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.card.MaterialCardView
import com.synrgy.travelid.R
import com.synrgy.travelid.common.formatPriceOrderHistory
import com.synrgy.travelid.databinding.FragmentPilihSeatEkonomiBinding
import com.synrgy.travelid.domain.model.main.SeatByFlightId
import com.synrgy.travelid.presentation.home.booking.book.BookingFragment.Companion.FLIGHT_ID_DETAIL_BOOKING
import com.synrgy.travelid.presentation.home.booking.book.BookingFragment.Companion.FLIGHT_PRICE_DETAIL_BOOKING
import com.synrgy.travelid.presentation.home.booking.book.BookingFragment.Companion.JUMLAH_PENUMPANG_BOOKING
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PilihSeatEkonomiFragment : BottomSheetDialogFragment() {
    interface SeatSelectionListener {
        fun onSeatSelected(selectedSeat: String)
        fun onTotalPrice(totalPrice: Int)
    }
    private lateinit var binding: FragmentPilihSeatEkonomiBinding
    private val viewModel: BookingViewModel by viewModels()
    private var seatSelected: String? = null
    private val isWhiteSeats = mutableMapOf<String, Boolean>()
    private var totalPrice: Int = 0
    private val selectedSeats = mutableListOf<String>()
    private var seatSelectionListener: SeatSelectionListener? = null
    private var totalPriceListener: SeatSelectionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPilihSeatEkonomiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val flightDetailIdBooking = arguments?.getInt(FLIGHT_ID_DETAIL_BOOKING)
        if (flightDetailIdBooking != null) {
            viewModel.seatByFlightId(flightDetailIdBooking)
        }

        observeViewModel()
        bindView()
    }

    private fun bindView(){
        val priceFromBooking = arguments?.getInt(FLIGHT_PRICE_DETAIL_BOOKING)
        binding.tvTravelPrice.text = formatPriceOrderHistory(priceFromBooking!!)
        val colorWhite = ContextCompat.getColor(requireContext(), R.color.white)
        val colorStroke = ContextCompat.getColor(requireContext(), R.color.primary)
        val colorBackground1 = ContextCompat.getColor(requireContext(), R.color.background1)
        val colorBackground2 = ContextCompat.getColor(requireContext(), R.color.background2)

        for (row in listOf('A', 'B', 'C', 'D', 'E', 'F')) {
            for (seatNumber in 8..22) {
                val seatId = "$row$seatNumber"
                isWhiteSeats[seatId] = true
                val seatView = binding.root.findViewWithTag<MaterialCardView>(seatId)
                seatView.setOnClickListener {
                    toggleSeatSelection(seatId, seatView, colorStroke, colorWhite, colorBackground1, colorBackground2)
                }
            }
        }

        binding.btnSelanjutnya.setOnClickListener {
            val selectedSeatsString = selectedSeats.joinToString(",")
            seatSelectionListener?.onSeatSelected(selectedSeatsString)
            seatSelectionListener?.onTotalPrice(totalPrice)
            dismiss()
        }
    }

    private fun toggleSeatSelection(
        seatId: String,
        seatView: MaterialCardView,
        colorStroke: Int,
        colorWhite: Int,
        colorBackground1: Int,
        colorBackground2: Int
    ) {
        val priceFromBooking = arguments?.getInt(FLIGHT_PRICE_DETAIL_BOOKING) ?: 0
        var additionalPrice = 0

        if (!seatView.isClickable) {
            seatView.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.seatSelected))
        } else {
            val jumlahPenumpangSeat = arguments?.getString(JUMLAH_PENUMPANG_BOOKING)?.toInt() ?: 0
            if (selectedSeats.size >= jumlahPenumpangSeat) {
                return
            }

            if (isWhiteSeats[seatId] == true) {
                if (!selectedSeats.contains(seatId)) {
                    selectedSeats.add(seatId)
                }
                seatView.setStrokeColor(colorStroke)
                seatView.setStrokeWidth(resources.getDimensionPixelSize(R.dimen.stroke_width))
                seatView.setCardBackgroundColor(colorWhite)

                additionalPrice = if (seatId[0] in listOf('A', 'C', 'D', 'F')) {
                    85000
                } else {
                    75000
                }
            } else {
                if (selectedSeats.contains(seatId)) {
                    selectedSeats.remove(seatId)
                }
                val backgroundColor = if (seatId[0] in listOf('A', 'C', 'E')) colorBackground1 else colorBackground2
                seatView.setStrokeColor(backgroundColor)
                seatView.setStrokeWidth(resources.getDimensionPixelSize(R.dimen.stroke_width))
                seatView.setCardBackgroundColor(backgroundColor)

                additionalPrice = if (seatId[0] in listOf('A', 'C', 'D', 'F')) {
                    -85000
                } else {
                    -75000
                }
            }
            isWhiteSeats[seatId] = !isWhiteSeats[seatId]!!

            totalPrice = priceFromBooking + calculateTotalAdditionalPrice()
            binding.tvTravelPrice.text = formatPriceOrderHistory(totalPrice)
        }
    }

    private fun calculateTotalAdditionalPrice(): Int {
        var totalAdditionalPrice = 0
        for ((seatId, isSelected) in isWhiteSeats) {
            if (!isSelected) {
                val additionalPrice = if (seatId[0] in listOf('A', 'C', 'D', 'F')) {
                    85000
                } else {
                    75000
                }
                totalAdditionalPrice += additionalPrice
            }
        }
        return totalAdditionalPrice
    }

    private fun observeViewModel(){
        viewModel.showSeatBooked.observe(viewLifecycleOwner, ::handleSeatBooked)
    }

    private fun handleSeatBooked(data: List<SeatByFlightId>) {
        for (seatByFlightId in data) {
            val seatNumbers = seatByFlightId.seatBooked.split(",")
            for (seatNumber in seatNumbers) {
                val cardViewId = resources.getIdentifier(seatNumber, "id", requireActivity().packageName)
                val cardView = view?.findViewById<MaterialCardView>(cardViewId)
                cardView?.apply {
                    setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.seatSelected))
                    isClickable = false
                }
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener { dialog ->
            val bottomSheetDialog = dialog as BottomSheetDialog
            val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            val behavior = BottomSheetBehavior.from(bottomSheet!!)
            behavior.isDraggable = false
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        return dialog
    }

    fun setSeatSelectionListener(listener: SeatSelectionListener) {
        seatSelectionListener = listener
    }

    fun setTotalPriceListener(listener: SeatSelectionListener) {
        totalPriceListener = listener
    }
}