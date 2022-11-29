package com.example.testcase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.testcase.constants.Constants
import com.example.testcase.constants.Methods
import com.example.testcase.databinding.ActivitySignInBinding
import com.example.testcase.models.User
import org.json.JSONException
import org.json.JSONObject
import kotlin.jvm.Throws

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSignIn.setOnClickListener {
            if (validation()) signIn(it)
        }
    }

    //  Проверка заполнености полей
    private fun validation(): Boolean {
        binding.inputLogin.error = null
        binding.inputPassword.error = null
        User.setData(binding.textLogin.text.toString(), binding.textPassword.text.toString())
        if (User.getLogin?.isEmpty() == true) {
            binding.inputLogin.error = "Поле пустое"
            return false
        }
        if (User.getPassword?.isEmpty() == true) {
            binding.inputPassword.error = "Поле пустое"
            return false
        }
        return true
    }

    //  Авторизация
    private fun signIn(view: View) {
        binding.buttonSignIn.isClickable = false
        binding.progressBar.visibility = View.VISIBLE
        val stringRequest =
            object : StringRequest(Method.POST, Constants.URL_LOGIN, Response.Listener {
                binding.progressBar.visibility = View.GONE
                binding.buttonSignIn.isClickable = true
                try {
                    if (!JSONObject(it).getBoolean("error")) {
                        val jsonObject = JSONObject(it)
                        User.setExtraData(
                            jsonObject.getString("user_name"),
                            jsonObject.getString("name_organization"),
                            jsonObject.getString("name_role")
                        )
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else Methods.callSnackbar(view, JSONObject(it).getString("message"))
                } catch (e: JSONException) {
                    Methods.callSnackbar(view, e.message.toString())
                }
            }, Response.ErrorListener {
                binding.progressBar.visibility = View.GONE
                binding.buttonSignIn.isClickable = true
                if (it?.message != null) Methods.callSnackbar(view, it.message!!)
            }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): MutableMap<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["login"] = User.getLogin.toString()
                    params["password"] = User.getPassword.toString()
                    return params
                }
            }
        Volley.newRequestQueue(this).add(stringRequest)
    }
}