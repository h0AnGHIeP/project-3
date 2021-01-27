package com.edu.hoang.ui.map

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class HanoiMapFragment : SupportMapFragment(), OnMapReadyCallback {

    private lateinit var viewModel: MapViewModel
    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?
    ): View? {
        val root = super.onCreateView(inflater, container, bundle)
        viewModel = ViewModelProvider(this).get(MapViewModel::class.java)
        viewModel.renderLocal()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMapAsync(this)
        viewModel.renderLatest()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        observeClinics()
        setZoomPreferences()
        updateCamera()
    }

    private fun updateCamera() {
        val camUpdate = CameraUpdateFactory.newLatLngZoom(
            LatLng(
                21.00354007932808, 105.82000315474384
            ), 14f
        )
        map.moveCamera(camUpdate)
    }

    private fun setZoomPreferences() {
        map.setMinZoomPreference(6f)
        map.setMaxZoomPreference(18f)
    }

    private fun observeClinics() {
        viewModel.clinics.observe(viewLifecycleOwner) { clinics ->
            clinics.forEach {
                val options = MarkerOptions().position(
                    LatLng(
                        it.latitude.toDouble(), it.longitude.toDouble()
                    )
                ).title(it.name)
                map.addMarker(options)
            }
        }
    }


}
