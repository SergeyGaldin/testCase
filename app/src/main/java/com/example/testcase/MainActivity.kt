package com.example.testcase

import android.app.SearchManager
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.testcase.adapters.RequestAdapter
import com.example.testcase.constants.Constants
import com.example.testcase.models.Request
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: RequestAdapter
    private lateinit var requestModel: Request
    private lateinit var mSearchView: SearchView
    private val listRequest = ArrayList<Request>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        getDataRequest()
        initializeAdapter()
    }

    private fun init() {
        setSupportActionBar(findViewById(R.id.toolbar))
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
    }

    private fun initializeAdapter() {
        adapter = RequestAdapter(this, listRequest)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = adapter
    }

    private fun getDataRequest() {
        val stringRequest = JsonArrayRequest(Constants.URL_GET_DATA_REQUEST, { response ->
            try {
                for (i in 0 until response.length()) {
                    val objectRequest = response.getJSONObject(i)
                    requestModel = Request(
                        objectRequest.getString("name_request"),
                        objectRequest.getString("priority"),
                        objectRequest.getString("status"),
                        objectRequest.getString("date_begine"),
                        objectRequest.getString("author")
                    )
                    listRequest.add(requestModel)
                }
                progressBar.visibility = View.GONE
            } catch (e: JSONException) {
                Log.d(TAG, "getDataRequest: ${e.message}")
                progressBar.visibility = View.GONE
            }
        }) { error ->
            Log.d(TAG, "getDataRequest: ${error.message}")
            progressBar.visibility = View.GONE
        }
        Volley.newRequestQueue(this).add(stringRequest)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        val mSearch: MenuItem = menu.findItem(R.id.search)
        mSearchView = mSearch.actionView as SearchView
        mSearchView.isIconifiedByDefault = false
        mSearchView.queryHint = "Поиск"
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val newList = ArrayList<Request>()
                for (item in listRequest) {
                    if (item.getDateRequest.contains(newText.toString())) {
                        newList.add(
                            Request(
                                item.getNameRequest,
                                item.getPriorityRequest,
                                item.getStatusRequest,
                                item.getDateRequest,
                                item.getExecutorRequest
                            )
                        )
                    }
                }
                adapter = RequestAdapter(applicationContext, newList)
                recyclerView.adapter = adapter
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            mSearchView.setQuery(query.toString(), false)
            mSearchView.requestFocus()
        }
    }
}