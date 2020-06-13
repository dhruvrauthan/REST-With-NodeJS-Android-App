package com.example.restapiwithnodejs.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.restapiwithnodejs.R
import com.example.restapiwithnodejs.RetrofitInterface
import com.example.restapiwithnodejs.models.User
import kotlinx.android.synthetic.main.fragment_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginFragment : Fragment() {

    //retrofit
    private lateinit var mRetrofit: Retrofit
    private lateinit var mRetrofitInterface: RetrofitInterface
    private val mBaseUrl = "http://10.0.2.2:3000"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRetrofit()

        login_user_button.setOnClickListener {

            val map = HashMap<String, String>()

            map["email"] = login_email_edittext.text.toString()
            map["password"] = login_password_edittext.text.toString()

            val call: Call<User> = mRetrofitInterface.loginUser(map)

            call.enqueue(object : Callback<User> {

                override fun onFailure(call: Call<User>, t: Throwable) {

                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()

                }

                override fun onResponse(call: Call<User>, response: Response<User>) {

                    if (response.code() == 200) {

                        val user: User = response.body()!!

                        Toast.makeText(
                            requireContext(),
                            "Name: " + user.name,
                            Toast.LENGTH_SHORT
                        ).show()

                    } else if (response.code() == 404) {

                        Toast.makeText(requireContext(), "Wrong credentials!", Toast.LENGTH_SHORT)
                            .show()

                    }

                }


            })

        }

    }

    private fun initRetrofit() {

        mRetrofit =
            Retrofit.Builder().baseUrl(mBaseUrl).addConverterFactory(GsonConverterFactory.create())
                .build()

        mRetrofitInterface = mRetrofit.create(RetrofitInterface::class.java)

    }

}