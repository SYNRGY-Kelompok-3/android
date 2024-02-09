package com.synrgy.travelid.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.synrgy.travelid.R
import com.synrgy.travelid.databinding.FragmentPilihJumlahPenumpangBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PilihJumlahPenumpangFragment : BottomSheetDialogFragment() {
    interface JumlahPenumpangSelectionListener {
        fun onJumlahPenumpangSelected(dewasa: Int, anak: Int, bayi: Int)
    }
    private var jumlahPenumpangSelectionListener: JumlahPenumpangSelectionListener? = null
    private lateinit var binding: FragmentPilihJumlahPenumpangBinding
    private var counterDewasa = 0
    private var counterAnak = 0
    private var counterBayi = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPilihJumlahPenumpangBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindView()
    }

    private fun bindView() {
        binding.btnIncrementDewasa.setOnClickListener {
            binding.tvCounterDewasa.text = (++counterDewasa).toString()
        }

        binding.btnDecrementDewasa.setOnClickListener {
            if (counterDewasa > 0) {
                counterDewasa--
            } else {
                counterDewasa = 0
            }
            binding.tvCounterDewasa.text = counterDewasa.toString()
        }

        binding.btnIncrementAnak.setOnClickListener {
            binding.tvCounterAnak.text = (++counterAnak).toString()
        }

        binding.btnDecrementAnak.setOnClickListener {
            if (counterAnak > 0) {
                counterAnak--
            } else {
                counterAnak = 0
            }
            binding.tvCounterAnak.text = counterAnak.toString()
        }

        binding.btnIncrementBayi.setOnClickListener {
            binding.tvCounterBayi.text = (++counterBayi).toString()
        }

        binding.btnDecrementBayi.setOnClickListener {
            if (counterBayi > 0) {
                counterBayi--
            } else {
                counterBayi = 0
            }
            binding.tvCounterBayi.text = counterBayi.toString()
        }

        binding.btnSimpan.setOnClickListener {
            val dewasa = binding.tvCounterDewasa.text.toString().toIntOrNull() ?: 0
            val anak = binding.tvCounterAnak.text.toString().toIntOrNull() ?: 0
            val bayi = binding.tvCounterBayi.text.toString().toIntOrNull() ?: 0
            jumlahPenumpangSelectionListener?.onJumlahPenumpangSelected(dewasa, anak, bayi)
            dismiss()
        }
    }

    fun setJumlahPenumpangSelectionListener(listener: JumlahPenumpangSelectionListener){
        jumlahPenumpangSelectionListener = listener
    }
}