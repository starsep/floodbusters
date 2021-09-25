package org.example.floodbusters.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import org.example.floodbusters.DataHolderAdapter
import org.example.floodbusters.R
import org.example.floodbusters.databinding.FragmentHomeBinding
import org.example.floodbusters.dataholder.User
import org.example.floodbusters.dataholder.WarningItemDataHolder
import org.example.floodbusters.ui.AvatarHeader

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel.text.observe(viewLifecycleOwner, {
            binding.avatarHeader.setContent {
                AvatarHeader(User(name = it), modifier = Modifier)
            }
        })

        val listView: ListView = binding.listView


        val dataHolderDanger = WarningItemDataHolder("red", "High flood risk", "The water level is raising rapidly")
        val dataHolderWarning = WarningItemDataHolder("yellow", "Warning flood", "")

        val list = mutableListOf<WarningItemDataHolder>()
        list.add(dataHolderDanger)
        list.add(dataHolderWarning)

        // initialize an array adapter
        val adapter: DataHolderAdapter = DataHolderAdapter(
            this.context,
            R.layout.notification_box, list
        )

        // attach the array adapter with list view
        listView.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}