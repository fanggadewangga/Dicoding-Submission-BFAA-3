package com.fangga.bfaa.bfaa_2.presentation.ui.follower

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fangga.bfaa.bfaa_2.base.BaseFragment
import com.fangga.bfaa.bfaa_2.data.Resource
import com.fangga.bfaa.bfaa_2.data.model.User
import com.fangga.bfaa.bfaa_2.databinding.FragmentFollowerBinding
import com.fangga.bfaa.bfaa_2.presentation.adapter.UserAdapter
import com.fangga.bfaa.bfaa_2.utils.ScreenOrientation
import com.fangga.bfaa.bfaa_2.utils.ViewStateCallback

class FollowerFragment : BaseFragment<FragmentFollowerBinding>()  {

    companion object {
        private const val KEY_BUNDLE = "USERNAME"

        fun getInstance(username: String): Fragment {
            return FollowerFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_BUNDLE, username)
                }
            }
        }
    }

    private lateinit var viewModel: FollowerViewModel
    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(KEY_BUNDLE)
        }
    }

    override fun inflateViewBinding(container: ViewGroup?): FragmentFollowerBinding =
        FragmentFollowerBinding.inflate(layoutInflater, container, false)

    override fun FragmentFollowerBinding.binder() {
        viewModel = ViewModelProvider(this@FollowerFragment)[FollowerViewModel::class.java]
        val rvAdapter = UserAdapter()

        val callback = object: ViewStateCallback<List<User>> {
            override fun onLoading() {
                followerProgressBar.visibility = visible
            }

            override fun onSuccess(data: List<User>) {
                rvAdapter.submitData(data)
                rvFollower.visibility = visible
                followerProgressBar.visibility = invisible
            }

            override fun onError(message: String?) {
                followerProgressBar.visibility = invisible
                ivSearchFail.visibility = visible
                tvFind.visibility = visible
            }

        }

        rvFollower.apply {
            adapter = rvAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        viewModel.getUserFollowers(username = username.toString()).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> callback.onError(it.message)
                is Resource.Loading -> callback.onLoading()
                is Resource.Success -> it.data?.let { it1 -> callback.onSuccess(it1) }
            }
        }
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }
}