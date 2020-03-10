package com.tgrodz.mappolygon.network.repository

import androidx.lifecycle.MutableLiveData
import com.tgrodz.mappolygon.model.Area

interface Repository {

    public fun loadData(data: MutableLiveData<List<Area>>, deviceId : String)
}