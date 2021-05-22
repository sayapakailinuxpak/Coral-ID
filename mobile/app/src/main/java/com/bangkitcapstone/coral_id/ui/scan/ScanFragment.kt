package com.bangkitcapstone.coral_id.ui.scan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bangkitcapstone.coral_id.R
import com.bangkitcapstone.coral_id.databinding.FragmentScanBinding


class ScanFragment : Fragment(), View.OnClickListener {

    private var _binding : FragmentScanBinding? = null
    private val binding get() = _binding
    private val buttonClick = AlphaAnimation(2f, 0.5f)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentScanBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnFlash?.setOnClickListener(this)
        binding?.btnPickImage?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_flash -> {
                v.startAnimation(buttonClick)
                Toast.makeText(context, "Turn on Flash", Toast.LENGTH_SHORT).show()
            }
            R.id.btn_pick_image -> {
                v.startAnimation(buttonClick)
                Toast.makeText(context, "Pick Image", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_scanFragment_to_resultFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}