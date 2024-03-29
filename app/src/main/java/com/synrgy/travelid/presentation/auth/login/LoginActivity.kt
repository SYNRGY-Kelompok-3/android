package com.synrgy.travelid.presentation.auth.login

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import com.synrgy.travelid.R
import com.synrgy.travelid.databinding.ActivityLoginBinding
import com.synrgy.travelid.domain.model.auth.ErrorMessage
import com.synrgy.travelid.domain.model.auth.UserLogin
import com.synrgy.travelid.domain.model.auth.UserLoginRequest
import com.synrgy.travelid.presentation.MainActivity
import com.synrgy.travelid.presentation.auth.forgotpassword.LupaPasswordActivity
import com.synrgy.travelid.presentation.auth.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    companion object{
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindView()
        observeViewModel()
    }

    private fun bindView() {
        binding.materialCardView.setBackgroundResource(R.drawable.bg_card_login)
        binding.buttonDaftar.setOnClickListener { handleLoginValidation() }
        binding.tvLupaKataSandi.setOnClickListener { goToForgotPassword() }
        binding.tvDaftar.setOnClickListener { goToRegister() }
        handleEmailTextOnChanged()
        handlePasswordTextOnChanged()
    }

    private fun observeViewModel(){
        viewModel.userLogin.observe(this, ::handleUserLogin)
        viewModel.loading.observe(this, ::handleShowLoading)
    }

    private fun handleUserLogin(userLogin: UserLogin) {
        when(userLogin.message) {
            "user not found" -> {
                binding.tilEmail.error = "Email belum kedaftar nih!"
            }
            "wrong password" -> {
                binding.tilPassword.error = "Kata sandi yang kamu masukin salah!"
            }
            else -> {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            }
        }
    }

    private fun handleLoginValidation() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        if (validator(email, password)){
            val request = UserLoginRequest(email, password)
            viewModel.userLogin(request)
        }
    }

    private fun handleShowLoading(isLoading: Boolean) {
        val dialogCustom = Dialog(this)
        dialogCustom.setContentView(R.layout.alert_loading)
        dialogCustom.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogCustom.setCancelable(false)
        if (isLoading) {
            dialogCustom.show()
        } else {
            dialogCustom.dismiss()
        }
    }

    private fun goToRegister() {
        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
    }

    private fun goToForgotPassword() {
        startActivity(Intent(this@LoginActivity, LupaPasswordActivity::class.java))
    }

    private fun validator(
        email: String,
        password: String
    ): Boolean {
        return when {
            email.isEmpty() -> {
                binding.tilEmail.error = "Email ga boleh kosong ya!"
                binding.tilEmail.isErrorEnabled = true
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.tilEmail.error = "Email ga valid nih!"
                binding.tilEmail.isErrorEnabled = true
                false
            }
            password.isEmpty() -> {
                binding.tilPassword.error = "Kata sandi ga boleh kosong ya!"
                binding.tilPassword.isErrorEnabled = true
                false
            }
            password.length < 8 -> {
                binding.tilPassword.error = "Kata sandimu kurang dari 8 karakter nih!"
                binding.tilPassword.isErrorEnabled = true
                false
            }
            else -> {
                binding.tilEmail.error = null
                binding.tilPassword.error = null

                binding.tilEmail.isErrorEnabled = false
                binding.tilPassword.isErrorEnabled = false
                true
            }
        }
    }

    private fun handlePasswordTextOnChanged() {
        binding.etPassword.doOnTextChanged { text, _, _, _ ->
            if(text!!.isEmpty()){
                binding.tilPassword.error = "Kata sandi ga boleh kosong ya!"
                binding.tilPassword.isErrorEnabled = true
            }else if(text.isNotEmpty()){
                binding.tilPassword.error = null
                binding.tilPassword.isErrorEnabled = false
            }
        }
    }

    private fun handleEmailTextOnChanged() {
        binding.etEmail.doOnTextChanged { text, _, _, _ ->
            if(text!!.isEmpty()){
                binding.tilEmail.error = "Email ga boleh kosong ya!"
                binding.tilEmail.isErrorEnabled = true
            }else if(text.isNotEmpty()){
                binding.tilEmail.error = null
                binding.tilEmail.isErrorEnabled = false
            }
        }
    }
}