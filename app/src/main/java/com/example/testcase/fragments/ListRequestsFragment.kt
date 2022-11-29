package com.example.testcase.fragments

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.testcase.AddRequestActivity
import com.example.testcase.ChangeDataRequestActivity
import com.example.testcase.R
import com.example.testcase.adapters.RequestAdapter
import com.example.testcase.constants.Constants
import com.example.testcase.models.Request
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONException

class ListRequestsFragment : Fragment() {
    private lateinit var adapter: RequestAdapter
    private lateinit var requestModel: Request
    private lateinit var mSearchView: SearchView
    private val listRequest = ArrayList<Request>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        init()
        getDataRequest()
        return inflater.inflate(R.layout.fragment_list_requests, container, false)
    }

    private fun init() {
        setSupportActionBar(findViewById(R.id.toolbar))
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        findViewById<FloatingActionButton>(R.id.buttonAdd).setOnClickListener {
            startActivity(Intent(this, AddRequestActivity::class.java))
        }
    }

    private fun initializeAdapter() {
        val itemOnClick: (Int) -> Unit = { position ->
            startActivity(
                Intent(this, ChangeDataRequestActivity::class.java)
                    .putExtra("name", listRequest[position].getNameRequest)
            )
        }
        adapter = RequestAdapter(this, listRequest, itemClickListener = itemOnClick)
        recyclerView.adapter = adapter
    }

    private fun getDataRequest() {
        val stringRequest = JsonArrayRequest(Constants.URL_GET_DATA_REQUEST, { response ->
            try {
                for (i in 0 until response.length()) {
                    val objectRequest = response.getJSONObject(i)
                    requestModel = Request(
                        objectRequest.getString("name_request"),
                        objectRequest.getString("name_priority"),
                        objectRequest.getString("name_status"),
                        objectRequest.getString("date_begine"),
                        objectRequest.getString("author")
                    )
                    listRequest.add(requestModel)
                }
                initializeAdapter()
                progressBar.visibility = View.GONE
            } catch (e: JSONException) {
                Log.d(ContentValues.TAG, "getDataRequest: ${e.message}")
                progressBar.visibility = View.GONE
            }
        }) { error ->
            Log.d(ContentValues.TAG, "getDataRequest: ${error.message}")
            progressBar.visibility = View.GONE
        }
        Volley.newRequestQueue(this).add(stringRequest)
    }
}