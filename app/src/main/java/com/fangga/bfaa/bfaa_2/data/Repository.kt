package com.fangga.bfaa.bfaa_2.data

import android.app.Application
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
        }.flowOn(Dispatchers.IO)

    fun getUserDetail(username: String?) = flow{
        try {
            val result =
                if (username?.let { dao.getFavoritedUserDetail(it) } != null) {
                    username.let { dao.getFavoritedUserDetail(it) }
                } else {
                    retrofit.getUserDetail(username.toString())
                }
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)

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
        }.flowOn(Dispatchers.IO)

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
        }.flowOn(Dispatchers.IO)

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
        }.flowOn(Dispatchers.IO)

    suspend fun insertFavoritedUser(user: User) = dao.insertFavoritedUser(user)

    suspend fun deleteFavoritedUser(user: User) = dao.deleteFavoritedUser(user)

    suspend fun saveTheme(isLightModeActive: Boolean) =
        dataStore.saveThemeSetting(isLightModeActive)

    fun getTheme() = dataStore.getThemeSetting()
}