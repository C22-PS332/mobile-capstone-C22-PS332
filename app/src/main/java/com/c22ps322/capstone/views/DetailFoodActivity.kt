package com.c22ps322.capstone.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.c22ps322.capstone.R
import com.c22ps322.capstone.adapters.RecipeTabAdapter
import com.c22ps322.capstone.databinding.ActivityDetailFoodBinding
import com.c22ps322.capstone.models.domain.DummyRecipe
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFoodActivity : AppCompatActivity() {

    private lateinit var dummyRecipe: DummyRecipe

    private lateinit var binding: ActivityDetailFoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailFoodBinding.inflate(layoutInflater)

        setContentView(binding.root)

        dummyRecipe = intent.getParcelableExtra<DummyRecipe>(RECIPE_PARAM) as DummyRecipe

        setupNavigation()

        setupTabLayout()

        bindRecipeToActivity()
    }

    private fun bindRecipeToActivity() {
        binding.apply {
            recipeTitleTv.text = dummyRecipe.title

            recipeDescTv.text = dummyRecipe.desc
        }

        Glide.with(this)
            .load(dummyRecipe.imageUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_broken_image_24)
            .into(binding.recipeImg)
    }

    private fun setupNavigation() {

        setSupportActionBar(binding.toolbarFragment)

        supportActionBar?.apply {

            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {

        onBackPressed()

        return true
    }

    private fun setupTabLayout() {

        val recipeTabAdapter = RecipeTabAdapter(this, dummyRecipe.ingredients)

        binding.apply {
            pager.adapter = recipeTabAdapter

            TabLayoutMediator(tabLayout, pager){ tab, pos ->
                tab.text = when(pos){
                    0 -> getString(R.string.recipe)
                    else -> getString(R.string.nutrition)
                }
            }.attach()
        }
    }

    companion object {
        const val RECIPE_PARAM = "recipe-param"
    }
}