package com.luizcorrea.recipebook.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.luizcorrea.recipebook.R
import coil.load

import androidx.navigation.NavController
import com.luizcorrea.recipebook.data.model.api.MealDetail
import com.luizcorrea.recipebook.databinding.FragmentCategoriesBinding
import com.luizcorrea.recipebook.databinding.RecipeDetailFragmentBinding
import com.luizcorrea.recipebook.ui.viewmodel.FilterByCategoryViewModel
import com.luizcorrea.recipebook.ui.viewmodel.RecipeViewModel
import com.luizcorrea.recipebook.ui.viewmodel.RecipeViewModelFactory
import com.luizcorrea.recipebook.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeDetailFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = RecipeDetailFragment()
    }

    @Inject
    lateinit var assistedFactory: RecipeViewModelFactory

    private lateinit var binding: RecipeDetailFragmentBinding
    private val viewModel: RecipeViewModel by viewModels() {
        RecipeViewModel.Factory(assistedFactory, mealID)
    }
    private var mealID: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<RecipeDetailFragmentBinding>(
            inflater,
            R.layout.recipe_detail_fragment, container, false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (arguments != null) {
            mealID = RecipeDetailFragmentArgs.fromBundle(requireArguments()).mealId
        }
        setupObserver()
    }

    fun setupObserver() {
        viewModel.recipe.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.detailView.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { users ->
                        renderUI(users.meals.get(0))
                    }
                    binding.detailView.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    fun renderUI(mealDetail: MealDetail) {
        binding.mealDetail = mealDetail
        binding.onFavoriteClickListener = this
        viewModel.isFavorite(mealID)
        setupFavoriteObserver()
    }

    fun setupFavoriteObserver() {
        viewModel.favorite.observe(viewLifecycleOwner, Observer {
            it?.let {
               binding.isFavorite = it
            }
        })
    }

    override fun onClick(view: View) {
        viewModel.onFavoriteClicked(mealID)
    }
}