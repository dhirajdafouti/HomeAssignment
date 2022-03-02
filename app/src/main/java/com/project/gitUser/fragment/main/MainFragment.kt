package com.project.gitUser.fragment.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.gitUser.databinding.FragmentMainBinding
import com.project.gitUser.fragment.adapters.ReposAdapter
import com.project.gitUser.utils.GitUserSearchResult
import com.project.gitUser.viewmodel.MainViewModel
import com.project.gitUser.viewmodel.ViewModelFactory
import timber.log.Timber


class MainFragment : Fragment() {
    //Instance of View Binding
    private lateinit var binding: FragmentMainBinding
    //View Model InStance
    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity).application
        val viewModelFactory = ViewModelFactory(activity)
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    lateinit var adapter: ReposAdapter
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
        // add dividers between RecyclerView's row items
        val decoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(decoration)
        setupScrollListener()
        initAdapter()
        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        if (viewModel.repoResult.value == null) {
            viewModel.searchGitRepository(query)
        }
        viewModel.navigateToDetailFragment.observe(viewLifecycleOwner, Observer {
            it?.let{
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.shownAsteroidDetail()
            }
        })
        initSearch(query)
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY, binding.searchRepo.text.trim().toString())
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

    private fun initSearch(query: String) {
        binding.searchRepo.setText(query)

        binding.searchRepo.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
        binding.searchRepo.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
    }

    private fun updateRepoListFromInput() {
        binding.searchRepo.text.trim().let {
            if (it.isNotEmpty()) {
                binding.list.scrollToPosition(0)
                viewModel.searchGitRepository(it.toString())
            }
        }
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
        val layoutManager = binding.list.layoutManager as LinearLayoutManager
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

    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = "Android"
    }
}

