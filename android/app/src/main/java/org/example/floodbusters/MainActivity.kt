package org.example.floodbusters

import android.Manifest
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.BasemapStyle
import com.esri.arcgisruntime.mapping.Viewpoint
import com.esri.arcgisruntime.mapping.view.MapView
import org.example.floodbusters.databinding.ActivityMainBinding
import com.huawei.hmf.tasks.OnSuccessListener
import com.huawei.hms.common.ResolvableApiException
import com.huawei.hms.location.*
import kotlinx.coroutines.DelicateCoroutinesApi


@DelicateCoroutinesApi
class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mapView: MapView by lazy { binding.mapView }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupMap()
//        setupLocation()
//        getLastLocation()
    }

    private fun setupMap() {
        mapView.map = ArcGISMap(BasemapStyle.ARCGIS_TOPOGRAPHIC)
        mapView.setViewpoint(Viewpoint(47.3774, 8.4766, 72000.0))
    }

   private fun setupLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            val strings = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            ActivityCompat.requestPermissions(this, strings, 1)
        }
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

    private val fusedLocationProviderClient: FusedLocationProviderClient by lazy { LocationServices.getFusedLocationProviderClient(baseContext) }

    private fun getLastLocation() {
        try {
            val lastLocation =
                    fusedLocationProviderClient.lastLocation
            lastLocation.addOnSuccessListener(OnSuccessListener { location ->
                if (location == null) {
                    Log.i(TAG, "getLastLocation onSuccess location is null")
                    return@OnSuccessListener
                }
                Log.i(
                        TAG,
                        "getLastLocation onSuccess location[Longitude,Latitude]:${location.longitude},${location.latitude}"
                )
                return@OnSuccessListener
            }).addOnFailureListener { e: Exception ->
                if (e is ResolvableApiException) {
                    val apiException = e
                    Log.e(TAG, "getLastLocation onFailure:" + apiException.statusCode)
                    try {
                        apiException.startResolutionForResult(this@MainActivity, 2009)
                    } catch (ex: IntentSender.SendIntentException) {
                        ex.printStackTrace()
                    }
                } else {
                    Log.e(TAG, "getLastLocation onFailure:" + e.message)
                }

            }
        } catch (e: Exception) {
            Log.e(TAG, "getLastLocation exception:${e.message}")
        }
    }

    companion object {
        const val TAG = "Floodbusters"
    }
}