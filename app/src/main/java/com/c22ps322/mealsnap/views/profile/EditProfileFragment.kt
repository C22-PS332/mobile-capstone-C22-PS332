package com.c22ps322.mealsnap.views.profile

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.c22ps322.mealsnap.R
import com.c22ps322.mealsnap.databinding.FragmentEditProfileBinding
import com.c22ps322.mealsnap.utils.showErrorInput
import com.c22ps322.mealsnap.utils.showSuccessValidationInput
import com.c22ps322.mealsnap.viewmodels.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null

    private val binding get() = _binding

    private lateinit var navController: NavController

    private val profileViewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNavigation()

        bindUserInformation()

        setupInputValidation()
    }

    private fun bindUserInformation() {
        val email = profileViewModel.getEmail().orEmpty()

        binding?.usernameTv?.text = email.substringBefore("@")
    }

    override fun onDestroyView() {
        super.onDestroyView()

        hideKeyBoard()

        _binding = null
    }

    private fun setupNavigation() {
        val appBarConfiguration =
            AppBarConfiguration(setOf(R.id.homeFragment, R.id.profileFragment))

        navController = findNavController()

        binding?.toolbarFragment?.apply {
            this.setupWithNavController(navController, appBarConfiguration)

            this.setNavigationOnClickListener { navController.navigateUp(appBarConfiguration) }
        }
    }

    private fun setupInputValidation() {

        binding?.fullNameEt?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                validateFullName(s)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding?.contactNumberEt?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                validateContactNumber(s)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding?.emailEt?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                validateEmail(s)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun validateContactNumber(number: CharSequence) {
        if (TextUtils.isEmpty(number))
            binding?.contactNumberLabel?.showErrorInput(getString(R.string.error_contact_number))
        else binding?.contactNumberLabel?.showSuccessValidationInput()
    }

    private fun validateEmail(email: CharSequence) {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            binding?.emailLabel?.showErrorInput(
                getString(R.string.error_email)
            ) else binding?.emailLabel?.showSuccessValidationInput()
    }

    private fun validateFullName(name: CharSequence) {
        if (TextUtils.isEmpty(name))
            binding?.fullNameLabel?.showErrorInput(getString(R.string.error_full_name))
        else binding?.fullNameLabel?.showSuccessValidationInput()
    }

    private fun hideKeyBoard() {
        val currentFocus = requireActivity().currentFocus ?: return

        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}