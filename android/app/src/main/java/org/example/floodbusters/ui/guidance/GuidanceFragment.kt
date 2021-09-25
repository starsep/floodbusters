package org.example.floodbusters.ui.guidance

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.example.floodbusters.databinding.FragmentGuidanceBinding

class GuidanceFragment : Fragment() {
    private var _binding: FragmentGuidanceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGuidanceBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.guidanceComposeView.setContent { GuidanceScreen(requireActivity()) }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}