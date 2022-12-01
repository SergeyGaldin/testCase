package com.example.testcase.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.setFragmentResultListener
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.testcase.constants.Constants
import com.example.testcase.constants.Methods
import com.example.testcase.databinding.FragmentChangeDataRequestBinding
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap
import kotlin.jvm.Throws

class ChangeDataRequestFragment : Fragment() {
    private lateinit var binding: FragmentChangeDataRequestBinding
    private var idRequest: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeDataRequestBinding.inflate(inflater)
        init()
        initializeAppBar()
        return binding.root
    }

    private fun init() {
        setFragmentResultListener("request_key") { _, result ->
            idRequest = result.getString("id")
            Methods.callToast(requireContext(), result.getString("id").toString())
        }

        binding.buttonDelete.setOnClickListener { deleteRequest() }
    }

    private fun initializeAppBar() {
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Изменение заявки"
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        })
    }

    private fun deleteRequest() {
        val stringRequest = object :
            StringRequest(Method.POST, Constants.URL_DELETE_REQUEST, Response.Listener {
                binding.progressBar.visibility = View.GONE
                binding.buttonDelete.isClickable = true
                try {
                    val jsonObject = JSONObject(it)
                    if (!jsonObject.getBoolean("error")) {
                        activity?.onBackPressed()
                    } else {
                        Methods.callSnackbar(requireView(), jsonObject.getString("message"))
                    }
                } catch (e: JSONException) {
                    Methods.callSnackbar(requireView(), e.message.toString())
                }
            }, Response.ErrorListener {
                binding.progressBar.visibility = View.GONE
                binding.buttonDelete.isClickable = true
                if (it?.message != null) Methods.callSnackbar(requireView(), it.message!!)
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["id_request"] = idRequest!!
                return params
            }
        }
        Volley.newRequestQueue(context).add(stringRequest)
    }
}