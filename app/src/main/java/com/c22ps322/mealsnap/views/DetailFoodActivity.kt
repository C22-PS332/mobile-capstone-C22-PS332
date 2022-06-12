package com.c22ps322.mealsnap.views

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.c22ps322.mealsnap.R
import com.c22ps322.mealsnap.adapters.ListIngredientAdapter
import com.c22ps322.mealsnap.adapters.RecipeTabAdapter
import com.c22ps322.mealsnap.databinding.ActivityDetailFoodBinding
import com.c22ps322.mealsnap.models.spoonacular.ExtendedIngredientsItem
import com.c22ps322.mealsnap.models.spoonacular.SpoonacularResponse
import com.c22ps322.mealsnap.utils.OnItemCallbackInterface
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFoodActivity : AppCompatActivity() {

    private lateinit var sponaacularRecipe: SpoonacularResponse

    private lateinit var binding: ActivityDetailFoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailFoodBinding.inflate(layoutInflater)

        setContentView(binding.root)

        sponaacularRecipe =
            intent.getParcelableExtra<SpoonacularResponse>(RECIPE_PARAM) as SpoonacularResponse

        setupNavigation()

        setupTabLayout()

        bindRecipeToActivity()
    }

    private fun bindRecipeToActivity() {

        val listIngredientAdapter = ListIngredientAdapter()

        listIngredientAdapter.onItemCallbackInterface =
            object : OnItemCallbackInterface<ExtendedIngredientsItem> {
                override fun onClick(item: ExtendedIngredientsItem) {
                    Toast.makeText(
                        this@DetailFoodActivity,
                        item.name.orEmpty(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        listIngredientAdapter.submitList(sponaacularRecipe.extendedIngredients.orEmpty())

        binding.apply {
            recipeTitleTv.text = sponaacularRecipe.title.orEmpty()

            recipeDescTv.text = sponaacularRecipe.creditsText.orEmpty()

            timeTv.text = getString(
                R.string.percentage_placeholder,
                sponaacularRecipe.readyInMinutes.toString(),
                "min"
            )

            ingredientList.layoutManager =
                LinearLayoutManager(this@DetailFoodActivity, LinearLayoutManager.HORIZONTAL, false)

            ingredientList.adapter = listIngredientAdapter

            servingsTv.text =
                getString(R.string.food_servings, sponaacularRecipe.servings.toString())
        }

        Glide.with(this)
            .load(sponaacularRecipe.image)
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

        val recipeTabAdapter = RecipeTabAdapter(
            this,
            sponaacularRecipe.summary.orEmpty(),
            sponaacularRecipe.sourceUrl.orEmpty(),
            sponaacularRecipe.nutrition
        )

        binding.apply {
            pager.adapter = recipeTabAdapter

            TabLayoutMediator(tabLayout, pager) { tab, pos ->
                tab.text = when (pos) {
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