package com.c22ps322.capstone.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.c22ps322.capstone.R
import com.c22ps322.capstone.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemReselectedListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupNavigation()
    }

    private fun setupNavigation(){

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController

        binding.bottomNavBar.apply {
            setupWithNavController(navController)

            setOnItemReselectedListener(this@MainActivity)
        }
    }

    override fun onNavigationItemReselected(item: MenuItem) {
        navController.popBackStack(item.itemId, inclusive = false)
    }
}