package com.c22ps322.capstone.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.c22ps322.capstone.views.detail.DetailRecipeFragment
import com.c22ps322.capstone.views.detail.NutritionFragment

class RecipeTabAdapter(fragment: FragmentActivity, private val ingredients: ArrayList<String>) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DetailRecipeFragment()
            else -> NutritionFragment.newInstance(ingredients)
        }
    }
}