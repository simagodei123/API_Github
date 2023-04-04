package com.example.GitHubUsers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.GitHubUsers.adapter.PrimaryAdapter
import com.example.GitHubUsers.databinding.FragmentListBinding
import com.example.GitHubUsers.detail.DetailViewModel

class listFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var detailViewModel: DetailViewModel

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var position = 0
        var username = arguments?.getString(ARG_USERNAME)

        Log.d("arguments: position", position.toString())
        Log.d("arguments: username", username.toString())

        detailViewModel =
            ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(
                DetailViewModel::class.java
            )
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        if (position == 1) {
            showLoading(true)
            username?.let { detailViewModel.getFollowers(it) }
            detailViewModel.followers.observe(viewLifecycleOwner) {
                setFollowData(it)
                showLoading(false)
            }
        }
        else {
            showLoading(true)
            username?.let { detailViewModel.getFollowing(it) }
            detailViewModel.following.observe(viewLifecycleOwner) {
                setFollowData(it)
                showLoading(false)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.pbFollowLoading.visibility = View.VISIBLE
        }
        else {
            binding.pbFollowLoading.visibility = View.GONE
        }
    }

    private fun setFollowData(listFollow: List<ItemsItem>) {
        binding.apply {
            binding.rvFollow.layoutManager = LinearLayoutManager(requireActivity())
            val adapter = PrimaryAdapter(listFollow)
            binding.rvFollow.adapter = adapter
        }
    }
}