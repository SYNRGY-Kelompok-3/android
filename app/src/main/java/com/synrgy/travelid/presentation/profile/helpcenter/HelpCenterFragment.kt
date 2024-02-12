package com.synrgy.travelid.presentation.profile.helpcenter

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.synrgy.travelid.R
import com.synrgy.travelid.domain.model.main.HelpCenterItem
import com.google.android.material.card.MaterialCardView
import com.synrgy.travelid.presentation.profile.helpcenter.adapter.HelpCenterAdapter

class HelpCenterFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HelpCenterAdapter
    private val helpCenterItems: List<HelpCenterItem> by lazy { createHelpCenterItems() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_help_center, container, false)

        recyclerView = view.findViewById(R.id.rv_help_center)
        adapter = HelpCenterAdapter(helpCenterItems)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        val cardHelp = view.findViewById<MaterialCardView>(R.id.card_help)
        cardHelp.setOnClickListener {
            val phoneNumber = "6285267301444"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://wa.me/$phoneNumber")
            startActivity(intent)
        }

        return view
    }

    private fun createHelpCenterItems(): List<HelpCenterItem> {
        val items = mutableListOf<HelpCenterItem>()

        // Apa itu travel.id?
        val content1 = SpannableStringBuilder()
        content1.append("Travel.id atau Travel Indonesia adalah sebuah platform yang dimana menyediakan untuk pembelian tiket pesawat secara online yang memberikan service layanan terbaik yang bisa kamu dapatkan. Tidak hanya itu travel.id memberikan penawaran yang sangat beragam, dimulai dari berbagai pilihan maskapai terbaik di Indonesia yang dapat dipilih oleh kamu, Ada artikel rekomendasi tempat yang bisa kamu kunjungi, dan memilih tempat duduk sesuai kebutuhan kamu dengan hanya sekali pemesanan. ")
        content1.append("\n\n")
        content1.append("Keamanan dan kenyamanan kamu adalah tujuan kami oleh sebab itu kamu tidak perlu khawatir untuk dapat bertransaksi di Travel.id karena Travel.id memiliki layanan customer service 24/7, dan juga keamanan platform yang terbaik. ")
        content1.append("\n\n")
        content1.append("Jadi tunggu apalagi ayo rencanakan perjalananmu dengan memesan tiket melalui Travel.id")
        items.add(HelpCenterItem("Apa itu travel.id?", content1))

        // Jika saya memiliki kendala terhadap pesanan saya, kemana saya harus menghubungi?
        val content2 = SpannableStringBuilder()
        content2.append("Kami dapat dihubungi di call center kami di nomor ")
        val phoneLink = object : ClickableSpan() {
            override fun onClick(view: View) {
                val phoneNumber = "6285267301444"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://wa.me/$phoneNumber")
                startActivity(intent)
            }
        }
        content2.setSpan(phoneLink, content2.length - 13, content2.length, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE)
        content2.append(" atau melalui email di ")
        val emailLink = object : ClickableSpan() {
            override fun onClick(view: View) {
                val emailIntent = Intent(Intent.ACTION_SENDTO)
                emailIntent.data = Uri.parse("mailto:travelidcenter@gmail.com")
                startActivity(emailIntent)
            }
        }
        content2.setSpan(emailLink, content2.length - 23, content2.length, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE)
        items.add(HelpCenterItem("Jika saya memiliki kendala terhadap pesanan saya, kemana saya harus menghubungi?", content2))

        // Bagaimana cara melakukan pemesanan tiket pesawat?
        val content3 = SpannableStringBuilder()
        content3.append("Sebelum melakukan pemesanan, registrasi dan login terlebih dahulu pada halaman ini. Pemesanan tiket pesawat dapat diakses dengan mudah dengan mengisi data seperti kota asal dan tujuan, tanggal pergi dan pulang, jumlah penumpang, dan kelas penerbangan.")
        items.add(HelpCenterItem("Bagaimana cara melakukan pemesanan tiket pesawat?", content3))

        // Bagaimana cara saya untuk melanjutkan pembayaran yang belum saya bayarkan?
        val content4 = SpannableStringBuilder()
        content4.append("Untuk melanjutkan pembayaran yang belum terbayarkan, lihat pada halaman histori transaksi atau di halaman notifikasi pada profil Anda.")
        items.add(HelpCenterItem("Bagaimana cara saya untuk melanjutkan pembayaran yang belum saya bayarkan?", content4))

        // Bagaimana proses refund pada aplikasi ini?
        val content5 = SpannableStringBuilder()
        content5.append("Proses refund saat ini dilakukan secara manual dengan menghubungi customer service kami di Whatsapp ")
        content5.append("+6285267301444") // Add link handling here
        content5.append(" atau melalui email ")
        content5.append("travelidcenter@gmail.com") // Add link handling here
        items.add(HelpCenterItem("Bagaimana proses refund pada aplikasi ini?", content5))

        // Apa yang menjadi perbedaan harga untuk anak-anak dan bayi?
        val content6 = SpannableStringBuilder()
        content6.append("Dewasa dan Anak dikenakan biaya full dari harga penerbangan karena mendapatkan tempat duduk dan free bagasi sebesar 20 kg jika mendapatkan fasilitas bagasi dari maskapainya (Ekonomi) sedangkan untuk bisnis free bagasinya sebesar 30 kg.")
        content6.append("\n")
        content6.append("Infant/Bayi di bawah umur 2 tahun itu 10% dari harga tiket dan free bagasi hanya sebesar 10 kg dengan catatan tidak mendapatkan tempat duduk.")
        items.add(HelpCenterItem("Apa yang menjadi perbedaan harga untuk anak-anak dan bayi?", content6))

        return items
    }
}
