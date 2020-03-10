package com.tgrodz.mappolygon.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

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

        //Get data from intent
        val intent = intent
        fieldNoDescr = intent.getStringExtra("FieldNoDescr")
        area = intent.getDoubleExtra("Area", 0.0)
        min_X = intent.getDoubleExtra("min_X", 0.0)
        max_X = intent.getDoubleExtra("max_X", 0.0)
        min_Y = intent.getDoubleExtra("min_Y", 0.0)
        max_Y = intent.getDoubleExtra("man_Y", 0.0)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        val kiev = LatLng(50.449218, 30.525824)
        mMap.addMarker(MarkerOptions().position(kiev).title("Marker in Kiev"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(kiev))

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(kiev, 13f));

        val polygonOptions = PolygonOptions().apply {
            //Square Example1
//            add(LatLng(-31.2, 154.9))
//            add(LatLng(-30.0, 142.2))
//            add(LatLng(-36.1, 142.1))
//            add(LatLng(-35.6, 154.8))

//            Square (Example2)
//            add(LatLng(50.444477, 30.498976)) //1
//            add(LatLng(50.445290, 30.500864)) //2
//            add(LatLng(50.443958, 30.508921)) //3
//            add(LatLng(50.443247, 30.508642)) //4

            // add(LatLng(50.442830, 30.508878)) //5
            // add(LatLng(50.440965, 30.508256)) //6
            // add(LatLng(50.441949, 30.501443)) //7


//            //A Cartesian coordinate system (from kernel json)
            add(LatLng(min_X, min_Y))
            add(LatLng(min_X, max_Y))
            add(LatLng(max_X, min_Y))
            add(LatLng(max_X, max_Y))


            strokeWidth(12f)
                .strokeColor(Color.BLUE)
                .fillColor(Color.argb(20, 0, 255, 0))
        }

        val polygon = mMap.addPolygon(polygonOptions)

    }
}
