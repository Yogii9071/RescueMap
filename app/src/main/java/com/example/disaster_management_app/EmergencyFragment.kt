package com.example.disaster_management_app

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.example.disaster_management_app.RescueData.agencies

class EmergencyFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private var mMap: GoogleMap? = null
    private lateinit var agencyListView: ListView
    private lateinit var adapter: ArrayAdapter<String>

    val rescueAgencies = agencies

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_emergency, container, false)
        mapView = view.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        agencyListView = view.findViewById(R.id.agencyListView)

        // Buttons
        view.findViewById<Button>(R.id.fireEmergency).setOnClickListener {
            displayAgencies("Fire")
        }
        view.findViewById<Button>(R.id.medicalEmergency).setOnClickListener {
            displayAgencies("Medical")
        }
        view.findViewById<Button>(R.id.policeEmergency).setOnClickListener {
            displayAgencies("Police")
        }

        return view
    }

    private fun displayAgencies(type: String) {
        val filtered = rescueAgencies.filter { it.type.equals(type, ignoreCase = true) }
        val agencyDetails = filtered.map {
            "${it.name}\nType: ${it.type}\nPhone: ${it.phoneNumber}\nEmail: ${it.email}"
        }
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, agencyDetails)
        agencyListView.adapter = adapter

        // Make list visible and set background color
        agencyListView.visibility = View.VISIBLE
        agencyListView.setBackgroundColor(resources.getColor(android.R.color.white, null))
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap?.uiSettings?.isZoomControlsEnabled = true

        for (agency in rescueAgencies) {
            val position = LatLng(agency.latitude, agency.longitude)
            mMap?.addMarker(
                MarkerOptions().position(position)
                    .title("${agency.name}")
                    .snippet("Type: ${agency.type}\nPhone: ${agency.phoneNumber}\nEmail: ${agency.email}")
            )
        }

        // Move camera
        rescueAgencies.firstOrNull()?.let {
            val location = LatLng(it.latitude, it.longitude)
            mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 9f))
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    data class RescueAgency(
        val name: String,
        val type: String,
        val latitude: Double,
        val longitude: Double,
        val phoneNumber: String,
        val email: String
    )
}
