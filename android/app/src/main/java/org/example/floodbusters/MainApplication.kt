package org.example.floodbusters

import android.app.Application
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setupArcGIS()
    }

    private fun setupArcGIS() {
        // TODO: remove API key from source code
        ArcGISRuntimeEnvironment.setApiKey("AAPK656287b000314dccad848cf9cca8fde4En6PvvnSTslZpt1ysboHHuR6B3U4aUOgm46XUD04eGM8488LbFgFW7CdL3SE1jFD")
    }
}