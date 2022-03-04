package com.project.gitUser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.gitUser.constants.Constants.NETWORK_PAGE_SIZE
import com.project.gitUser.database.getDataBase
import com.project.gitUser.model.UserFollower
import com.project.gitUser.model.UserInfo
import com.project.gitUser.model.asDatabaseModel
import com.project.gitUser.network.remotemodel.NetworkGitUserSearchDataObject
import com.project.gitUser.network.remotemodel.asDatabaseModel
import com.project.gitUser.network.service.GitUserService
import com.project.gitUser.utils.GitUserDetailsResult
import com.project.gitUser.utils.GitUserSearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class GitUserRepository(private val application: Application) {
    val _searchResults = MutableLiveData<GitUserSearchResult>()

    val _userInfoDetails = MutableLiveData<GitUserDetailsResult>()


    val searchResultToUI: LiveData<GitUserSearchResult>
        get() =
            _searchResults

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
        return _searchResults
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
            _searchResults.postValue(GitUserSearchResult.Success(listOfVehicleNames))
            successful = true
        } catch (exception: IOException) {
            _searchResults.postValue(GitUserSearchResult.Error(exception))
        } catch (exception: HttpException) {
            _searchResults.postValue(GitUserSearchResult.Error(exception))

        }
        isRequestInProgress = false
        return successful
    }


    suspend fun requestRefreshUserFollowers() {
        if (isRequestInProgress) return
        val successful = requestAndSaveToDataBase()
        if (successful) {
            lastRequestedPage++
        }
    }

    suspend fun userInfoDetails(url: String?) {
        _progressBar.value = true
        withContext(Dispatchers.IO) {
            userinfoDetailsAndSaveToDataBase(url)
        }
        _progressBar.value = false
    }

    private suspend fun userinfoDetailsAndSaveToDataBase(url: String?): Boolean {
        var successful = false
        isRequestInProgress = true

        try {
            val response = GitUserService.retrofitApiService.userInfo(url)
            dataBase.gitUserDao.insertUserInfo(
                com.project.gitUser.model.NetworkGitUserSearchDataObject(response).asDatabaseModel()
            )

            val data = dataBase.gitUserDao.queryUserDetails()

            val listOfVehicleNames = UserInfo(
                company = data.company,
                created_at = data.created_at,
                email = data.email,
                id = data.id,
                location = data.location,
                login = data.login,
                name = data.name,
                type = data.type,
                updated_at = data.updated_at,
                url = data.url
            )
            _userInfoDetails.postValue(GitUserDetailsResult.Success(listOfVehicleNames))
            System.out.println("Dhiraj"+_userInfoDetails.value.toString())
            successful = true
        } catch (exception: IOException) {
            _userInfoDetails.postValue(GitUserDetailsResult.Error(exception))
        } catch (exception: HttpException) {
            _userInfoDetails.postValue(GitUserDetailsResult.Error(exception))

        }
        isRequestInProgress = false
        return successful
    }

    companion object {
        const val GITHUB_STARTING_PAGE_INDEX = 1
    }

}