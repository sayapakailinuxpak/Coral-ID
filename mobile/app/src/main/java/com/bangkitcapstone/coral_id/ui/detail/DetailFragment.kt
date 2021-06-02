package com.bangkitcapstone.coral_id.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bangkitcapstone.coral_id.R
import com.bangkitcapstone.coral_id.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private var  _binding: FragmentDetailBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            mtoolbarDetail.setNavigationOnClickListener {
                Toast.makeText(activity, "Close detail fragment", Toast.LENGTH_SHORT).show()
            }
            mappbarSheet.outlineProvider = null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}