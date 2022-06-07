package com.c22ps322.capstone.views

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.c22ps322.capstone.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var emailFinal: String
    private lateinit var passwordFinal: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // checking session

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
            // TO DO -- post login
            val text = "Email: $emailFinal, Pass length: ${passwordFinal.length}"
            Toast.makeText(this@LoginActivity, text, Toast.LENGTH_SHORT).show()

            val mainIntent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(mainIntent)
        }
    }

    private fun setLoginButtonEnable() {
        binding.logInBtn.isEnabled = binding.emailEt.text.isNotEmpty() && binding.passwordEt.text.isNotEmpty()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loginPg.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}