package com.example.testcase

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonObjectRequest
import com.example.testcase.adapters.RequestAdapter
import com.example.testcase.constants.Constants
import com.example.testcase.models.Request
import org.json.JSONException
import com.android.volley.Request.Method.GET
import com.android.volley.toolbox.Volley


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val listRequest: ArrayList<Request> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

    }

    private fun init() {
        setSupportActionBar(findViewById(R.id.toolbar))
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        getDataRequest()

        recyclerView.adapter = RequestAdapter(this, listRequest)
    }

    private fun getDataRequest() {
        val stringRequest =
            JsonObjectRequest(GET, Constants.URL_GET_DATA_REQUEST, null, { response ->
                try {
                    if (!response.getBoolean("error")) {
                        for (i in response.getString("id")){
                            Request.setData(
                                response.getString("name_request"),
                                response.getString("priority"),
                                response.getString("status"),
                                response.getString("date_begine"),
                                response.getString("author")
                            )
                            listRequest.add(Request)
                        }
                    } else {
                        Log.d(TAG, "getDataRequest: что то не так")
                    }
                } catch (e: JSONException) {
                    Log.d(TAG, "getDataRequest: ${e.message}")
                }
            }) { error ->
                Log.d(TAG, "getDataRequest: ${error.message}")
            }
        Volley.newRequestQueue(this).add(stringRequest)


//        listRequest.add(
//            Request(
//                "№2002 Техническое обслуживание локальной станции оповещения",
//                "Высокий", "Не закрыта",
//                "18.02.2001", "Организация 1 / Галдин С.Д."
//            )
//        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        val mSearch: MenuItem = menu.findItem(R.id.search)
        val mSearchView: SearchView = mSearch.actionView as SearchView
        mSearchView.isIconifiedByDefault = false
        mSearchView.queryHint = "Поиск"
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}