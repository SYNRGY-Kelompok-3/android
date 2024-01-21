package com.synrgy.travelid.presentation.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import com.synrgy.travelid.databinding.ActivityRegisterBinding
import com.synrgy.travelid.domain.model.ResetPasswordRequest
import com.synrgy.travelid.domain.model.UpdatePasswordRequest
import com.synrgy.travelid.domain.model.UserRegister
import com.synrgy.travelid.domain.model.UserRegisterRequest
import com.synrgy.travelid.presentation.forgotpassword.LupaPasswordActivity
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
    private var identityNumber: String = ""
    private var dateOfBirth: String = ""
    private var gender: String = ""
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
        handleNamaTextOnChanged()
        handlePasswordTextOnChanged()
        handleEmailTextOnChanged()
        handleNomorHandphoneTextOnChanged()
    }

    private fun observeViewModel(){
        viewModel.userRegister.observe(this, ::handleResponse)
    }

    private fun handleRegisterValidation() {
        email = binding.etEmail.text.toString()
        password = binding.etPassword.text.toString()
        fullname = binding.etNama.text.toString()
        phoneNumber = binding.etNomorHandphone.text.toString()
        identityNumber = binding.etNomorIdentity.text.toString()

        val validation = handleValidation(
            fullname, email, phoneNumber, identityNumber, password
        )

        if (validation == "passed") {
            val request = UserRegisterRequest(
                email,
                password,
                fullname,
                identityNumber,
                dateOfBirth,
                gender,
                phoneNumber
            )
            viewModel.userRegister(request)
        }
    }

    private fun handleResponse(response: UserRegister) {
        if(response.message == "Success") { handleOpenBottomSheetSuccess() }
        if(response.message == "Username already used"){
            binding.tilEmail.error = "Email udah kedaftar nih!"
        }else if(response.message == "Identity Number already used") {
            binding.tilNomorIdentity.error = "Nomor KTP udah kedaftar nih!"
        }
    }

    private fun handleOpenBottomSheetSuccess() {
        intent.putExtra(USER_EMAIL_REGISTER, email)
        confirmFragment = ConfirmFragment()
        confirmFragment.show(supportFragmentManager, "BSDialogFragment")
    }

    private fun handleValidation(
        fullname: String,
        email: String,
        phoneNumber: String,
        identityNumber: String,
        password: String
    ): String {
        return when {
            fullname.isEmpty() -> {
                binding.tilNama.error = "Nama ga boleh kosong ya!"
                "Nama kosong!"
            }
            email.isEmpty() -> {
                binding.tilEmail.error = "Email ga boleh kosong ya!"
                "Email kosong!"
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.tilEmail.error = "Email ga sesuai nih!"
                "Email tidak sesuai!"
            }
            phoneNumber.isEmpty() -> {
                binding.tilNomorHandphone.error = "Nomor handphone ga boleh kosong ya!"
                "Nomor handphone kosong!"
            }
            password.isEmpty() -> {
                binding.tilPassword.error = "Password ga boleh kosong ya!"
                "Password kosong!"
            }
            identityNumber.isEmpty() -> {
                binding.tilNomorIdentity.error = "Nomor KTP ga boleh kosong ya!"
                "Nomor KTP kosong!"
            }
            password.length < 8 -> {
                binding.tilPassword.error = "Kata sandimu kurang dari 8 karakter nih!"
                "Kata sandi kurang dari 8 karakter!"
            }
            else -> {
                binding.tilNama.error = null
                binding.tilNomorHandphone.error = null
                binding.tilNomorIdentity.error = null
                binding.tilEmail.error = null
                binding.tilPassword.error = null
                "passed"
            }
        }
    }

    private fun handleNamaTextOnChanged() {
        binding.etNama.doOnTextChanged { text, _, _, _ ->
            if(text!!.isEmpty()){
                binding.tilNama.error = "Nama ga boleh kosong ya!"
            }else if(text.isNotEmpty()){
                binding.tilNama.error = null
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

    private fun handleNomorHandphoneTextOnChanged() {
        binding.etNomorHandphone.doOnTextChanged { text, _, _, _ ->
            if(text!!.isEmpty()){
                binding.tilNomorHandphone.error = "Nomor handphone ga boleh kosong ya!"
            }else if(text.isNotEmpty()){
                binding.tilNomorHandphone.error = null
            }
        }
    }
}