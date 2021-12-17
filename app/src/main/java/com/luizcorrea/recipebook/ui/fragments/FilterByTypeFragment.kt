package com.luizcorrea.recipebook.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.luizcorrea.recipebook.utils.*
import com.luizcorrea.recipebook.data.model.db.Meal
import com.luizcorrea.recipebook.databinding.FragmentCategoriesBinding
import com.luizcorrea.recipebook.ui.adapter.MealAdapter
import com.luizcorrea.recipebook.ui.viewmodel.FilterByCategoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.luizcorrea.recipebook.R
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.luizcorrea.recipebook.ui.viewmodel.FilterByCategoryViewModelFactory
import com.luizcorrea.recipebook.utils.AppUtils.Companion.hideKeyboard
import com.luizcorrea.recipebook.utils.GridSpacingItemDecoration
import java.util.*

@AndroidEntryPoint
class FilterByTypeFragment : Fragment(), MealAdapter.FavoriteClickListener {

    private lateinit var adapter: MealAdapter
    private lateinit var binding: FragmentCategoriesBinding
    private var category: String? = null
    private var isFavorites: String = "N"

    @Inject
    lateinit var assistedFactory: FilterByCategoryViewModelFactory
    private val filterByCategoryViewModel: FilterByCategoryViewModel by viewModels() {
        FilterByCategoryViewModel.Factory(assistedFactory, category, isFavorites)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentCategoriesBinding>(
            inflater,
            R.layout.fragment_categories, container, false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val controller: NavController = Navigation.findNavController(requireView())
        controller.popBackStack(R.id.recipeDetailFragment, true)
        if (arguments != null) {
            category = FilterByTypeFragmentArgs.fromBundle(requireArguments()).category
            isFavorites = FilterByTypeFragmentArgs.fromBundle(requireArguments()).isFavorites
        }
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        binding.recyclerView.layoutManager = GridLayoutManager(activity, 1)
        adapter = MealAdapter(arrayListOf(), this)
        binding.recyclerView.addItemDecoration(
            GridSpacingItemDecoration(true, 1, 20, true)
        )
        binding.recyclerView.adapter = adapter
        binding.ivCancel.setOnClickListener {
            binding.editSearch.text = null
            hideKeyboard(requireActivity())
        }
        binding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterByCategoryViewModel.setSearchText(s.toString())
            }

        })
    }

    private fun setupObserver() {
        filterByCategoryViewModel.searchText.observe(this, Observer {
            it?.let {
                adapter.filter.filter(it)
            }
        })
        filterByCategoryViewModel.meals.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { users ->
                        renderList(users.meals)
                    }
                    binding.recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(meals: List<Meal>) {
        adapter.clearData()
        adapter.addData(meals)
        adapter.notifyDataSetChanged()
        binding.editSearch.text?.let {
            adapter.filter.filter(it.toString())
        }
    }

    override fun onFavoriteClick(meal: Meal) {
        filterByCategoryViewModel.onFavoriteClicked(meal)
    }

}