package com.synrgy.travelid.presentation.tentangkami

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.synrgy.travelid.R
import com.synrgy.travelid.presentation.layanan.KetentuanLayananFragment
import com.synrgy.travelid.presentation.privasi.PrivasiFragment

class TentangKamiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tentang_kami)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, KetentuanLayananFragment())
                .commit()
        }
    }
}