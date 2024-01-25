package com.synrgy.travelid.presentation.auth.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import com.synrgy.travelid.databinding.ActivityRegisterBinding
import com.synrgy.travelid.domain.model.auth.ErrorMessage
import com.synrgy.travelid.domain.model.auth.UserRegister
import com.synrgy.travelid.domain.model.auth.UserRegisterRequest
import com.synrgy.travelid.presentation.auth.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    companion object{
        const val USER_EMAIL_REGISTER = "UserEmailRegister"
    }
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var confirmFragment: ConfirmFragment
    private var email: String = ""
    private var password: String = ""
    private var fullname: String = ""
    private var phoneNumber: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindView()
        observeViewModel()
    }

    private fun bindView() {
        binding.buttonDaftar.setOnClickListener { handleRegisterValidation() }
        binding.icBack.setOnClickListener { handleGoBack() }
        handleNamaTextOnChanged()
        handlePasswordTextOnChanged()
        handleEmailTextOnChanged()
        handleNomorHandphoneTextOnChanged()
    }

    private fun observeViewModel(){
        viewModel.userRegister.observe(this, ::handleResponse)
        viewModel.errorRegister.observe(this, ::handleError)
    }

    private fun handleRegisterValidation() {
        email = binding.etEmail.text.toString()
        password = binding.etPassword.text.toString()
        fullname = binding.etNama.text.toString()
        phoneNumber = binding.etNomorHandphone.text.toString()

        val validation = handleValidation(
            fullname, email, phoneNumber, password
        )

        if (validation == "passed") {
            val request = UserRegisterRequest(
                email,
                password,
                fullname,
                phoneNumber
            )
            viewModel.userRegister(request)
        }
    }

    private fun handleResponse(response: UserRegister) {
        if(response.message == "Success") { handleOpenBottomSheetSuccess() }
    }

    private fun handleError(errorMessage: ErrorMessage) {
        when (errorMessage.message) {
            "Username already used" -> {
                binding.tilEmail.error = "Email udah kedaftar nih!"
            }
            "password not valid" -> {
                binding.tilPassword.error = "Kata sandi harus diawali huruf kapital dan diakhiri angka!"
            }
        }
    }

    private fun handleOpenBottomSheetSuccess() {
        intent.putExtra(USER_EMAIL_REGISTER, email)
        confirmFragment = ConfirmFragment()
        confirmFragment.show(supportFragmentManager, "BSDialogFragment")
    }

    private fun handleGoBack() {
        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
    }

    private fun handleValidation(
        fullname: String,
        email: String,
        phoneNumber: String,
        password: String
    ): String {
        val passwordRegex = Regex("^(?=.*[A-Z])(?=.*\\d).+\$")

        return when {
            fullname.isEmpty() -> {
                binding.tilNama.error = "Nama ga boleh kosong ya!"
                binding.tilNama.isErrorEnabled = true
                "Nama kosong!"
            }
            email.isEmpty() -> {
                binding.tilEmail.error = "Email ga boleh kosong ya!"
                binding.tilEmail.isErrorEnabled = true
                "Email kosong!"
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.tilEmail.error = "Email ga valid nih!"
                binding.tilEmail.isErrorEnabled = true
                "Email tidak valid!"
            }
            phoneNumber.isEmpty() -> {
                binding.tilNomorHandphone.error = "Nomor handphone ga boleh kosong ya!"
                binding.tilNomorHandphone.isErrorEnabled = true
                "Nomor handphone kosong!"
            }
            password.isEmpty() -> {
                binding.tilPassword.error = "Kata sandi ga boleh kosong ya!"
                binding.tilPassword.isErrorEnabled = true
                "Kata sandi kosong!"
            }
            password.length < 8 -> {
                binding.tilPassword.error = "Kata sandimu kurang dari 8 karakter nih!"
                binding.tilPassword.isErrorEnabled = true
                "Kata sandi kurang dari 8 karakter!"
            }
            !passwordRegex.matches(password) -> {
                binding.tilPassword.error = "Kata sandi harus diawali huruf kapital dan diakhiri angka!"
                binding.tilPassword.isErrorEnabled = true
                "Kata sandi tidak valid!"
            }
            else -> {
                binding.tilNama.error = null
                binding.tilNomorHandphone.error = null
                binding.tilEmail.error = null
                binding.tilPassword.error = null

                binding.tilNama.isErrorEnabled = false
                binding.tilNomorHandphone.isErrorEnabled = false
                binding.tilEmail.isErrorEnabled = false
                binding.tilPassword.isErrorEnabled = false
                "passed"
            }
        }
    }

    private fun handleNamaTextOnChanged() {
        binding.etNama.doOnTextChanged { text, _, _, _ ->
            if(text!!.isEmpty()){
                binding.tilNama.error = "Nama ga boleh kosong ya!"
                binding.tilNama.isErrorEnabled = true
            }else if(text.isNotEmpty()){
                binding.tilNama.error = null
                binding.tilNama.isErrorEnabled = false
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

    private fun handleNomorHandphoneTextOnChanged() {
        binding.etNomorHandphone.doOnTextChanged { text, _, _, _ ->
            if(text!!.isEmpty()){
                binding.tilNomorHandphone.error = "Nomor handphone ga boleh kosong ya!"
                binding.tilNomorHandphone.isErrorEnabled = true
            }else if(text.isNotEmpty()){
                binding.tilNomorHandphone.error = null
                binding.tilNomorHandphone.isErrorEnabled = false
            }
        }
    }
}