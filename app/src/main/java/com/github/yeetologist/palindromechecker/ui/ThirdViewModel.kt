package com.github.yeetologist.palindromechecker.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.yeetologist.palindromechecker.data.ApiConfig
import com.github.yeetologist.palindromechecker.data.DataItem
import com.github.yeetologist.palindromechecker.data.UserResponse
import com.github.yeetologist.palindromechecker.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdViewModel : ViewModel() {
    private val _resultCount = MutableLiveData<Event<Int>>()
    val resultCount: LiveData<Event<Int>> = _resultCount
    private val _listUsers = MutableLiveData<Event<List<DataItem>>>()
    val listUsers: LiveData<Event<List<DataItem>>> = _listUsers
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers()
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listUsers.value = Event(responseBody.data)
                        _resultCount.value = Event(responseBody.total)
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