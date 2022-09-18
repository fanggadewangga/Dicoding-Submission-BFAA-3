package com.fangga.bfaa.bfaa_2.presentation.ui.detail

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fangga.bfaa.bfaa_2.base.BaseActivity
import com.fangga.bfaa.bfaa_2.data.Resource
import com.fangga.bfaa.bfaa_2.data.model.User
import com.fangga.bfaa.bfaa_2.databinding.ActivityDetailBinding
import com.fangga.bfaa.bfaa_2.presentation.adapter.FollowPagerAdapter
import com.fangga.bfaa.bfaa_2.utils.Constant.EXTRA_USER
import com.fangga.bfaa.bfaa_2.utils.Constant.TAB_TITLES
import com.fangga.bfaa.bfaa_2.utils.ScreenOrientation
import com.fangga.bfaa.bfaa_2.utils.ViewStateCallback
import com.google.android.material.tabs.TabLayoutMediator

@Suppress("DEPRECATION")
class DetailActivity : BaseActivity<ActivityDetailBinding>() {
    private lateinit var viewModel: DetailViewModel

    override fun inflateViewBinding(): ActivityDetailBinding {
        return ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }

    @SuppressLint("SetTextI18n")
    override fun ActivityDetailBinding.binder() {
        val callback = object: ViewStateCallback<User> {
            override fun onLoading() {
                makeInvisible()
                progressBar.visibility = visible
            }

            override fun onSuccess(data: User) {
                if (data.company == null) {
                    ivCompany.visibility = View.GONE
                    tvCompany.visibility = View.GONE
                }

                if (data.location == null) {
                    ivLocation.visibility = View.GONE
                    tvLocation.visibility = View.GONE
                }

                tvUsername.text = data.username
                tvName.text = data.name
                tvCompany.text = data.company
                tvLocation.text = data.location
                tvRepository.text = "${data.repository} Repositories"
                tvFollower.text = "${data.follower} Followers"
                tvFollowing.text = "${data.following} Following"

                Glide.with(this@DetailActivity)
                    .load(data.avatar)
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivUserAvatar)

                ivCantLoad.visibility = invisible
                tvCantLoad.visibility = invisible
                progressBar.visibility = invisible
            }

            override fun onError(message: String?) {
                makeInvisible()
                progressBar.visibility = invisible
            }

            fun makeInvisible(){
                tvUsername.visibility = View.INVISIBLE
                tvName.visibility = View.INVISIBLE
                tvCompany.visibility = View.INVISIBLE
                tvLocation.visibility = View.INVISIBLE
                tvRepository.visibility = View.INVISIBLE
                tvFollower.visibility = View.INVISIBLE
                tvFollowing.visibility = View.INVISIBLE
                ivCompany.visibility = View.GONE
                ivLocation.visibility = View.GONE
                ivUserAvatar.visibility = View.GONE
                ivFollower.visibility = View.GONE
                ivFollowing.visibility = View.GONE
                ivRepository.visibility = View.GONE
                ivCantLoad.visibility = View.INVISIBLE
                tvCantLoad.visibility = View.INVISIBLE
                tabs.visibility = invisible
            }
        }

        viewModel = ViewModelProvider(this@DetailActivity)[DetailViewModel::class.java]

        val username = intent.getStringExtra(EXTRA_USER)

        viewModel.getUserDetail(username).observe(this@DetailActivity) {
            when (it) {
                is Resource.Error -> callback.onError(it.message)
                is Resource.Loading -> callback.onLoading()
                is Resource.Success -> it.data?.let { it1 -> callback.onSuccess(it1) }
            }
        }

        viewPager.adapter = FollowPagerAdapter(this@DetailActivity, username.toString())
        TabLayoutMediator(tabs, viewPager) { tabs, position ->
            tabs.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = username
            elevation = 0f
        }
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