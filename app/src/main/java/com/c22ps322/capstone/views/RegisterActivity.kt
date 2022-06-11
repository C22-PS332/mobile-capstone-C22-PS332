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

        // set button enable
        setRegisterButtonEnable()

        // text change listener
        binding.nameEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setRegisterButtonEnable()
            }
            override fun afterTextChanged(p0: Editable?) {
                nameFinal = binding.nameEt.text.toString()
            }
        })

        binding.emailEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setRegisterButtonEnable()
            }
            override fun afterTextChanged(p0: Editable?) {
                emailFinal = binding.emailEt.text.toString()
            }
        })

        binding.passwordEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
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
        binding.registerBtn.isEnabled = binding.nameEt.text!!.isNotEmpty() && binding.emailEt.text!!.isNotEmpty() && binding.passwordEt.text!!.isNotEmpty()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.registerPg.visibility = if (isLoading) View.VISIBLE else View.GONE
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