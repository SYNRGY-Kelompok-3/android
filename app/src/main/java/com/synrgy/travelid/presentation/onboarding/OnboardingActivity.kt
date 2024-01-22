package com.synrgy.travelid.presentation.onboarding

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.ForegroundColorSpan
import android.text.style.TypefaceSpan
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.textview.MaterialTextView
import com.synrgy.travelid.R
import com.synrgy.travelid.databinding.ActivityOnboardingBinding
import com.synrgy.travelid.presentation.auth.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Teks yang akan ditampilkan
        val fullText = "Dengan melanjutkan, kamu menyetujui Syarat Layanan kami. Baca Kebijakan Privasi kami."

        // Buat SpannableString untuk menetapkan warna pada beberapa bagian teks
        val spannableString = SpannableString(fullText)

        // Tentukan indeks awal dan akhir setiap bagian teks yang ingin diubah warnanya
        val startTermsOfService = fullText.indexOf("Syarat Layanan")
        val endTermsOfService = startTermsOfService + "Syarat Layanan".length
        val startPrivacyPolicy = fullText.indexOf("Kebijakan Privasi")
        val endPrivacyPolicy = startPrivacyPolicy + "Kebijakan Privasi".length

        // Set warna putih dan jenis huruf poppinsmedium untuk "Syarat Layanan" dan "Kebijakan Privasi"
        spannableString.setSpan(ForegroundColorSpan(Color.WHITE), startTermsOfService, endTermsOfService, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        setCustomTypeface(spannableString, startTermsOfService, endTermsOfService, "font/poppinsmedium.ttf")

        spannableString.setSpan(ForegroundColorSpan(Color.WHITE), startPrivacyPolicy, endPrivacyPolicy, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        setCustomTypeface(spannableString, startPrivacyPolicy, endPrivacyPolicy, "font/poppinsmedium.ttf")

        // Set warna putih untuk "Syarat Layanan" dan "Kebijakan Privasi"
        spannableString.setSpan(ForegroundColorSpan(Color.WHITE), startTermsOfService, endTermsOfService, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(Color.WHITE), startPrivacyPolicy, endPrivacyPolicy, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Set warna #D8D8D8 untuk bagian lainnya
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#B9B9B9")), 0, startTermsOfService, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#B9B9B9")), endTermsOfService, startPrivacyPolicy, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#B9B9B9")), endPrivacyPolicy, fullText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Terapkan SpannableString pada MaterialTextView
        binding.materialTextView4.text = spannableString

        binding.btnMasuk.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setCustomTypeface(spannableString: SpannableString, start: Int, end: Int, fontPath: String) {
        val typeface = ResourcesCompat.getFont(this, R.font.poppinsmedium)
        spannableString.setSpan(CustomTypefaceSpan(typeface!!), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    private class CustomTypefaceSpan(private val typeface: Typeface) : TypefaceSpan("") {
        override fun updateDrawState(ds: TextPaint) {
            applyCustomTypeface(ds, typeface)
        }

        override fun updateMeasureState(p: TextPaint) {
            applyCustomTypeface(p, typeface)
        }

        private fun applyCustomTypeface(paint: Paint, tf: Typeface) {
            paint.typeface = tf
        }
    }

}