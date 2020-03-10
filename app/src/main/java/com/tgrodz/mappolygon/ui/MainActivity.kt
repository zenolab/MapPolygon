package com.tgrodz.mappolygon.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu

import androidx.appcompat.widget.SearchView;

import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jessicathornsby.myapplication.viewmodel.BaseViewModelFactory
import com.tgrodz.mappolygon.R
import com.tgrodz.mappolygon.model.Area
import com.tgrodz.mappolygon.ui.adapter.AreaAdapter
import com.tgrodz.mappolygon.ui.adapter.ItemListener
import com.tgrodz.mappolygon.ui.viewmodel.AreaViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), ItemListener {

    companion object { @JvmField val TAG: String = MainActivity::class.java.simpleName }
    private val SEARCH_TAG: String = "--SearchView"

    private lateinit var areaAdapter: AreaAdapter
    private lateinit var filteredAreaAdapter: AreaAdapter

    val filteredAreas = ArrayList<Area>()
    lateinit var areas: ArrayList<Area>
    private lateinit var context: Context


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        initRecyclerView()
        loadData()
    }

    private fun loadData() {
        val deviceId = getString("deviceId")
        val viewModel: AreaViewModel by lazy {
            ViewModelProviders.of(this, BaseViewModelFactory { AreaViewModel(deviceId) })
                .get(AreaViewModel::class.java)
        }
        viewModel.getAreaData()?.observe(this, Observer<List<Area>> { it -> handleResponse(it) })
    }

    private fun handleResponse(list: List<Area>?) {
        Log.d(TAG, "List of area size is: ${list?.size}")
        areas = list as ArrayList<Area>
        areaAdapter = AreaAdapter(areas!!, this)
        recycler_view.adapter = areaAdapter
        filteredAreaAdapter = areaAdapter
    }

    private fun initRecyclerView() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager
    }

    override fun onItemClick(item: Area) {
        Toast.makeText(this, "You clicked: ${item.area}", Toast.LENGTH_SHORT).show()

        Log.d(TAG, " max_Y ${item.min_X}")
        Log.d(TAG, " min_Y ${item.max_X}")
        Log.d(TAG, " max_X ${item.min_Y}")
        Log.d(TAG, " min_X ${item.max_Y}")

        val intent = Intent(this, MapsActivity::class.java)
        intent.putExtra("FieldNoDescr", item.fieldNoDescr)
        intent.putExtra("Area", item.area)
        intent.putExtra(ANGLE1, item.min_X)
        intent.putExtra(ANGLE2, item.max_X)
        intent.putExtra(ANGLE3, item.min_Y)
        intent.putExtra(ANGLE4, item.max_Y)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.action_menu, menu)
        val searchViewItem = menu.findItem(R.id.menuSearch)
        val searchView = searchViewItem.actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE
        searchView.queryHint = getString("search_hint")
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) recycler_view.adapter = areaAdapter
                else {
                    filteredAreas.clear()
                    for (area in areas)
                        if (area.fieldNoDescr.toLowerCase(Locale.getDefault())
                                .contains(newText.toLowerCase(Locale.getDefault()))
                            || area.fieldNo.toLowerCase(Locale.getDefault()).contains(
                                newText.toLowerCase(Locale.getDefault()))
                            || area.cornType.toLowerCase(Locale.getDefault()).contains(
                                newText.toLowerCase(Locale.getDefault()))
                        ) filteredAreas.add(area)
                    if (filteredAreas.isEmpty()) Log.d(SEARCH_TAG, " Areas list isEmpty()")
                    filteredAreaAdapter.setArticles(filteredAreas)
                    recycler_view.adapter = filteredAreaAdapter
                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun AppCompatActivity.getString(name: String): String {
        return resources.getString(resources.getIdentifier(name, "string", packageName))
    }

}
