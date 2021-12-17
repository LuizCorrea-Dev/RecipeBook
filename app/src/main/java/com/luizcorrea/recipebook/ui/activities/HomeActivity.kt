package com.luizcorrea.recipebook.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.luizcorrea.recipebook.R
import com.luizcorrea.recipebook.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import android.view.View
import androidx.navigation.Navigation

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home)
        binding.favoritesClickListener = View.OnClickListener {
            println("favorites clicked")
            navigateToFavorites()
        }
    }

    private fun navigateToFavorites() {
        val navController = Navigation.findNavController(this, R.id.myNavHostFragment)
        val bundle = Bundle()
        bundle.putString("isFavorites", "Y")
        navController.popBackStack(R.id.filterByTypeFragment, true);
        navController.navigate(R.id.filterByTypeFragment, bundle);
    }

}