package com.fangga.bfaa.bfaa_2.data.local

import androidx.room.*
import com.fangga.bfaa.bfaa_2.data.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user ORDER BY username ASC")
    suspend fun getFavorites(): List<User>

    @Query("SELECT * FROM user WHERE username =:username")
    suspend fun getFavoritedUserDetail(username: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoritedUser(user: User)

    @Delete
    suspend fun deleteFavoritedUser(user: User)
}