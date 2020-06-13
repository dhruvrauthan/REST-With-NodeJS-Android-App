package com.example.restapiwithnodejs.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.restapiwithnodejs.R
import com.example.restapiwithnodejs.RetrofitInterface
import kotlinx.android.synthetic.main.fragment_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterFragment : Fragment() {

    //retrofit
    private lateinit var mRetrofit: Retrofit
    private lateinit var mRetrofitInterface: RetrofitInterface
    private val mBaseUrl = "http://10.0.2.2:3000"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRetrofit()

        register_user_button.setOnClickListener {

            val map = HashMap<String, String>()

            map["name"] = register_name_edittext.text.toString()
            map["email"] = register_email_edittext.text.toString()
            map["password"] = register_password_edittext.text.toString()

            val call: Call<Void> = mRetrofitInterface.registerUser(map)

            call.enqueue(object : Callback<Void> {

                override fun onFailure(call: Call<Void>, t: Throwable) {

                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()

                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {

                    if (response.code() == 200) {

                        Toast.makeText(
                            requireContext(),
                            "Signed up successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else if (response.code() == 400) {

                        Toast.makeText(requireContext(), "User already exists!", Toast.LENGTH_SHORT)
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