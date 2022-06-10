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
import com.c22ps322.capstone.databinding.ActivityLoginBinding
import com.c22ps322.capstone.models.enums.NetworkResult
import com.c22ps322.capstone.viewmodels.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var emailFinal: String
    private lateinit var passwordFinal: String
    private val loginViewModel by viewModels<LoginViewModel>()
    private var loginJob: Job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set button enable
        setLoginButtonEnable()

        // add listener to email edittext
        binding.emailEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setLoginButtonEnable()
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
                setLoginButtonEnable()
            }
            override fun afterTextChanged(p0: Editable?) {
                passwordFinal = binding.passwordEt.text.toString()
            }
        })

        // add listener for directing to register
        binding.registerNowTv.setOnClickListener {
            val regIntent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(regIntent)
            finish()
        }

        // add listener for login
        binding.logInBtn.setOnClickListener{
            login(emailFinal, passwordFinal)
        }
    }

    private fun setLoginButtonEnable() {
        binding.logInBtn.isEnabled = binding.emailEt.text.isNotEmpty() && binding.passwordEt.text.isNotEmpty()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loginPg.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun login(email: String, password: String) {
        lifecycleScope.launchWhenResumed {
            if (loginJob.isActive) loginJob.cancel()

            loginJob = launch {

                loginViewModel.login(email, password).collect { result ->
                    when (result) {
                        is NetworkResult.Loading -> showLoading(true)

                        is NetworkResult.Success -> {
                            showLoading(false)

                            binding.root.let {
                                Snackbar.make(
                                    it,
                                    "Successfully logged in",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }

                            val mainIntent = Intent(this@LoginActivity, MainActivity::class.java)
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