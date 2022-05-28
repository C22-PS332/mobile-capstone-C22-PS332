package com.c22ps322.capstone.views.profile

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.c22ps322.capstone.R
import com.c22ps322.capstone.databinding.FragmentChangePasswordBinding
import com.c22ps322.capstone.utils.showErrorInput

class ChangePasswordFragment : Fragment() {

    private var _binding: FragmentChangePasswordBinding ?= null

    private val binding get() = _binding

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNavigation()

        setupInputValidation()
    }

    private fun setupNavigation() {
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.profileFragment))

        navController = findNavController()

        binding?.toolbarFragment?.apply {
            this.setupWithNavController(navController, appBarConfiguration)

            this.setNavigationOnClickListener { navController.navigateUp(appBarConfiguration) }
        }
    }

    private fun setupInputValidation() {

        binding?.apply {
            this.newPasswordEt.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) { validateNewPassword(s) }

                override fun afterTextChanged(s: Editable?) {}
            })

            this.retypeNewPasswordEt.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) { validateRetypedPassword(s) }

                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }

    private fun validateRetypedPassword(password: CharSequence) {
        binding?.newPasswordEt?.text.let {
            if (it.toString() != password.toString() && password.isNotEmpty()) binding?.retypeNewPasswordLabel?.showErrorInput(getString(R.string.error_new_password))
            else binding?.retypeNewPasswordLabel?.error = null
        }
    }

    private fun validateNewPassword(password: CharSequence) {

        if(password.length < 8 && password.isNotEmpty()) binding?.newPasswordLabel?.showErrorInput(getString(R.string.error_change_password))
        else binding?.newPasswordLabel?.error = null
    }

    private fun hideKeyBoard() {
        requireActivity().currentFocus ?: return

        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus!!.windowToken, 0)
    }


    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null

        hideKeyBoard()
    }
}