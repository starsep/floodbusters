package org.example.floodbusters

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.example.floodbusters.databinding.ActivityPreInformationBinding

class PreInformationActivity : AppCompatActivity() {
    private val binding by lazy { ActivityPreInformationBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_pre_information)
        navView.setupWithNavController(navController)
    }
}