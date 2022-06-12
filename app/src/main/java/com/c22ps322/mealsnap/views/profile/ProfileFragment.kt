package com.c22ps322.mealsnap.views.profile

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.c22ps322.mealsnap.R
import com.c22ps322.mealsnap.databinding.CameraSettingDialogBinding
import com.c22ps322.mealsnap.databinding.FragmentProfileBinding
import com.c22ps322.mealsnap.models.enums.CameraOption
import com.c22ps322.mealsnap.viewmodels.ProfileViewModel
import com.c22ps322.mealsnap.viewmodels.SettingViewModel
import com.c22ps322.mealsnap.views.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding

    private lateinit var navController: NavController

    private lateinit var cameraOption: String

    private lateinit var dialog: Dialog

    private val settingViewModel by viewModels<SettingViewModel>()

    private val profileViewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadSettings()

        setupNavigation()
    }

    private fun loadSettings() {
        settingViewModel.getCameraOption().observe(viewLifecycleOwner) {
            cameraOption = it

            binding?.cameraSelectedTv?.text = it
        }

        val email = profileViewModel.getEmail() ?: return

        binding?.usernameTv?.text = email.substringBefore("@")

        binding?.emailTv?.text = email
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

    override fun onClick(v: View) {
        when (v.id) {

            R.id.camera_setting_btn -> showCameraSettingDialog()

            R.id.edit_account_btn -> navController.navigate(
                R.id.action_profileFragment_to_editProfileFragment,
                null
            )

            R.id.change_password_btn -> navController.navigate(
                R.id.action_profileFragment_to_changePasswordFragment,
                null
            )

            R.id.about_us_btn -> navController.navigate(
                R.id.action_profileFragment_to_aboutUsFragment,
                null
            )

            R.id.save_camera_setting_btn -> {
                updateCameraPreference()

                dialog.dismiss()
            }

            R.id.log_out_btn -> navigateToFirstScreen()

            else -> return
        }
    }


    private fun navigateToFirstScreen() {
        profileViewModel.logout()

        val intent = Intent(requireContext(), LoginActivity::class.java)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)

        startActivity(intent)

        activity?.finish()
    }

    private fun showCameraSettingDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext(), R.style.CustomRoundedAlertDialog)

        val dialogView = CameraSettingDialogBinding.inflate(layoutInflater)

        dialogBuilder.setView(dialogView.root)

        dialogView.saveCameraSettingBtn.setOnClickListener(this)

        when (cameraOption) {
            CameraOption.CAMERA_X -> dialogView.cameraX.isChecked =
                true

            CameraOption.SYSTEM -> dialogView.system.isChecked =
                true
        }

        dialogView.cameraOption.setOnCheckedChangeListener { _, id ->
            cameraOption = when (id) {
                R.id.cameraX -> CameraOption.CAMERA_X

                else -> CameraOption.SYSTEM
            }
        }

        dialog = dialogBuilder.create()

        dialog.show()
    }

    private fun updateCameraPreference() {
        settingViewModel.saveCameraOption(cameraOption)
    }
}