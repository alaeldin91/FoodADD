package com.example.foodapp.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.*
import androidx.databinding.ViewDataBinding
import com.example.foodapp.R
import com.example.foodapp.databinding.LayCustomDialogBinding
import com.example.foodapp.helper.PreferencesHelper
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val sharedPreferences: PreferencesHelper = PreferencesHelper(this)
        signup.setOnClickListener {
            val firstName = firstNameEdt.text.toString()
            val seccondName = secondNameEdt.text.toString()
            val password = passwordEdt.text.toString()
            val confirm_password = confirm_passwordEdt.text.toString()
            if (TextUtils.isEmpty(firstName)) {
                firstNameEdt.error = "من فضلك ادخل الاسم الاول"
                firstNameEdt.requestFocus()
            } else if (TextUtils.isEmpty(seccondName)) {
                secondNameEdt.error = "من فضلك ادخل الاسم الثاني"
                secondNameEdt.requestFocus()
            } else if (TextUtils.isEmpty(password)) {
                passwordEdt.error
                passwordEdt.requestFocus()
            }
            else if (!password.equals(confirm_password)) {
                showCustomDialog()
            } else {
                sharedPreferences.put("firstName", firstName)
                sharedPreferences.put("secondName", seccondName)
                sharedPreferences.put("password", password)

                startActivity(Intent(applicationContext, VerficationRegister::class.java))
                finish()
            }
        }
    }

    private fun showCustomDialog() {
        val dialogBinding: LayCustomDialogBinding? = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.lay_custom_dialog,
            null,
            false
        )
        val customDialog = AlertDialog.Builder(this,0).create()
        customDialog.setView(dialogBinding?.root)
        customDialog.setCancelable(false)
        customDialog.show()
        dialogBinding?.btnok?.setOnClickListener{
            customDialog.dismiss()

        }


    }
}