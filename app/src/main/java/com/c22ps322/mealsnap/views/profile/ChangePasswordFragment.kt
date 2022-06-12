package com.c22ps322.mealsnap.views.profile

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.c22ps322.mealsnap.R
import com.c22ps322.mealsnap.databinding.FragmentChangePasswordBinding
import com.c22ps322.mealsnap.models.enums.NetworkResult
import com.c22ps322.mealsnap.utils.showErrorInput
import com.c22ps322.mealsnap.viewmodels.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job

@AndroidEntryPoint
class ChangePasswordFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentChangePasswordBinding? = null

    private val binding get() = _binding

    private lateinit var navController: NavController

    private val profileViewModel by viewModels<ProfileViewModel>()

    private var changePasswordJob: Job = Job()

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
        val appBarConfiguration =
            AppBarConfiguration(setOf(R.id.homeFragment, R.id.profileFragment))

        navController = findNavController()

        binding?.toolbarFragment?.apply {
            this.setupWithNavController(navController, appBarConfiguration)

            this.setNavigationOnClickListener { navController.navigateUp(appBarConfiguration) }
        }
    }

    private fun setupInputValidation() {

        binding?.apply {
            this.newPasswordEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    validateNewPassword(s)
                }

                override fun afterTextChanged(s: Editable?) {}
            })

            this.retypeNewPasswordEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    validateRetypedPassword(s)
                }

                override fun afterTextChanged(s: Editable?) {}
            })

            this.savePasswordBtn.setOnClickListener(this@ChangePasswordFragment)
        }
    }

    private fun validateRetypedPassword(password: CharSequence) {
        binding?.newPasswordEt?.text.let {
            if (it.toString() != password.toString() && password.isNotEmpty()) binding?.retypeNewPasswordLabel?.showErrorInput(
                getString(R.string.error_new_password)
            )
            else binding?.retypeNewPasswordLabel?.error = null
        }
    }

    private fun validateNewPassword(password: CharSequence) {
        if (password.length < 8 && password.isNotEmpty()) binding?.newPasswordLabel?.showErrorInput(
            getString(R.string.error_change_password)
        )
        else binding?.newPasswordLabel?.error = null
    }

    private fun hideKeyBoard() {
        val currentFocus = requireActivity().currentFocus ?: return

        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }


    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null

        hideKeyBoard()
    }

    override fun onClick(v: View) {
        val oldPassword = binding?.oldPasswordEt?.text?.toString().orEmpty()

        if (TextUtils.isEmpty(oldPassword)) {

            Toast.makeText(
                requireContext(),
                getString(R.string.error_old_password),
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val newPassword = binding?.newPasswordEt?.text?.toString().orEmpty()

        if (newPassword.length < 8) {

            Toast.makeText(
                requireContext(),
                getString(R.string.error_change_password),
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val confirmPassword = binding?.newPasswordEt?.text?.toString().orEmpty()

        if (confirmPassword != newPassword) {

            Toast.makeText(
                requireContext(),
                getString(R.string.error_retyped_password),
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val email = profileViewModel.getEmail() ?: return

        if (changePasswordJob.isActive) changePasswordJob.cancel()

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {

            val changePasswordFlow =
                profileViewModel.changePassword(email, oldPassword, newPassword)

            changePasswordFlow.collect { result ->
                when (result) {
                    is NetworkResult.Loading -> binding?.progressHorizontal?.isVisible = true

                    is NetworkResult.Success -> {
                        binding?.progressHorizontal?.hide()

                        Toast.makeText(
                            requireContext(),
                            result.data.message,
                            Toast.LENGTH_SHORT
                        ).show()

                        navController.navigate(R.id.action_changePasswordFragment_to_profileFragment)
                    }

                    is NetworkResult.Error -> {
                        binding?.progressHorizontal?.hide()

                        Toast.makeText(
                            requireContext(),
                            result.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}