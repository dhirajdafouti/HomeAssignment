package com.project.gitUser.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.gitUser.model.UserFollower
import com.project.gitUser.repository.GitUserRepository
import com.project.gitUser.utils.GitUserSearchResult
import kotlinx.coroutines.launch

//The View Model class which acts as a interface between the view and Data.
class MainViewModel(private val application: Application) : ViewModel() {

    //The Instance of repository class.

    private val repository = GitUserRepository(application)

    private val _navigateToDetailFragment = MutableLiveData<UserFollower>()
    val navigateToDetailFragment: LiveData<UserFollower>
        get() = _navigateToDetailFragment


    //live data for the search result from the Single Source of truth.
    private val queryLiveData = MutableLiveData<String>()
    val repoResult: LiveData<GitUserSearchResult>
        get() =
            repository.searchResults

    //live data variable for progress bar.
    val progressBar: LiveData<Boolean>
        get() = repository.progressBar


    fun shownAsteroidDetail() {
        _navigateToDetailFragment.value = null
    }

    fun setUserData(userData: UserFollower) {
        _navigateToDetailFragment.value = userData
    }

    //search  based on query passed.
    fun searchUserWithMaximumFollowers() {
        viewModelScope.launch {
            repository.getSearchResultStream()
        }
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
                viewModelScope.launch {
                    repository.requestMore()

            }
        }
    }

    companion object {
        private const val VISIBLE_THRESHOLD = 2
    }

}