package com.luizcorrea.recipebook.ui.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.luizcorrea.recipebook.data.model.db.Category
import com.luizcorrea.recipebook.data.model.db.Meal
import com.luizcorrea.recipebook.databinding.CategoryItemLayoutBinding
import com.luizcorrea.recipebook.databinding.MealItemLayoutBinding
import com.luizcorrea.recipebook.ui.clicklisteners.MealClickListener
import com.luizcorrea.recipebook.ui.fragments.CategoriesFragmentDirections
import com.luizcorrea.recipebook.ui.fragments.FilterByTypeFragmentDirections
import java.util.*

class MealAdapter(
    private val meals: ArrayList<Meal>,
    private val favoriteClickListener: FavoriteClickListener
) : RecyclerView.Adapter<MealAdapter.DataViewHolder>(), Filterable {
    private var filteredList = ArrayList<Meal>()

    init {
        filteredList = meals
    }

    interface FavoriteClickListener {
        fun onFavoriteClick(meal: Meal)
    }

    inner class DataViewHolder(val binding: MealItemLayoutBinding)
        : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.clickListener = View.OnClickListener { view ->
                navigateToMeal(binding.meal?.id, view)
            }
            binding.favoriteClickListener = favoriteClickListener
        }

        private fun navigateToMeal(mealId: Int?, view: View) {
            mealId?.let {
                var action = FilterByTypeFragmentDirections.actionFilterByTypeFragmentToRecipeDetailFragment(mealId = mealId)
                view.findNavController().navigate(action)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MealItemLayoutBinding.inflate(inflater)
        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int = filteredList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.binding.meal = filteredList[position]
        holder.binding.executePendingBindings();
    }

    fun addData(list: List<Meal>) {
        meals.addAll(list)
    }

    fun clearData() {
        meals.clear()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                filteredList = if (constraint.isNullOrEmpty()) {
                    meals
                } else {
                    val list = ArrayList<Meal>()
                    for (character in meals) {
                        if (character.mealName.toLowerCase(Locale.getDefault())
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
                filteredList = results?.values as ArrayList<Meal>
                notifyDataSetChanged()
            }

        }
    }

}