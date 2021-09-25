package org.example.floodbusters

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import org.example.floodbusters.databinding.ActivityPreInformationBinding
import org.example.floodbusters.ui.group.GroupFragment

import org.example.floodbusters.ui.guidance.GuidanceFragment

import org.example.floodbusters.ui.home.HomeFragment




class PreInformationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreInformationBinding

    val fragment1: Fragment = HomeFragment()
    val fragment2: Fragment = GuidanceFragment()
    val fragment3: Fragment = GroupFragment()
    val fm: FragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPreInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_pre_information)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_guidance, R.id.navigation_group, R.id.navigation_profile,
            )
        )
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}