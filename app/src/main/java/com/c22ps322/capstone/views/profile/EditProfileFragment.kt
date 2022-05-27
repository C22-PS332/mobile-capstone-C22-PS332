package com.c22ps322.capstone.views.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.c22ps322.capstone.R
import com.c22ps322.capstone.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding ?= null

    private val binding get() = _binding

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEditProfileBinding.inflate(inflater, container,false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNavigation()
    }

    private fun setupNavigation() {
        navController = findNavController()

        val appBarConfiguration =
            AppBarConfiguration(setOf(R.id.homeFragment, R.id.profileFragment))

        binding?.toolbarFragment?.let {
            it.setupWithNavController(navController, appBarConfiguration)

            it.setNavigationOnClickListener { navController.navigateUp() }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}