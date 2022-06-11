package com.c22ps322.capstone.views

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.c22ps322.capstone.databinding.ActivityRegisterBinding
import com.c22ps322.capstone.models.enums.NetworkResult
import com.c22ps322.capstone.viewmodels.LoginViewModel
import com.c22ps322.capstone.viewmodels.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var nameFinal: String
    private lateinit var emailFinal: String
    private lateinit var passwordFinal: String
    private val registerViewModel by viewModels<RegisterViewModel>()
    private var registerJob: Job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // add listener to name edittext
        binding.nameEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!isNameValid(binding.nameEt.text!!.toString())) {
                    binding.nameTil.helperText = "Name cannot be empty"
                } else {
                    binding.nameTil.helperText = null
                }

                setRegisterButtonEnable()
            }
            override fun afterTextChanged(p0: Editable?) {
                nameFinal = binding.nameEt.text.toString()
            }
        })

        // add listener to email edittext
        binding.emailEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!isEmailValid(binding.emailEt.text!!.toString())) {
                    binding.emailTil.helperText = "Email is not valid"
                } else {
                    binding.emailTil.helperText = null
                }

                setRegisterButtonEnable()
            }
            override fun afterTextChanged(p0: Editable?) {
                emailFinal = binding.emailEt.text.toString()
            }
        })

        // add listener to password edittext
        binding.passwordEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!isPasswordValid(binding.passwordEt.text!!.toString())) {
                    binding.passwordTil.helperText = "Minimum 8 characters"
                } else {
                    binding.passwordTil.helperText = null
                }

                setRegisterButtonEnable()
            }
            override fun afterTextChanged(p0: Editable?) {
                passwordFinal = binding.passwordEt.text.toString()
            }
        })

        // add listener for directing to login page
        binding.loginNowTv.setOnClickListener {
            val loginIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(loginIntent)
            finish()
        }

        // add listener for post register
        binding.registerBtn.setOnClickListener {
            register(nameFinal, emailFinal, passwordFinal)
        }
    }

    private fun setRegisterButtonEnable() {
        binding.registerBtn.isEnabled = isNameValid(binding.nameEt.text!!.toString()) && isEmailValid(binding.emailEt.text!!.toString()) && isPasswordValid(binding.passwordEt.text!!.toString())
    }

    private fun showLoading(isLoading: Boolean) {
        binding.registerPg.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 8
    }

    private fun isNameValid(name: String): Boolean {
        return name.isNotEmpty()
    }

    private fun register(name: String, email: String, password: String) {
        lifecycleScope.launchWhenResumed {
            if (registerJob.isActive) registerJob.cancel()

            registerJob = launch {

                registerViewModel.register(name, email, password).collect { result ->
                    when (result) {
                        is NetworkResult.Loading -> showLoading(true)

                        is NetworkResult.Success -> {
                            showLoading(false)

                            binding.root.let {
                                Snackbar.make(
                                    it,
                                    "Successfully created account",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }

                            val mainIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(mainIntent)

                            finish()
                        }

                        is NetworkResult.Error -> {
                            showLoading(false)

                            binding.root.let {
                                Snackbar.make(
                                    it,
                                    result.message,
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }
}