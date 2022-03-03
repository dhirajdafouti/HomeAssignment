package com.project.gitUser.fragment.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.gitUser.databinding.FragmentMainBinding
import com.project.gitUser.fragment.adapters.ReposAdapter
import com.project.gitUser.utils.GitUserSearchResult
import com.project.gitUser.viewmodel.MainViewModel
import com.project.gitUser.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_main.*
import timber.log.Timber


class MainFragment : Fragment() {
    //Instance of View Binding
    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: ReposAdapter

    //View Model InStance
    private val viewModel: MainViewModel by lazy {
        val activity = requireActivity().application
        val viewModelFactory = ViewModelFactory(activity)
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel
        adapter = ReposAdapter(
            ReposAdapter.RepoAdapterOnClickListener { userData ->
                viewModel.setUserData(userData)
            }
        )
        viewModel.progressBar.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.statusLoadingWheel.visibility = View.VISIBLE
            } else {
                binding.statusLoadingWheel.visibility = View.GONE
            }
        })
        binding.list.layoutManager= GridLayoutManager(this.context, 2, RecyclerView.VERTICAL, false)
        // add dividers between RecyclerView's row items
        val decoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(decoration)
        setupScrollListener()
        initAdapter()
        viewModel.searchUserWithMaximumFollowers()
        viewModel.navigateToDetailFragment.observe(viewLifecycleOwner, Observer {
//            it?.let{
//                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
//                viewModel.shownAsteroidDetail()
//            }
        })
        binding.swipeRefreshLayout.setOnRefreshListener {
            showEmptyList(true)
            viewModel.searchUserWithMaximumFollowers()
            binding.list.adapter!!.notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing=false
        }
        return binding.root
    }
    /**
     * Initialize the Adapter.
     */
    @SuppressLint("TimberArgCount")
    private fun initAdapter() {
        binding.list.adapter = adapter
        viewModel.repoResult.observe(viewLifecycleOwner, Observer {
            when (it) {
                is GitUserSearchResult.Success -> {
                    showEmptyList(it.data.isEmpty())
                    adapter.submitList(it.data)
                }
                is GitUserSearchResult.Error -> {
                    Timber.d("Error Received",{it.error})
                    Toast.makeText(
                        activity,
                        "No Internet or Server Connection Error!!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }


    private fun showEmptyList(show: Boolean) {
        if (show) {
            binding.emptyList.visibility = View.VISIBLE
            binding.list.visibility = View.GONE
        } else {
            binding.emptyList.visibility = View.GONE
            binding.list.visibility = View.VISIBLE
        }
    }

    private fun setupScrollListener() {
        val layoutManager = binding.list.layoutManager as GridLayoutManager
        binding.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                viewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })
    }

}


