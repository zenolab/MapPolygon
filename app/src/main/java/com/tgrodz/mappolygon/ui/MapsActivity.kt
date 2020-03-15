package com.tgrodz.mappolygon.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.tgrodz.mappolygon.R
import kotlin.properties.Delegates


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object { @JvmField val TAG: String =  MapsActivity::class.java.simpleName }

    private lateinit var mMap: GoogleMap

    lateinit var fieldNoDescr: String
    var area by Delegates.notNull<Double>()

    var min_X by Delegates.notNull<Double>()
    var max_X by Delegates.notNull<Double>()
    var min_Y by Delegates.notNull<Double>()
    var max_Y by Delegates.notNull<Double>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val intent = intent
        fieldNoDescr = intent.getStringExtra("FieldNoDescr")
        area = intent.getDoubleExtra("Area", 0.0)

        min_X = intent.getDoubleExtra(ANGLE1, 0.0)
        max_X = intent.getDoubleExtra(ANGLE2 , 0.0)
        min_Y = intent.getDoubleExtra(ANGLE3, 0.0)
        max_Y = intent.getDoubleExtra(ANGLE4, 0.0)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val kiev = LatLng(50.449218, 30.525824)
        mMap.addMarker(MarkerOptions().position(kiev).title("Marker in Kiev"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(kiev))

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(kiev, 6f));

        val polygonOptions = PolygonOptions().apply {

            Log.d(TAG, " max_Y ${max_Y}")
            Log.d(TAG, " min_Y ${min_Y}")
            Log.d(TAG, " max_X ${max_X}")
            Log.d(TAG, " min_X ${min_X}")

            add(LatLng(max_Y, min_X))
            add(LatLng(min_Y, min_X))
            add(LatLng(min_Y, max_X))
            add(LatLng(max_Y, max_X))

            strokeWidth(12f)
                .strokeColor(Color.BLUE)
                .fillColor(Color.argb(20, 0, 255, 0))
        }

        val polygon = mMap.addPolygon(polygonOptions)

    }
}
