package com.tgrodz.mappolygon.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tgrodz.mappolygon.ui.MainActivity
import com.tgrodz.mappolygon.model.Area
import com.tgrodz.mappolygon.network.AreaApi
import com.tgrodz.mappolygon.network.repository.AreaRepository
import javax.inject.Inject

class AreaViewModel (private val deviceId: String) : ViewModel()   {

    companion object { @JvmField val TAG: String = AreaViewModel::class.java.simpleName }

    private val cryptoRepository = AreaRepository()
    private val areasData : MutableLiveData<List<Area>> by lazy {
        MutableLiveData<List<Area>>()
    }

    fun getAreaData() : MutableLiveData<List<Area>>? {
        Log.d(TAG,"Get data of area")
        cryptoRepository.loadData(areasData,deviceId)
        return areasData
    }

    override fun onCleared() {
        super.onCleared()
    }

}