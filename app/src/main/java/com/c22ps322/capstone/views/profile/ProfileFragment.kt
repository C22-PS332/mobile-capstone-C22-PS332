package com.c22ps322.capstone.views.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.c22ps322.capstone.R
import com.c22ps322.capstone.databinding.FragmentProfileBinding

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

        binding?.changePasswordBtn?.setOnClickListener(this)

        binding?.editAccountBtn?.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onClick(v: View?) {
        v?.let {
            when(it.id){
                R.id.edit_account_btn -> {
                    val actionToEditProfileFragment = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment()

                    navController.navigate(actionToEditProfileFragment)
                }
                R.id.change_password_btn -> {
                    val actionToChangePasswordFragment = ProfileFragmentDirections.actionProfileFragmentToChangePasswordFragment()

                    navController.navigate(actionToChangePasswordFragment)
                }
            }
        }
    }
}