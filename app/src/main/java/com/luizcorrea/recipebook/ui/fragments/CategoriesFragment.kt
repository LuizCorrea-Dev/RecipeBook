package com.luizcorrea.recipebook.ui.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.luizcorrea.recipebook.utils.Status
import com.luizcorrea.recipebook.R
import com.luizcorrea.recipebook.data.model.db.Category
import com.luizcorrea.recipebook.databinding.FragmentCategoriesBinding
import com.luizcorrea.recipebook.ui.adapter.CategoryAdapter
import com.luizcorrea.recipebook.ui.viewmodel.CategoryViewModel
import com.luizcorrea.recipebook.utils.AppUtils.Companion.hideKeyboard
import com.luizcorrea.recipebook.utils.GridSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CategoriesFragment : Fragment() {
    private val TAG = "CategoriesFragment"

    private val categoryViewModel: CategoryViewModel by viewModels()
    private lateinit var adapter: CategoryAdapter
    private lateinit var binding: FragmentCategoriesBinding

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
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        binding.recyclerView.layoutManager = GridLayoutManager(activity, 1)
        adapter = CategoryAdapter(arrayListOf())
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
                categoryViewModel.setSearchText(s.toString())
            }

        })
    }

    private fun setupObserver() {
        categoryViewModel.searchText.observe(this, Observer {
            Log.d(TAG, "setupObserver() called $it")
            it?.let {
                adapter.filter.filter(it)
            }
        })
        categoryViewModel.categories.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "setupObserver() called" + it.status)
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users.categories) }
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

    private fun renderList(users: List<Category>) {
        Log.d(TAG, "renderList() called with: users = $users")
        adapter.addData(users)
        adapter.notifyDataSetChanged()
        binding.editSearch.text?.let {
            adapter.filter.filter(it.toString())
        }
    }

}