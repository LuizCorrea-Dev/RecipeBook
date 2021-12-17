package com.luizcorrea.recipebook.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.luizcorrea.recipebook.data.model.db.Category
import com.luizcorrea.recipebook.databinding.CategoryItemLayoutBinding
import com.luizcorrea.recipebook.ui.fragments.CategoriesFragmentDirections
import java.util.*

class CategoryAdapter(
    private val categories: ArrayList<Category>
) : RecyclerView.Adapter<CategoryAdapter.DataViewHolder>(), Filterable {
    private var filteredList = ArrayList<Category>()

    init {
        filteredList = categories
    }

    inner class DataViewHolder(val binding: CategoryItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener { view ->
                navigateToCategory(binding.categoryId, view)
            }
        }

        private fun navigateToCategory(categoryId: String?, view: View) {
            var action =
                CategoriesFragmentDirections.actionCategoriesFragmentToFilterByTypeFragment(category = categoryId)
            view.findNavController().navigate(action)
        }

        fun bind(category: Category) {
            with(binding) {
                categoryId = category.categoryName
                textViewTitle.text = category.categoryName
                //textViewDescription.text = category.description
                imageViewThumbNail.load(category.thumbnail)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CategoryItemLayoutBinding.inflate(inflater)
        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int = filteredList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(filteredList[position])

    fun addData(list: List<Category>) {
        categories.addAll(list)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                filteredList = if (constraint.isNullOrEmpty()) {
                    categories
                } else {
                    val list = ArrayList<Category>()
                    for (character in categories) {
                        if (character.categoryName.toLowerCase(Locale.getDefault())
                                .contains(constraint.toString().toLowerCase(Locale.getDefault()))
                        ) {
                            list.add(character)
                        }
                    }
                    list
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as ArrayList<Category>
                notifyDataSetChanged()
            }

        }
    }

}