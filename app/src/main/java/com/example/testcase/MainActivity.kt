package com.example.testcase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testcase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }



//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.update -> {
//                listRequest.clear()
//                getDataRequest()
//            }
//        }
//        return true
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.app_bar_menu, menu)
//        val mSearch: MenuItem = menu.findItem(R.id.search)
//        mSearchView = mSearch.actionView as SearchView
//        mSearchView.isIconifiedByDefault = false
//        mSearchView.queryHint = "Поиск"
//        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                val newList = ArrayList<Request>()
//                for (item in listRequest) {
//                    if (item.getNameRequest.contains(newText.toString()) ||
//                        item.getPriorityRequest.contains(newText.toString()) ||
//                        item.getStatusRequest.contains(newText.toString()) ||
//                        item.getDateRequest.contains(newText.toString()) ||
//                        item.getExecutorRequest.contains(newText.toString())
//                    ) {
//                        newList.add(
//                            Request(
//                                item.getNameRequest,
//                                item.getPriorityRequest,
//                                item.getStatusRequest,
//                                item.getDateRequest,
//                                item.getExecutorRequest
//                            )
//                        )
//                    }
//                }
//                val itemOnClick: (Int) -> Unit = { position ->
//                    startActivity(
//                        Intent(applicationContext, ChangeDataRequestActivity::class.java)
//                            .putExtra("name", listRequest[position].getNameRequest)
//                    )
//                }
//                adapter = RequestAdapter(applicationContext, newList, itemOnClick)
//                recyclerView.adapter = adapter
//                return true
//            }
//        })
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onNewIntent(intent: Intent) {
//        super.onNewIntent(intent)
//        if (Intent.ACTION_SEARCH == intent.action) {
//            val query = intent.getStringExtra(SearchManager.QUERY)
//            mSearchView.setQuery(query.toString(), false)
//            mSearchView.requestFocus()
//        }
//    }
}