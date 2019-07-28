package com.tsurkis.recipesearch.ui.screens.recipe.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tsurkis.recipesearch.R
import com.tsurkis.recipesearch.custom.wrappers.ImageLoader
import com.tsurkis.recipesearch.data.repository.model.Recipe
import kotlinx.android.synthetic.main.item_recipe.view.*

class RecipeAdapter(private val imageLoader: ImageLoader) : ListAdapter<Recipe, RecipeViewHolder>(RecipeDataComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipe = getItem(position), imageLoader = imageLoader)
    }
}

class RecipeViewHolder(
    private val rootView: View
) : RecyclerView.ViewHolder(rootView) {

    fun bind(recipe: Recipe, imageLoader: ImageLoader) {
        imageLoader.loadImage(
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