package com.example.testcase

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.testcase.constants.Constants
import com.example.testcase.constants.Methods
import com.example.testcase.models.User
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONException
import org.json.JSONObject
import kotlin.jvm.Throws

class SignInActivity : AppCompatActivity() {
    private lateinit var inputLogin: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var textLogin: TextInputEditText
    private lateinit var textPassword: TextInputEditText
    private lateinit var buttonSignIn: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        init()

        buttonSignIn.setOnClickListener {
            if (validation()) signIn(it)
        }
    }

    private fun init() {
        inputLogin = findViewById(R.id.inputLogin)
        inputPassword = findViewById(R.id.inputPassword)
        textLogin = findViewById(R.id.textLogin)
        textPassword = findViewById(R.id.textPassword)
        buttonSignIn = findViewById(R.id.buttonSignIn)
        progressBar = findViewById(R.id.progressBar)
    }

    //  Проверка заполнености полей
    private fun validation(): Boolean {
        inputLogin.error = null
        inputPassword.error = null
        User.setData(textLogin.text.toString(), textPassword.text.toString())
        if (User.getLogin.isEmpty()) {
            inputLogin.error = "Поле пустое"
            return false
        }
        if (User.getPassword.isEmpty()) {
            inputPassword.error = "Поле пустое"
            return false
        }
        return true
    }

    //  Авторизация
    private fun signIn(view: View) {
        buttonSignIn.isClickable = false
        progressBar.visibility = View.VISIBLE
        val stringRequest =
            object : StringRequest(Method.POST, Constants.URL_LOGIN, Response.Listener {
                progressBar.visibility = View.GONE
                buttonSignIn.isClickable = true
                try {
                    if (!JSONObject(it).getBoolean("error")) {
                        val jsonObject = JSONObject(it)
                        User.setExtraData(
                            jsonObject.getString("user_name"),
                            jsonObject.getString("name_organization"),
                            jsonObject.getString("name_role")
                        )
                        Log.d(TAG, "signIn: ${User.getRole}")
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else Methods.callSnackbar(view, JSONObject(it).getString("message"))
                } catch (e: JSONException) {
                    Methods.callSnackbar(view, e.message.toString())
                }
            }, Response.ErrorListener {
                progressBar.visibility = View.GONE
                buttonSignIn.isClickable = true
                if (it?.message != null) Methods.callSnackbar(view, it.message!!)
            }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): MutableMap<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["login"] = User.getLogin
                    params["password"] = User.getPassword
                    return params
                }
            }
        Volley.newRequestQueue(this).add(stringRequest)
    }
}