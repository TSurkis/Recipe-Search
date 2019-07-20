package com.tsurkis.recipesearch.ui.screens.recipe.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.tsurkis.recipesearch.R
import kotlinx.android.synthetic.main.activity_recipe_search.*

class RecipeSearchActivity : AppCompatActivity() {

    private lateinit var viewModel: RecipeSearchViewModel
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_search)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this).get(RecipeSearchViewModel::class.java)

        initializeDataObservation()
        initializeUIStateObservation()
        initializeUI()
    }

    private fun initializeUI() {
        recipeSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(queryString: String?): Boolean {
                viewModel.searchRecipes(queryString)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Ignore for easier data manipulation
                return false
            }
        })
    }

    private fun initializeUIStateObservation() {
        viewModel
            .recipesSearchUIState
            .observe(
                this,
                Observer(::onUIStateChanged)
            )
    }

    private fun onUIStateChanged(uiState: RecipesSearchUIState) {
        loader.visibility =
            if (uiState.showLoader) {
                View.VISIBLE
            } else {
                View.GONE
            }

        recipeSearchRecyclerView.visibility =
            if (uiState.shouldDisplaySearchList) {
                View.VISIBLE
            } else {
                View.GONE
            }

        recipeSearchView.setIconifiedByDefault(uiState.shouldShowSearch)

        if (uiState.shouldDisplayError) {
            Toast.makeText(this, uiState.errorText, Toast.LENGTH_LONG).show()
        }
    }

    private fun initializeDataObservation() {
        recipeAdapter = RecipeAdapter()
        recipeSearchRecyclerView.apply {
            this.layoutManager =
                LinearLayoutManager(context)
                    .apply {
                        orientation = VERTICAL
                    }
            this.adapter = this@RecipeSearchActivity.recipeAdapter
        }
        viewModel
            .recipes
            .observe(
                this,
                Observer { recipes ->
                    recipeSearchRecyclerView.scrollToPosition(0)
                    recipeAdapter.submitList(recipes)
                })
    }
}
