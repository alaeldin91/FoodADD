package com.example.foodapp.activites

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.foodapp.R
import com.example.foodapp.activites.VerficationRegister.Companion.RC_SIGN_IN
import com.example.foodapp.databinding.LayCustomDialogBinding
import com.example.foodapp.helper.PreferencesHelper
import com.example.foodapp.pojo.LoginModel
import com.example.foodapp.retrofit.AuthApi
import com.example.foodapp.retrofit.RetrofitInstances
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.passwordEdt
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {
    lateinit var callbackManager: CallbackManager
    lateinit var signInButton: SignInButton
    private lateinit var googleApiClient: GoogleSignInClient

    companion object {
        const val signin = 234
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val login_txt = findViewById<TextView>(R.id.login)
        callbackManager = CallbackManager.Factory.create()
        val gso: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
                .build()
        googleApiClient = GoogleSignIn.getClient(this, gso)
        val google_Signin_account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)

        signInButton = findViewById<SignInButton>(R.id.google_Signin)
        signInButton.setOnClickListener {


            signInGmail()


        }


        loginfacebook.setOnClickListener {
            LoginManager.getInstance()
                .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                    override fun onSuccess(result: LoginResult?) {
                        RequestData()
                    }

                    override fun onCancel() {
                        startActivity(Intent(this@MainActivity, Register::class.java))

                    }

                    override fun onError(error: FacebookException?) {
                        Toast.makeText(applicationContext, "خطا في تسجيل الدخول", Toast.LENGTH_LONG)
                            .show()

                    }

                })
        }




        login_txt.setOnClickListener {
            //startActivity(Intent(applicationContext, Home::class.java))
            val phoneNumber = phoneNumberEdt.text.toString()
            val password = passwordEdt.text.toString()
            if (TextUtils.isEmpty(phoneNumber)) {
                phoneNumberEdt.error = "من فضلك ادخل رقم الهاتف"
                phoneNumberEdt.requestFocus()
            } else if (TextUtils.isEmpty(password)) {
                passwordEdt.error = "من فضلك ادخل كلمة السر"
                passwordEdt.requestFocus()
            } else {
                signIn(phoneNumber, password)
                Log.d("alaeldin1", phoneNumber + "aND" + password)
            }
        }
        val register_txt = findViewById<TextView>(R.id.register_txt)
        register_txt.setOnClickListener {
            startActivity(Intent(applicationContext, Register::class.java))

        }

    }

    private fun signInGmail() {
        val signInIntent: Intent = googleApiClient.signInIntent
        startActivityForResult(signInIntent, signin)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
        if (requestCode == signin) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completeTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completeTask.getResult(ApiException::class.java)
            if (completeTask.isSuccessful) {
                val name: String = account.displayName.toString()
                val familyName: String = account.familyName.toString()
                val email: String = account.email.toString()
                startActivity(Intent(this, Home::class.java))
                finish()
            } else {
                Toast.makeText(applicationContext, "Login Failed", Toast.LENGTH_LONG).show()
            }
        }
        catch ( e:ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }


    }

    private fun signIn(phoneNumber: String, password: String) {
        val sharedPreferences: PreferencesHelper = PreferencesHelper(this)
        val reltn = RetrofitInstances.getRetrofitInstance().create(AuthApi::class.java)
        reltn.signIn(phoneNumber, password).enqueue(object : Callback<LoginModel> {
            override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {

                if (response.body()?.response.equals("ok") == true) {
                    sharedPreferences.put("phoneNumbers", phoneNumber)
                    sharedPreferences.put("passwords", password)
                    startActivity(Intent(this@MainActivity, Home::class.java))

                    Toast.makeText(applicationContext, "تم التسجيل بنجاح", Toast.LENGTH_LONG)
                        .show()
                    Log.d("alaeldin", response.body()?.response.toString())
                    Log.d("alaeldin", phoneNumber)
                    finish()
                } else {
                    Toast.makeText(applicationContext, "خطا في التسجيل للدخول", Toast.LENGTH_LONG)
                        .show()
                }

            }

            override fun onFailure(call: Call<LoginModel>, t: Throwable) {
                Log.d("alaeldin", t.message.toString())

            }

        })

    }

    private fun RequestData() {
        val request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
            object : GraphRequest.GraphJSONObjectCallback {
                override fun onCompleted(`object`: JSONObject?, response: GraphResponse?) {
                    val json = response?.jsonObject
                    try {
                        if (json != null) {
                            val sharedPreferences: PreferencesHelper =
                                PreferencesHelper(applicationContext)

                            val name: String = json.getString("name")
                            Log.i("musa", name)
                            sharedPreferences.put("name", name)
                            startActivity(Intent(this@MainActivity, Home::class.java))
                            finish()

                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

            }
        )
        request.executeAsync()
    }


    private fun showCustomDialog() {
        val dialogBinding: LayCustomDialogBinding? = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.lay_custom_dialog,
            null,
            false
        )
        val customDialog = AlertDialog.Builder(this, 0).create()
        customDialog.setView(dialogBinding?.root)
        customDialog.setCancelable(false)
        customDialog.show()
        dialogBinding?.btnok?.setOnClickListener {
            customDialog.dismiss()
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("Not yet implemented")
    }

}