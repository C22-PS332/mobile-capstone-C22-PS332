package com.c22ps322.capstone.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.c22ps322.capstone.R
import com.c22ps322.capstone.databinding.RecipeCardBinding
import com.c22ps322.capstone.models.domain.DummyRecipe
import com.c22ps322.capstone.utils.OnItemCallbackInterface

class ListRecipeAdapter : ListAdapter<DummyRecipe, ListRecipeAdapter.ViewHolder>(DiffCallBack) {

    lateinit var onItemCallback: OnItemCallbackInterface<DummyRecipe>

    inner class ViewHolder(private val binding: RecipeCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dummyRecipe: DummyRecipe) {

            Glide.with(itemView.context)
                .load(dummyRecipe.imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_broken_image_24)
                .into(binding.recipeImg)

            binding.recipeNameTv.text = dummyRecipe.title

            binding.recipeDescTv.text = dummyRecipe.desc

            binding.recipeCard.setOnClickListener { onItemCallback.onClick(dummyRecipe) }
        }
    }

    object DiffCallBack : DiffUtil.ItemCallback<DummyRecipe>() {
        override fun areItemsTheSame(oldItem: DummyRecipe, newItem: DummyRecipe) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: DummyRecipe, newItem: DummyRecipe) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val withBinding: RecipeCardBinding =
            RecipeCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(withBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}