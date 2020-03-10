package com.tgrodz.mappolygon.network.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.tgrodz.mappolygon.network.RetrofitClient
import com.tgrodz.mappolygon.model.Area
import io.reactivex.disposables.Disposable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AreaRepository : Repository {

    private val Any.TAG: String
        get() {
            val tag = javaClass.simpleName
            return if (tag.length <= 23) tag else tag.substring(0, 23)
        }

    private lateinit var liveDataRepo: MutableLiveData<List<Area>>
    private val requestInterface = RetrofitClient.INSTANCE

    override fun loadData(data: MutableLiveData<List<Area>>, deviceId: String) {
        liveDataRepo = data
        requestInterface.getData(deviceId, deviceId).enqueue(object : Callback<List<Area>> {
            override fun onResponse(call: Call<List<Area>>, response: Response<List<Area>>) {
                if (response.isSuccessful) {
                    Log.w(TAG, " Data load successful");
                    if (response.code() < 400 && response.body() != null) {
                        Log.w(TAG, "Data load ${response.body()}");
                        liveDataRepo.value = response.body()
                    } else {
                        Log.e(TAG, " No data");
                    }
                }
            }

            override fun onFailure(call: Call<List<Area>>, t: Throwable) {
                Log.e(TAG, " Data load Failure ");
                t.printStackTrace()
            }
        })

    }

}