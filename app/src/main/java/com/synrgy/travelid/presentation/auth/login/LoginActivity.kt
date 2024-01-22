package com.synrgy.travelid.presentation.auth.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import com.synrgy.travelid.R
import com.synrgy.travelid.databinding.ActivityLoginBinding
import com.synrgy.travelid.domain.model.UserLoginRequest
import com.synrgy.travelid.presentation.auth.forgotpassword.LupaPasswordActivity
import com.synrgy.travelid.presentation.auth.register.RegisterActivity
import com.synrgy.travelid.presentation.home.HomeActivity
import com.synrgy.travelid.presentation.home.HomeFragment
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
        viewModel.openHomePage.observe(this, ::handleOpenHomePage)
        viewModel.error.observe(this, ::handleError)
    }

    private fun handleOpenHomePage(isLoggedIn: Boolean) {
        if(isLoggedIn){
            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
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

    private fun goToRegister() {
        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
    }

    private fun goToForgotPassword() {
        startActivity(Intent(this@LoginActivity, LupaPasswordActivity::class.java))
    }

    private fun handleError(error: String?){
        binding.tilEmail.error = error
        binding.tilPassword.error = error
    }

    private fun validator(
        email: String,
        password: String
    ): Boolean {
        return when {
            email.isEmpty() -> {
                binding.tilEmail.error = "Email ga boleh kosong ya!"
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.tilEmail.error = "Email ga valid nih!"
                false
            }
            password.isEmpty() -> {
                binding.tilPassword.error = "Kata sandi ga boleh kosong ya!"
                false
            }
            password.length < 8 -> {
                binding.tilPassword.error = "Kata sandimu kurang dari 8 karakter nih!"
                false
            }
            else -> {
                binding.tilEmail.error = null
                binding.tilPassword.error = null
                true
            }
        }
    }

    private fun handlePasswordTextOnChanged() {
        binding.etPassword.doOnTextChanged { text, _, _, _ ->
            if(text!!.isEmpty()){
                binding.tilPassword.error = "Kata sandi ga boleh kosong ya!"
            }else if(text.isNotEmpty()){
                binding.tilPassword.error = null
            }
        }
    }

    private fun handleEmailTextOnChanged() {
        binding.etEmail.doOnTextChanged { text, _, _, _ ->
            if(text!!.isEmpty()){
                binding.tilEmail.error = "Email ga boleh kosong ya!"
            }else if(text.isNotEmpty()){
                binding.tilEmail.error = null
            }
        }
    }
}