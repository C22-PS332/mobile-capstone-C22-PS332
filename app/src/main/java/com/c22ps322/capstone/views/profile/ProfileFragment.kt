package com.c22ps322.capstone.views.profile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.c22ps322.capstone.R
import com.c22ps322.capstone.databinding.FragmentProfileBinding
import com.c22ps322.capstone.views.WelcomeActivity


class ProfileFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentProfileBinding ?= null

    private val binding get() = _binding

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNavigation()
    }

    private fun setupNavigation() {
        navController = findNavController()

        binding?.cameraSettingBtn?.setOnClickListener(this)

        binding?.changePasswordBtn?.setOnClickListener(this)

        binding?.aboutUsBtn?.setOnClickListener(this)

        binding?.editAccountBtn?.setOnClickListener(this)

        binding?.logOutBtn?.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onClick(v: View?) {
        v?.let {

            when(it.id){

                R.id.camera_setting_btn -> showCameraSettingDialog()

                R.id.edit_account_btn -> navController.navigate(R.id.action_profileFragment_to_editProfileFragment, null)

                R.id.change_password_btn -> navController.navigate(R.id.action_profileFragment_to_changePasswordFragment, null)

                R.id.about_us_btn -> navController.navigate(R.id.action_profileFragment_to_aboutUsFragment, null)

                R.id.log_out_btn -> navigateToFirstScreen()

                else -> return
            }
        }
    }

    private fun navigateToFirstScreen() {
        val intent = Intent(requireContext(), WelcomeActivity::class.java)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)

        startActivity(intent)

        activity?.finish()
    }

    private fun showCameraSettingDialog() {
        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomRoundedAlertDialog)

        val dialogView = layoutInflater.inflate(R.layout.camera_setting_dialog, null)

        dialog.setView(dialogView)

        dialog.show()
    }
}