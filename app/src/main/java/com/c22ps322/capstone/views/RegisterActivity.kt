package com.c22ps322.capstone.views

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.c22ps322.capstone.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var nameFinal: String
    private lateinit var telpFinal: String
    private lateinit var emailFinal: String
    private lateinit var passwordFinal: String

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

        binding.telpEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setRegisterButtonEnable()
            }
            override fun afterTextChanged(p0: Editable?) {
                telpFinal = binding.telpEt.text.toString()
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
            // TO DO -- post register
            val text = "Name: $nameFinal, Telp: $telpFinal, Email: $emailFinal, Pass length: ${passwordFinal.length}"
            Toast.makeText(this@RegisterActivity, text, Toast.LENGTH_SHORT).show()

            val loginIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(loginIntent)
        }
    }

    private fun setRegisterButtonEnable() {
        binding.registerBtn.isEnabled = binding.nameEt.text.isNotEmpty() && binding.telpEt.text.isNotEmpty() && binding.emailEt.text.isNotEmpty() && binding.passwordEt.text.isNotEmpty()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.registerPg.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}