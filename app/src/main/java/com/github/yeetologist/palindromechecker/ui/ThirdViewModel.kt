package com.github.yeetologist.palindromechecker.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.yeetologist.palindromechecker.data.ApiConfig
import com.github.yeetologist.palindromechecker.data.DataItem
import com.github.yeetologist.palindromechecker.data.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdViewModel : ViewModel() {
    private val _resultCount = MutableLiveData<Int>()
    val resultCount: LiveData<Int> = _resultCount
    private val _listUsersBefore = MutableLiveData<List<DataItem>>()
    private val _listUsers = MutableLiveData<List<DataItem>>()
    val listUsers: LiveData<List<DataItem>> = _listUsers
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(page: Int = 1) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers(page = page)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if (responseBody.data.size == 10) {
                            _listUsers.value = responseBody.data
                            _resultCount.value = responseBody.total
                        } else {
                            _listUsers.value?.let {
                                _listUsers.value = it + responseBody.data
                                val templateUser = ArrayList<DataItem>()
                                for (i in 1..(10 - responseBody.data.size)) {
                                    templateUser.add(
                                        DataItem(
                                            id = (10..1000000).random(),
                                            lastName = "Lastname",
                                            firstName = "Firstname",
                                            avatar = "https://reqres.in/img/faces/3-image.jpg",
                                            email = "EMAIL@EMAIL.COM"
                                        )
                                    )
                                }
                                _listUsers.value = it + templateUser
                            }
                        }
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "MainViewModel"
    }

}