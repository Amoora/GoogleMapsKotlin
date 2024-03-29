package com.example.googlemapskotlin

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*
import java.util.jar.Manifest

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private var REQUEST_LOCATION_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val latitude = 11.993155
        val longitude =8.560523
        val zoomLevel = 15f
        val startingPointLatLong = LatLng(latitude,longitude )


        map.moveCamera(CameraUpdateFactory.newLatLngZoom(startingPointLatLong, zoomLevel))
        map.addMarker(MarkerOptions().position(startingPointLatLong).title("Marker in Sydney"))
        setMapLongClick(map)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.map_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.normal_map -> {
                GoogleMap.MAP_TYPE_NORMAL
                true
            }
            R.id.hybrid_map -> {
                GoogleMap.MAP_TYPE_HYBRID
                true
            }
            R.id.satellite_map -> {
                GoogleMap.MAP_TYPE_SATELLITE
                true
            }
            R.id.terrain_map -> {
                GoogleMap.MAP_TYPE_TERRAIN
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun setMapLongClick(map:GoogleMap){
        map.setOnMapLongClickListener {LatLng ->
            val snippet = String.format(Locale.getDefault(), LatLng.latitude, LatLng.longitude
            )
            map.addMarker(
                MarkerOptions()
                    .position(LatLng)
                    .title(getString( R.string.app_name))
                    .snippet(snippet)

            )
        }

        }
        private fun isPermissionGranted(): Boolean{
            return ContextCompat.checkSelfPermission(
               this,
               android.Manifest.permission.ACCESS_FINE_LOCATION)===PackageManager.PERMISSION_GRANTED
        }

    private fun enabeMyLocation(){
        if (isPermissionGranted()){
            map.setMyLocationEnabled(true)
        }
        else{
            ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),REQUEST_LOCATION_PERMISSION)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_LOCATION_PERMISSION){
                if(grantResults.size>0 && (grantResults[0]==PackageManager.PERMISSION_GRANTED))
                    enabeMyLocation()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    }

