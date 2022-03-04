package com.project.gitUser.fragment.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.project.gitUser.databinding.FragmentDetailsBinding
import com.project.gitUser.model.UserFollower
import com.project.gitUser.utils.GitUserDetailsResult
import com.project.gitUser.viewmodel.MainViewModel
import com.project.gitUser.viewmodel.ViewModelFactory


class DetailsFragment : Fragment() {

    private lateinit var binding:FragmentDetailsBinding
    //View Model InStance
    private val viewModel: MainViewModel by lazy {
        val activity = requireActivity().application
        val viewModelFactory = ViewModelFactory(activity)
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigateToDetailFragment.observe(this,Observer<UserFollower>{
            viewModel.requestUserInfo(it.loginName?:"")
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel =viewModel
        binding.openWeb.setOnClickListener {
                viewModel._navigateToDetailFragment.value?.url.apply {
                    val intent = android.content.Intent(
                        android.content.Intent.ACTION_VIEW,
                        android.net.Uri.parse(viewModel._navigateToDetailFragment.value?.url)
                    )
                    context?.startActivity(intent)
                }

        }
        viewModel.progressBar.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.statusLoadingWheel.visibility = View.VISIBLE
            } else {
                binding.statusLoadingWheel.visibility = View.GONE
            }
        })
        initView()
        return binding.root
    }


    private fun initView() {
        viewModel.userInfo.observe(viewLifecycleOwner, Observer {
            when (it) {
                is GitUserDetailsResult.Success -> {
                    System.out.println("Dhiraj"+it.data.location)
                    binding.langaugeTextValue.text = it.data.location
                    binding.repoLastUpdateValue.text=it.data.updated_at
                    viewModel.shownAsteroidDetail()
                }
                is GitUserDetailsResult.Error -> {
                 //TODO:The Error has to be logged.
                    viewModel.shownAsteroidDetail()

                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
