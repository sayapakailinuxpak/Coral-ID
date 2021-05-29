package com.bangkitcapstone.coral_id.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.bangkitcapstone.coral_id.R
import com.bangkitcapstone.coral_id.data.DataCoral
import com.bangkitcapstone.coral_id.databinding.FragmentHomeBinding
import com.bangkitcapstone.coral_id.utils.DataDummy

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*val listCoral = DataDummy.generateDummyCoral()
        Log.d("Lihat :", listCoral.toString())*/

        binding?.button?.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_scanFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}