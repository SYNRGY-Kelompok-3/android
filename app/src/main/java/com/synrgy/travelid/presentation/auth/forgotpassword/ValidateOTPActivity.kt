package com.synrgy.travelid.presentation.auth.forgotpassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import com.synrgy.travelid.databinding.ActivityValidateOtpBinding
import com.synrgy.travelid.domain.model.auth.ResetPasswordRequest
import com.synrgy.travelid.domain.model.auth.ValidateOTP
import com.synrgy.travelid.domain.model.auth.ValidateOTPRequest
import com.synrgy.travelid.presentation.auth.forgotpassword.LupaPasswordActivity.Companion.USER_EMAIL
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ValidateOTPActivity : AppCompatActivity() {
    companion object{
        const val USER_OTP_CODE = "UserOTPCode"
    }
    private lateinit var binding: ActivityValidateOtpBinding
    private val viewModel: LupaPasswordViewModel by viewModels()
    private var countDownTimer: CountDownTimer? = null
    private var timeRemaining: Long = 0
    private var otp1: String = ""
    private var otp2: String = ""
    private var otp3: String = ""
    private var otp4: String = ""
    private var otp5: String = ""
    private var otp6: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityValidateOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindView()
        observeViewModel()
    }

    private fun bindView(){
        binding.btnVerifikasi.setOnClickListener { handleValidation() }
        binding.tvKirimUlang.setOnClickListener { handleSendVerifCode() }
        binding.etOtp1.requestFocus()
        setupOtpTextWatchers()
        setDataUser()
        handleOtpCode1()
        handleOtpCode2()
        handleOtpCode3()
        handleOtpCode4()
        handleOtpCode5()
        handleOtpCode6()
    }

    private fun handleSendVerifCode() {
        val userEmail = intent.getStringExtra(USER_EMAIL)
        val request = ResetPasswordRequest(userEmail.toString())

        viewModel.resetPassword(request)
        startCountdown()
    }

    private fun observeViewModel(){
        viewModel.validateOTP.observe(this, ::handleResponse)
    }

    private fun handleValidation() {
        otp1 = binding.etOtp1.text.toString()
        otp2 = binding.etOtp2.text.toString()
        otp3 = binding.etOtp3.text.toString()
        otp4 = binding.etOtp4.text.toString()
        otp5 = binding.etOtp5.text.toString()
        otp6 = binding.etOtp6.text.toString()

        val otp = ValidateOTPRequest("$otp1$otp2$otp3$otp4$otp5$otp6")
        viewModel.validateOTP(otp)
    }

    private fun handleResponse(validateOTP: ValidateOTP) {
        if(validateOTP.message == "sukses"){ goToActivityAturUlangPassword() }
        else if(validateOTP.message == "Token not valid") {
            binding.tvError.visibility = View.VISIBLE
        }
    }

    private fun goToActivityAturUlangPassword() {
        val sendOTP = "$otp1$otp2$otp3$otp4$otp5$otp6"
        val userEmail = intent.getStringExtra(USER_EMAIL)

        val intentActivity = Intent(
            this@ValidateOTPActivity,
            AturUlangPasswordActivity::class.java
        ).apply {
            putExtra(USER_OTP_CODE, sendOTP)
            putExtra(USER_EMAIL, userEmail)
        }
        startActivity(intentActivity)
    }

    private fun setDataUser() {
        val userEmail = intent.getStringExtra(USER_EMAIL)
        binding.tvEmail.text = userEmail
    }

    private fun setupOtpTextWatchers() {
        val editTextIds = listOf(
            binding.etOtp1, binding.etOtp2, binding.etOtp3,
            binding.etOtp4, binding.etOtp5, binding.etOtp6
        )

        for (i in 0 until editTextIds.size - 1) {
            val currentEditText = editTextIds[i]
            val nextEditText = editTextIds[i + 1]

            currentEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1) {
                        nextEditText.requestFocus()
                    } else if (s?.length == 0) {
                        // Handle backspace or delete key press
                        currentEditText.clearFocus()
                        if (i > 0) {
                            editTextIds[i - 1].requestFocus()
                            editTextIds[i - 1].text = null
                        }
                    }
                }
            })
        }

        val lastEditText = editTextIds.last()
        lastEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

            override fun afterTextChanged(s: Editable?) {
                if (s?.length == 1) {
                    binding.etOtp1.clearFocus()
                } else if (s?.length == 0) {
                    // Handle backspace or delete key press
                    val previousIndex = editTextIds.indexOf(lastEditText) - 1
                    if (previousIndex >= 0) {
                        editTextIds[previousIndex].requestFocus()
                        editTextIds[previousIndex].text = null
                    }
                }
            }
        })
    }

    private fun startCountdown() {
        val countdownTimeInMillis: Long = 1 * 60 * 1000

        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(countdownTimeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining = millisUntilFinished
                updateCountdownText()
            }

            override fun onFinish() {
                timeRemaining = 0
                updateCountdownText()
                binding.tvKirimUlang.isEnabled = true
                binding.tvKirimUlang.text = "Kirim ulang"
            }
        }.start()

        binding.tvKirimUlang.isEnabled = false
    }

    private fun updateCountdownText() {
        val minutes = (timeRemaining / 1000) / 60
        val seconds = (timeRemaining / 1000) % 60
        val countdownText = String.format("%02d:%02d", minutes, seconds)
        binding.tvKirimUlang.text = countdownText
    }

    private fun handleOtpCode1() {
        binding.etOtp1.doOnTextChanged { text, _, _, _ ->
            if(text!!.isEmpty()){
                binding.tvError.visibility = View.GONE
            }else if(text.isNotEmpty()){
                binding.tvError.visibility = View.GONE
            }
        }
    }

    private fun handleOtpCode2() {
        binding.etOtp2.doOnTextChanged { text, _, _, _ ->
            if(text!!.isEmpty()){
                binding.tvError.visibility = View.GONE
            }else if(text.isNotEmpty()){
                binding.tvError.visibility = View.GONE
            }
        }
    }

    private fun handleOtpCode3() {
        binding.etOtp3.doOnTextChanged { text, _, _, _ ->
            if(text!!.isEmpty()){
                binding.tvError.visibility = View.GONE
            }else if(text.isNotEmpty()){
                binding.tvError.visibility = View.GONE
            }
        }
    }

    private fun handleOtpCode4() {
        binding.etOtp4.doOnTextChanged { text, _, _, _ ->
            if(text!!.isEmpty()){
                binding.tvError.visibility = View.GONE
            }else if(text.isNotEmpty()){
                binding.tvError.visibility = View.GONE
            }
        }
    }

    private fun handleOtpCode5() {
        binding.etOtp5.doOnTextChanged { text, _, _, _ ->
            if(text!!.isEmpty()){
                binding.tvError.visibility = View.GONE
            }else if(text.isNotEmpty()){
                binding.tvError.visibility = View.GONE
            }
        }
    }

    private fun handleOtpCode6() {
        binding.etOtp6.doOnTextChanged { text, _, _, _ ->
            if(text!!.isEmpty()){
                binding.tvError.visibility = View.GONE
            }else if(text.isNotEmpty()){
                binding.tvError.visibility = View.GONE
            }
        }
    }
}