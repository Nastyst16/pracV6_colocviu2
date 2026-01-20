package com.example.pracv6_colocviu2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Initializare Fragment
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // CERINTA: Centrare pe Ghelmegioaia, Mehedinti
        // Coordonate aproximative: 44.624 N, 22.890 E
        val ghelmegioaiaLocation = LatLng(44.613, 22.831)

        googleMap.addMarker(
            MarkerOptions()
                .position(ghelmegioaiaLocation)
                .title("Ghelmegioaia")
        )

        // Zoom 15 ca sa se vada satul
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ghelmegioaiaLocation, 15f))
    }
}