package com.fangga.bfaa.bfaa_2.data

import android.app.Application
import androidx.lifecycle.asLiveData
import com.fangga.bfaa.bfaa_2.data.datastore.UserDataStore
import com.fangga.bfaa.bfaa_2.data.local.UserDao
import com.fangga.bfaa.bfaa_2.data.local.UserDatabase
import com.fangga.bfaa.bfaa_2.data.model.User
import com.fangga.bfaa.bfaa_2.data.remote.ApiService
import com.fangga.bfaa.bfaa_2.data.remote.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class Repository(application: Application) {

    private val dao: UserDao
    private val retrofit: ApiService = RetrofitInstance.create()
    private val dataStore: UserDataStore

    init {
        val database: UserDatabase = UserDatabase.getInstance(application)
        dao = database.userDao()
        dataStore = UserDataStore.getInstance(application)
    }

    fun searchUsers(query: String) =
        flow {
            emit(Resource.Loading())
            try {
                val list = retrofit.searchUser(query).items
                if (list.isEmpty())
                    emit(Resource.Error(null))
                else {
                    emit(Resource.Success(list))
                }

            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage))
            }
        }.flowOn(Dispatchers.IO).asLiveData()

    fun getUserDetail(username: String?) = flow{
        try {
            val result = retrofit.getUserDetail(username.toString())
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO).asLiveData()

    fun getUserFollowers(username: String) =
        flow {
            emit(Resource.Loading())
            try {
                val list = retrofit.getUserFollowers(username)
                if (list.isEmpty())
                    emit(Resource.Error(null))
                else {
                    emit(Resource.Success(list))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage))
            }
        }.flowOn(Dispatchers.IO).asLiveData()

    fun getUserFollowing(username: String) =
        flow {
            emit(Resource.Loading())
            try {
                val list = retrofit.getUserFollowings(username)
                if (list.isEmpty())
                    emit(Resource.Error(null))
                else {
                    emit(Resource.Success(list))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage))
            }
        }.flowOn(Dispatchers.IO).asLiveData()

    fun getFavorites() =
        flow {
            emit(Resource.Loading())
            try {
                val list = dao.getFavorites()
                if (list.isEmpty())
                    emit(Resource.Error(null))
                else
                    emit(Resource.Success(list))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage))
            }
        }.flowOn(Dispatchers.IO).asLiveData()

    suspend fun insertFavoritedUser(user: User) = dao.insertFavoritedUser(user)

    suspend fun deleteFavoritedUser(user: User) = dao.deleteFavoritedUser(user)

    suspend fun getTheme() = dataStore.getThemeSetting()
}