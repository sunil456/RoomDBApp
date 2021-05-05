package io.sunil.roomdbapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import io.sunil.roomdbapp.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun getAllData() : LiveData<List<User>>

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUser()

    @Update
    suspend fun updateUserData(user: User)

    @Delete
    suspend fun deleteUser(user: User)


}