package com.example.dicoding_restaurant_review_course.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.dicoding_restaurant_review_course.ApiConfig
import com.example.dicoding_restaurant_review_course.ReviewAdapter
import com.example.dicoding_restaurant_review_course.data.response.CustomerReviewsItem
import com.example.dicoding_restaurant_review_course.data.response.PostReviewResponse
import com.example.dicoding_restaurant_review_course.data.response.Restaurant
import com.example.dicoding_restaurant_review_course.data.response.RestaurantResponse
import com.example.dicoding_restaurant_review_course.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    companion object{
        private const val TAG = "MainActivity"
        private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


        mainViewModel.restaurant.observe(this) {
            x ->
            setRestaurantData(x)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvReview.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)

        mainViewModel.listReview.observe(this) {
            x -> setReviewData(x)
        }

        mainViewModel.isLoading.observe(this) {
            x -> showLoading(x)
        }

        mainViewModel.snackbarText.observe(this, {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        })
        binding.btnSend.setOnClickListener{ view ->
            mainViewModel.postReview(binding.edReview.text.toString())
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

    }



    private fun setReviewData(items: List<CustomerReviewsItem?>?) {
        val adapter = ReviewAdapter()
        adapter.submitList(items)
        binding.rvReview.adapter = adapter
        binding.edReview.setText("")
    }

    private fun setRestaurantData(restaurant: Restaurant?) {
        binding.tvTitle.text = restaurant?.name
        binding.tvDescription.text = restaurant?.description
        Glide.with(this@MainActivity)
            .load("https://restaurant-api.dicoding.dev/images/large/15")
            .into(binding.ivPicture)


    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


}