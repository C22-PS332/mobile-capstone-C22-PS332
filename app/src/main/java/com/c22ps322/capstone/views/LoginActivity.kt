package com.c22ps322.capstone.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import com.c22ps322.capstone.R
import com.c22ps322.capstone.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var emailFinal: String
    private lateinit var passwordFinal: String
    private lateinit var linkRegisterText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // checking session

        // setting if button enable

        // add listener to email edittext
        binding.emailEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.passwordEt.text!!.isNotEmpty()) {
                    // set button enable
                    setLoginButtonEnable()
                }
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
                if (binding.emailEt.text!!.isNotEmpty()) {
                    // set button enable
                    setLoginButtonEnable()
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                passwordFinal = binding.passwordEt.text.toString()
            }
        })

        // add listener for directing to register
        binding.registerNowTv.setOnClickListener {
            val regIntent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(regIntent)
        }

        // add listener for login
        binding.logInBtn.setOnClickListener{
            // TO DO -- post login
            val mainIntent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(mainIntent)
        }
    }

    private fun setLoginButtonEnable() {
        val emailText = binding.emailEt.text
        binding.logInBtn.isEnabled = emailText != null && emailText.toString().isNotEmpty()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loginPg.visibility = View.VISIBLE
        } else {
            binding.loginPg.visibility = View.GONE
        }
    }
}