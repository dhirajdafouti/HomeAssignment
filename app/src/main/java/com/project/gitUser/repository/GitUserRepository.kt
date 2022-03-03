package com.project.gitUser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.gitUser.constants.Constants.NETWORK_PAGE_SIZE
import com.project.gitUser.database.getDataBase
import com.project.gitUser.model.UserFollower
import com.project.gitUser.network.remotemodel.NetworkGitUserSearchDataObject
import com.project.gitUser.network.remotemodel.asDatabaseModel
import com.project.gitUser.network.service.GitUserService
import com.project.gitUser.utils.GitUserSearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class GitUserRepository(private val application: Application) {
    val searchResults = MutableLiveData<GitUserSearchResult>()
    private val dataBase = getDataBase(application)

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = GITHUB_STARTING_PAGE_INDEX

    private val _progressBar: MutableLiveData<Boolean> = MutableLiveData(false)
    val progressBar: LiveData<Boolean> get() = _progressBar

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    //The Suspend method will request for Search data to the Network.
    suspend fun getSearchResultStream(): MutableLiveData<GitUserSearchResult> {
        _progressBar.value = true
        withContext(Dispatchers.IO) {
            deleteOlderDataBaseTables()
            requestAndSaveToDataBase()
        }
        _progressBar.value = false
        return searchResults
    }

    //Method will delete all the tables before fetching the new queries.
    private suspend fun deleteOlderDataBaseTables() {
        dataBase.gitUserDao.deleteUserFollowers()
    }

    private suspend fun requestAndSaveToDataBase(): Boolean {
        var successful = false
        isRequestInProgress = true
        try {
            val response = GitUserService.retrofitApiService.searchUserFollowers(
                lastRequestedPage,
                NETWORK_PAGE_SIZE
            )
            val repos = response.items ?: emptyList()
            dataBase.gitUserDao.insertUserFollowers(NetworkGitUserSearchDataObject(repos).asDatabaseModel())
            val data = dataBase.gitUserDao.queryUserFollowers()

            var listOfVehicleNames: List<UserFollower> = emptyList()
            listOfVehicleNames = data.map {
                UserFollower(
                    id = it.id,
                    loginName = it.loginName,
                    url = it.url,
                    profileUrl = it.avatar_url
                )
            }
            searchResults.postValue(GitUserSearchResult.Success(listOfVehicleNames))
            successful = true
        } catch (exception: IOException) {
            searchResults.postValue(GitUserSearchResult.Error(exception))
        } catch (exception: HttpException) {
            searchResults.postValue(GitUserSearchResult.Error(exception))

        }
        isRequestInProgress = false
        return successful
    }


    suspend fun requestMore() {
        if (isRequestInProgress) return
        val successful = requestAndSaveToDataBase()
        if (successful) {
            lastRequestedPage++
        }
    }

    companion object {
        const val GITHUB_STARTING_PAGE_INDEX = 1
    }

}