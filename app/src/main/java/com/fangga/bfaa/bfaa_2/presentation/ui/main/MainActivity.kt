package com.fangga.bfaa.bfaa_2.presentation.ui.main

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fangga.bfaa.bfaa_2.R
import com.fangga.bfaa.bfaa_2.base.BaseActivity
import com.fangga.bfaa.bfaa_2.data.Resource
import com.fangga.bfaa.bfaa_2.data.model.User
import com.fangga.bfaa.bfaa_2.databinding.ActivityMainBinding
import com.fangga.bfaa.bfaa_2.presentation.adapter.UserAdapter
import com.fangga.bfaa.bfaa_2.utils.ScreenOrientation
import com.fangga.bfaa.bfaa_2.utils.ViewStateCallback

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var viewModel: MainViewModel
    private lateinit var userQuery: String

    override fun inflateViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }

    override fun ActivityMainBinding.binder() {
        val rvAdapter = UserAdapter()
        viewModel = ViewModelProvider(this@MainActivity)[MainViewModel::class.java]

        val callback = object: ViewStateCallback<List<User>> {
            override fun onLoading() {
                includeMain.apply {
                    rvListUser.visibility = invisible
                    tvFind.visibility = invisible
                    ivFind.visibility = invisible
                    progressBar.visibility = visible
                }
            }

            override fun onSuccess(data: List<User>) {
                rvAdapter.submitData(data)
                includeMain.apply {
                    rvListUser.visibility = visible
                    tvFind.visibility = invisible
                    ivFind.visibility = invisible
                    progressBar.visibility = invisible
                }
            }

            override fun onError(message: String?) {
                includeMain.apply {
                    rvListUser.visibility = invisible
                    tvFind.visibility = visible
                    tvFind.text = getString(R.string.search_cant_find)
                    ivFind.visibility = visible
                    ivFind.setImageResource(R.drawable.search_fail)
                    progressBar.visibility = invisible
                }
            }
        }

        includeMain.rvListUser.apply {
            adapter = rvAdapter
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        }

        searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    userQuery = query.toString()
                    clearFocus()
                    viewModel.searchUsers(query = userQuery).observe(this@MainActivity) {
                        when (it) {
                            is Resource.Error -> callback.onError(it.message)
                            is Resource.Loading -> callback.onLoading()
                            is Resource.Success -> it.data?.let { it1 -> callback.onSuccess(it1) }
                        }
                    }
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
        supportActionBar?.hide()
    }
}