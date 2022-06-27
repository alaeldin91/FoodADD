package com.example.foodapp.activites

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.foodapp.R
import com.example.foodapp.helper.PreferencesHelper
import com.example.foodapp.pojo.RegisterModel
import com.example.foodapp.retrofit.AuthApi
import com.example.foodapp.retrofit.RetrofitInstance
import com.example.foodapp.retrofit.RetrofitInstances
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_verfication_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class VerficationRegister : AppCompatActivity() {
    companion object {
        const val RC_SIGN_IN = 123

    }

    private lateinit var firebaseauth: FirebaseAuth
    private lateinit var phoneNumber: String
    private lateinit var sharedPreferences: PreferencesHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verfication_register)
        firebaseauth = FirebaseAuth.getInstance()
        sharedPreferences = PreferencesHelper(this)
        val isUserSignIn = firebaseauth.currentUser != null
        if (!isUserSignIn) {
            sigin()
        } else {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }

    private fun sigin() {
        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(
                Arrays.asList(
                    AuthUI.IdpConfig.PhoneBuilder().build()
                )
            ).build(), RC_SIGN_IN
        )

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        handleSignInResponse(resultCode, data)
    }

    private fun handleSignInResponse(resultCode: Int, data: Intent?) {
        val response = IdpResponse.fromResultIntent(data)
        when {
            resultCode == Activity.RESULT_OK -> {
                showSnackbar("تم تسجيل الدخول بنجاح")
                sigup()
            }
            response == null -> {
                showSnackbar("الغاء الدخول")
                return
            }
            response.error?.errorCode == ErrorCodes.NO_NETWORK -> {
                showSnackbar("خطا في الشبكة")
                return
            }
            response.error?.errorCode == ErrorCodes.UNKNOWN_ERROR -> {
                showSnackbar("خطا غير معروف")
                return
            }
            else -> {
                showSnackbar("استرجاع غير معلوم")
            }

        }
    }

    fun signOut() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                // user is now signed out
                //todo:
                showSnackbar("sign out successful")
                finish()
            }
    }

    fun showSnackbar(message: String) {
        Snackbar.make(root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun sigup() {
        phoneNumber = firebaseauth.currentUser?.phoneNumber.toString()
        val retIn = RetrofitInstances.getRetrofitInstance().create(AuthApi::class.java)
        sharedPreferences.put("phoneNumber", phoneNumber)
        val firstName: String = sharedPreferences.getString("firstName").toString()
        val secondName: String = sharedPreferences.getString("secondName").toString()
        val password: String = sharedPreferences.getString("password").toString()
        Log.d("alaeldinmusa", firstName + " " + secondName + "  " + password + " " + phoneNumber)

        retIn.getRegisters(firstName, secondName, phoneNumber, password)
            .enqueue(object : Callback<RegisterModel> {
                override fun onResponse(
                    call: Call<RegisterModel>,
                    response: Response<RegisterModel>
                ) {
                    if (response.code() == 200) {
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                        finish()
                    } else {
                        startActivity(Intent(applicationContext, Register::class.java))
                        finish()
                    }

                }

                override fun onFailure(call: Call<RegisterModel>, t: Throwable) {
                    showSnackbar("خطا في عملية الاتصال مع السيرفر")
                    Log.d("alaeldin", t.message.toString())

                }

            })
    }


}