package com.fangga.bfaa.bfaa_2.presentation.ui.favorite

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fangga.bfaa.bfaa_2.base.BaseActivity
import com.fangga.bfaa.bfaa_2.data.Resource
import com.fangga.bfaa.bfaa_2.data.model.User
import com.fangga.bfaa.bfaa_2.databinding.ActivityFavoriteBinding
import com.fangga.bfaa.bfaa_2.presentation.adapter.UserAdapter
import com.fangga.bfaa.bfaa_2.utils.ScreenOrientation
import com.fangga.bfaa.bfaa_2.utils.ViewStateCallback

@Suppress("DEPRECATION")
class FavoriteActivity : BaseActivity<ActivityFavoriteBinding>() {
    private lateinit var viewModel: FavoriteViewModel
    private val rvAdapter = UserAdapter()

    override fun inflateViewBinding(): ActivityFavoriteBinding {
        return ActivityFavoriteBinding.inflate(layoutInflater)
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }

    override fun ActivityFavoriteBinding.binder() {
        viewModel = ViewModelProvider(this@FavoriteActivity)[FavoriteViewModel::class.java]

        rvFavorite.apply {
            adapter = rvAdapter
            layoutManager =
                LinearLayoutManager(this@FavoriteActivity, LinearLayoutManager.VERTICAL, false)
        }

        viewModel.getFavorites().observe(this@FavoriteActivity) {
            when (it) {
                is Resource.Error -> callback.onError(it.message)
                is Resource.Loading -> callback.onLoading()
                is Resource.Success -> it.data?.let { it1 -> callback.onSuccess(it1) }
            }
        }

        supportActionBar?.hide()
    }

    private val callback = object : ViewStateCallback<List<User>> {
        override fun onLoading() {
            binding.apply {
                favoriteProgressBar.visibility = visible
                rvFavorite.visibility = invisible
                ivEmpty.visibility = invisible
                tvEmpty.visibility = invisible
            }
        }

        override fun onSuccess(data: List<User>) {
            binding.apply {
                rvAdapter.submitData(data)
                rvFavorite.visibility = visible
                ivEmpty.visibility = invisible
                tvEmpty.visibility = invisible
                favoriteProgressBar.visibility = invisible
            }
        }

        override fun onError(message: String?) {
            binding.apply {
                favoriteProgressBar.visibility = invisible
                rvFavorite.visibility = invisible
                ivEmpty.visibility = visible
                tvEmpty.visibility = visible
            }
        }

    }

    override fun onResume() {
        viewModel = ViewModelProvider(this@FavoriteActivity)[FavoriteViewModel::class.java]

        viewModel.getFavorites().observe(this@FavoriteActivity) {
            when (it) {
                is Resource.Error -> callback.onError(it.message)
                is Resource.Loading -> callback.onLoading()
                is Resource.Success -> it.data?.let { it1 -> callback.onSuccess(it1) }
            }
        }
        super.onResume()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}