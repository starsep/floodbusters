package org.example.floodbusters

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.BasemapStyle
import com.esri.arcgisruntime.mapping.Viewpoint
import com.esri.arcgisruntime.mapping.view.MapView
import org.example.floodbusters.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val activityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val mapView: MapView by lazy {
        activityMainBinding.mapView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)
        setupMap()
    }

    private fun setupMap() {
        // TODO: remove API key from source code
        ArcGISRuntimeEnvironment.setApiKey("AAPK656287b000314dccad848cf9cca8fde4En6PvvnSTslZpt1ysboHHuR6B3U4aUOgm46XUD04eGM8488LbFgFW7CdL3SE1jFD")
        mapView.map = ArcGISMap(BasemapStyle.ARCGIS_TOPOGRAPHIC)
        mapView.setViewpoint(Viewpoint(47.3774, 8.4766, 72000.0))
    }

    override fun onPause() {
        mapView.pause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.resume()
    }

    override fun onDestroy() {
        mapView.dispose()
        super.onDestroy()
    }
}