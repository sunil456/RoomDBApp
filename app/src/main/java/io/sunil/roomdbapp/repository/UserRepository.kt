package io.sunil.roomdbapp.repository

import androidx.lifecycle.LiveData
import io.sunil.roomdbapp.model.User
import io.sunil.roomdbapp.data.UserDao

class UserRepository(private val userDao: UserDao) {

    val readAllData: LiveData<List<User>> = userDao.getAllData()
//    suspend fun getAllUserData(): LiveData<List<User>> = userDao.getAllData()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    suspend fun updateUser(user: User){
        userDao.updateUserData(user)
    }

    suspend fun deleteUser(user: User){
        userDao.deleteUser(user)
    }

    suspend fun deleteAllUser() = userDao.deleteAllUser()
}