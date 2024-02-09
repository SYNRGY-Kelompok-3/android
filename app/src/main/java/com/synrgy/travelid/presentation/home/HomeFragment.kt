package com.synrgy.travelid.presentation.home

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.synrgy.travelid.R
import com.synrgy.travelid.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class HomeFragment :
    Fragment(),
    PilihKotaAwalFragment.OriginCitySelectionListener,
    PilihKotaTujuanFragment.DestinationCitySelectionListener,
    PilihJumlahPenumpangFragment.JumlahPenumpangSelectionListener,
    PilihKelasPenerbanganFragment.KelasPenerbanganSelectionListener
{
    companion object {
        const val LOKASI_AWAL = "LokasiAwal"
        const val LOKASI_TUJUAN = "LokasiTujuan"
        const val TANGGAL_PERGI = "TanggalPergi"
        const val TANGGAL_PULANG = "TanggalPulang"
        const val JUMLAH_PENUMPANG = "JumlahPenumpang"
        const val KELAS_PENERBANGAN = "KelasPenerbangan"
        const val KURSI_DEWASA = "KursiDewasa"
        const val KURSI_BAYI = "KursiBayi"
    }
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    @SuppressLint("NewApi")
    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStatusBarColor()
        bindView()
    }

    private fun bindView() {
        binding.etTanggalPergi.setOnClickListener {
            val pilihTanggal = PilihTanggalFragment()
            pilihTanggal.show(childFragmentManager, pilihTanggal.tag)
            childFragmentManager.setFragmentResultListener("dateRequestKey", viewLifecycleOwner) { _, bundle ->
                val selectedDate = bundle.getString("selectedDate")
                binding.etTanggalPergi.setText(selectedDate)
            }
        }

        binding.icSwap.setOnClickListener {
            val locAwal = binding.etLokasiAwal.text.toString()
            val locTujuan = binding.etLokasiTujuan.text.toString()

            binding.etLokasiAwal.setText(locTujuan)
            binding.etLokasiTujuan.setText(locAwal)
        }

        binding.switchPulangPergi.addOnStatusChangedListener { isChecked ->
            if (isChecked) {
                binding.etTanggalPulang.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.neutral_04))
                binding.tilTanggalPulang.boxBackgroundColor = ContextCompat.getColor(requireContext(), R.color.white)
                binding.tvPulang.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary))
                binding.etTanggalPulang.setOnClickListener {
                    val pilihTanggalPulang = PilihTanggalPulangFragment()
                    pilihTanggalPulang.show(childFragmentManager, pilihTanggalPulang.tag)
                    childFragmentManager.setFragmentResultListener("dateRequestKeyPulang", viewLifecycleOwner) { _, bundle ->
                        val selectedDate = bundle.getString("selectedDatePulang")
                        binding.etTanggalPulang.setText(selectedDate)
                    }
                }
            } else {
                binding.etTanggalPulang.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.textColorHint))
                binding.tilTanggalPulang.boxBackgroundColor = ContextCompat.getColor(requireContext(), R.color.boxBackgroundColor)
                binding.tvPulang.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColor))
                binding.etTanggalPulang.text?.clear()
            }
        }

        binding.etLokasiAwal.setOnClickListener {
            val pilihKotaAwal = PilihKotaAwalFragment()
            pilihKotaAwal.setOriginCitySelectionListener(this)
            pilihKotaAwal.show(childFragmentManager, pilihKotaAwal.tag)
        }

        binding.etLokasiTujuan.setOnClickListener {
            val pilihKotaTujuan = PilihKotaTujuanFragment()
            pilihKotaTujuan.setDestinationCitySelectionListener(this)
            pilihKotaTujuan.show(childFragmentManager, pilihKotaTujuan.tag)
        }

        binding.etJumlahPenumpang.setOnClickListener {
            val jumlahPenumpang = PilihJumlahPenumpangFragment()
            jumlahPenumpang.setJumlahPenumpangSelectionListener(this)
            jumlahPenumpang.show(childFragmentManager, jumlahPenumpang.tag)
        }

        binding.etKelasPenerbangan.setOnClickListener {
            val kelasPenerbangan = PilihKelasPenerbanganFragment()
            kelasPenerbangan.setKelasPenerbanganSelectionListener(this)
            kelasPenerbangan.show(childFragmentManager, kelasPenerbangan.tag)
        }

        binding.btnCariTiket.setOnClickListener {
            val lokasiAwal = binding.etLokasiAwal.text.toString()
            val lokasiTujuan = binding.etLokasiTujuan.text.toString()
            val tanggalPergi = binding.etTanggalPergi.text.toString()
            val tanggalPulang = binding.etTanggalPulang.text.toString()
            val jumlahPenumpang = binding.etJumlahPenumpang.text.toString()
            val kelasPenerbangan = binding.etKelasPenerbangan.text.toString()

            val regex = "(\\d+)\\s+Dewasa,\\s+(\\d+)\\s+Anak,\\s+(\\d+)\\s+Bayi".toRegex()
            val matchResult = regex.find(jumlahPenumpang)
            val dewasaCount = matchResult?.groupValues?.get(1)?.toIntOrNull() ?: 0
            val anakCount = matchResult?.groupValues?.get(2)?.toIntOrNull() ?: 0
            val bayiCount = matchResult?.groupValues?.get(3)?.toIntOrNull() ?: 0
            val totalSeats = dewasaCount + anakCount + bayiCount

            val validation = handleValidation(
                lokasiAwal, lokasiTujuan, tanggalPergi, jumlahPenumpang, kelasPenerbangan
            )

            if(validation == "passed") {
                val bundle = Bundle()
                bundle.putString(LOKASI_AWAL, lokasiAwal)
                bundle.putString(LOKASI_TUJUAN, lokasiTujuan)
                bundle.putString(TANGGAL_PERGI, tanggalPergi)
                bundle.putString(TANGGAL_PULANG, tanggalPulang)
                bundle.putString(JUMLAH_PENUMPANG, totalSeats.toString())
                bundle.putString(KELAS_PENERBANGAN, kelasPenerbangan)
                bundle.putString(KURSI_DEWASA, dewasaCount.toString())
                bundle.putString(KURSI_BAYI, bayiCount.toString())
                findNavController().navigate(R.id.action_homeFragment_to_searchFragment, bundle)
            }
        }
    }

    private fun setStatusBarColor() {
        requireActivity().window.statusBarColor = Color.TRANSPARENT
        requireActivity().window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    override fun onOriginCitySelected(city: String) {
        binding.etLokasiAwal.setText(city)
    }

    override fun onDestinationCitySelected(city: String) {
        binding.etLokasiTujuan.setText(city)
    }

    override fun onJumlahPenumpangSelected(dewasa: Int, anak: Int, bayi: Int) {
        binding.etJumlahPenumpang.setText("$dewasa Dewasa, $anak Anak, $bayi Bayi")
    }

    override fun onKelasPenerbanganSelected(kelas: String) {
        binding.etKelasPenerbangan.setText(kelas)
    }

    private fun handleValidation(
        lokasiAwal: String,
        lokasiTujuan: String,
        tanggalPergi: String,
        jumlahPenumpang: String,
        kelasPenerbangan: String
    ): String {
        return when {
            lokasiAwal.isEmpty() -> {
                Toast.makeText(requireContext(), "Lokasi awal tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                "Lokasi awal kosong!"
            }
            lokasiTujuan.isEmpty() -> {
                Toast.makeText(requireContext(), "Lokasi tujuan tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                "Lokasi awal kosong!"
            }
            tanggalPergi.isEmpty() -> {
                Toast.makeText(requireContext(), "Tanggal pergi tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                "Lokasi awal kosong!"
            }
            jumlahPenumpang.isEmpty() -> {
                Toast.makeText(requireContext(), "Jumlah penumpang tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                "Lokasi awal kosong!"
            }
            kelasPenerbangan.isEmpty() -> {
                Toast.makeText(requireContext(), "Kelas penerbangan tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                "Lokasi awal kosong!"
            }
            else -> {
                "passed"
            }
        }
    }
}