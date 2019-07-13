package com.tsurkis.recipesearch.ui.screens.recipesearch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tsurkis.recipesearch.R
import com.tsurkis.recipesearch.custom.wrappers.ImageLoader
import com.tsurkis.recipesearch.data.repository.models.Recipe
import kotlinx.android.synthetic.main.item_recipe.view.*

class RecipeAdapter : ListAdapter<Recipe, RecipeViewHolder>(RecipeDataComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val rootView =
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.item_recipe, parent,
                    false
                )
        return RecipeViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipe = getItem(position))
    }
}

class RecipeViewHolder(
    private val rootView: View
) : RecyclerView.ViewHolder(rootView) {

    fun bind(recipe: Recipe) {
        ImageLoader
            .instance
            .loadImage(
                imageUrl = recipe.thumbnailUrl,
                imageView = rootView.recipeImageView
            )
        rootView.recipeNameTextView.text = recipe.name
    }
}

class RecipeDataComparator : DiffUtil.ItemCallback<Recipe>() {
    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) = oldItem == newItem
}