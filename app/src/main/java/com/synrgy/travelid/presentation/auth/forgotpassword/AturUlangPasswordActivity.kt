package com.synrgy.travelid.presentation.auth.forgotpassword

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.synrgy.travelid.R
import com.synrgy.travelid.databinding.ActivityAturUlangPasswordBinding
import com.synrgy.travelid.databinding.ActivityLupaPasswordBinding
import com.synrgy.travelid.domain.model.UpdatePasswordRequest
import com.synrgy.travelid.presentation.auth.forgotpassword.LupaPasswordActivity.Companion.USER_EMAIL
import com.synrgy.travelid.presentation.auth.forgotpassword.ValidateOTPActivity.Companion.USER_OTP_CODE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AturUlangPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAturUlangPasswordBinding
    private val viewModel: LupaPasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAturUlangPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindView()
    }

    private fun bindView(){
        binding.buttonKirimLupaPw.setOnClickListener { handlePassword() }
        binding.etKataSandi.requestFocus()
        handlePasswordTextOnChanged()
        handleNewPasswordTextOnChanged()
    }

    private fun handlePassword() {
        val userOTP = intent.getStringExtra(USER_OTP_CODE).toString()
        val userEmail = intent.getStringExtra(USER_EMAIL).toString()
        val newPw = binding.etKataSandi.text.toString()
        val confirmNewPw = binding.etKonfirmasiKataSandi.text.toString()

        val validation = handleValidation(
            userOTP, userEmail, newPw, confirmNewPw
        )
        if (validation == "passed"){
            val request = UpdatePasswordRequest(userOTP, userEmail, newPw, confirmNewPw)
            viewModel.updatePassword(request)
            handleOpenBottomSheetSuccess()
        }

    }

    private fun handleOpenBottomSheetSuccess() {
        val bottomSheetFragment = BottomSheetSuccessResetFragment()
        bottomSheetFragment.isCancelable = false
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }

    private fun handleValidation(
        userOTP: String,
        userEmail: String,
        newPw: String,
        confirmNewPw: String
    ): String {
        return when {
            newPw.isEmpty() -> {
                binding.tilKataSandi.error = "Kata sandi ga boleh kosong ya!"
                binding.tilKataSandi.isErrorEnabled = true
                "Kata sandi kosong!"
            }
            newPw.length < 8 -> {
                binding.tilKataSandi.error = "Kata sandimu kurang dari 8 karakter nih!"
                binding.tilKataSandi.isErrorEnabled = true
                "Kata sandi kurang dari 8 karakter!"
            }
            confirmNewPw.isEmpty() -> {
                binding.tilKonfirmasiKataSandi.error = "Konfirmasi kata sandi ga boleh kosong ya!"
                binding.tilKonfirmasiKataSandi.isErrorEnabled = true
                "Konfirmasi kata sandi kosong!"
            }
            confirmNewPw.length < 8 -> {
                binding.tilKonfirmasiKataSandi.error = "Konfirmasi kata sandimu kurang dari 8 karakter nih!"
                binding.tilKonfirmasiKataSandi.isErrorEnabled = true
                "Konfirmasi kata sandi kurang dari 8 karakter!"
            }
            confirmNewPw != newPw -> {
                binding.tilKonfirmasiKataSandi.error = "Konfirmasi kata sandimu ga sesuai!"
                binding.tilKonfirmasiKataSandi.isErrorEnabled = true
                "Konfirmasi kata sandi ga sesuai!"
            }
            else -> {
                binding.tilKataSandi.error = null
                binding.tilKonfirmasiKataSandi.error = null

                binding.tilKataSandi.isErrorEnabled = false
                binding.tilKonfirmasiKataSandi.isErrorEnabled = false
                "passed"
            }
        }
    }


    private fun handlePasswordTextOnChanged() {
        binding.etKataSandi.doOnTextChanged { text, _, _, _ ->
            if(text!!.isEmpty()){
                binding.tilKataSandi.error = "Kata sandi ga boleh kosong ya!"
                binding.tilKataSandi.isErrorEnabled = true
            }else if(text.isNotEmpty()){
                binding.tilKataSandi.error = null
                binding.tilKataSandi.isErrorEnabled = false
            }
        }
    }

    private fun handleNewPasswordTextOnChanged() {
        binding.etKonfirmasiKataSandi.doOnTextChanged { text, _, _, _ ->
            if(text!!.isEmpty()){
                binding.tilKonfirmasiKataSandi.error = "Konfirmasi kata sandi ga boleh kosong ya!"
                binding.tilKonfirmasiKataSandi.isErrorEnabled = true
            }else if(text.isNotEmpty()){
                binding.tilKonfirmasiKataSandi.error = null
                binding.tilKonfirmasiKataSandi.isErrorEnabled = false
            }
        }
    }
}