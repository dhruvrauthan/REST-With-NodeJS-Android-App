package com.example.restapiwithnodejs

import com.example.restapiwithnodejs.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitInterface {

    @POST("/login")
    fun loginUser(@Body map: HashMap<String,String>): Call<User>

    @POST("/register")
    fun registerUser(@Body map: HashMap<String, String>): Call<Void>

}