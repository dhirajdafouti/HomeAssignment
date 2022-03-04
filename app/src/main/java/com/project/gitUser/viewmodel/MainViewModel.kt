package com.project.gitUser.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.gitUser.model.UserFollower
import com.project.gitUser.repository.GitUserRepository
import com.project.gitUser.utils.GitUserDetailsResult
import com.project.gitUser.utils.GitUserSearchResult
import kotlinx.coroutines.launch

//The View Model class which acts as a interface between the view and Data.
class MainViewModel(private val application: Application) : ViewModel() {

    //The Instance of repository class.

    private val repository = GitUserRepository(application)

     val _navigateToDetailFragment = MutableLiveData<UserFollower>()

    val navigateToDetailFragment: LiveData<UserFollower>
        get() = _navigateToDetailFragment


    val userFollowers: LiveData<GitUserSearchResult>
        get() =
            repository.searchResultToUI

    //live data variable for progress bar.
    val progressBar: LiveData<Boolean>
        get() = repository.progressBar

    val userInfo:LiveData<GitUserDetailsResult>get() = repository._userInfoDetails

    fun shownAsteroidDetail() {
        _navigateToDetailFragment.value = null
    }

    fun setUserFollowerUrl(userData: UserFollower) {
        _navigateToDetailFragment.value = userData
    }

    //search  based on query passed.
    fun searchUserWithMaximumFollowers() {
        viewModelScope.launch {
         repository.getSearchResultStream()

        }
    }

    fun requestUserInfo(url: String?) {
        viewModelScope.launch {
            repository.userInfoDetails(url)
        }

    }

    fun requestRefresh() {
      viewModelScope.launch {
          repository.requestRefreshUserFollowers()
      }
    }
    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
                viewModelScope.launch {
                    repository.requestRefreshUserFollowers()
                }

            }
        }

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }
    }




