package com.bangkitcapstone.coral_id.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD
import androidx.lifecycle.ViewModelProvider
=======
import android.widget.Toast
>>>>>>> 87cbbc3052dda20dd09e6000e0f1ebfef09f2c7f
import com.bangkitcapstone.coral_id.R
import com.bangkitcapstone.coral_id.data.source.remote.response.CoralsResponse
import com.bangkitcapstone.coral_id.databinding.FragmentDetailBinding
<<<<<<< HEAD
import com.bangkitcapstone.coral_id.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_detail.*
=======
>>>>>>> 87cbbc3052dda20dd09e6000e0f1ebfef09f2c7f

class DetailFragment : Fragment() {
    private var  _binding: FragmentDetailBinding? = null
    private val binding get() = _binding

    private lateinit var viewModel: DetailViewModel
    private val factory = ViewModelFactory.getInstance()

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

        val id = arguments?.getInt("id")

        activity?.let {
            viewModel = ViewModelProvider(
                it,
                factory
            )[DetailViewModel::class.java]
            if (id != null) {
                viewModel.getCoralById(id).observe(viewLifecycleOwner, {
                    Log.d("Lihat coral by id", it.toString())
                    dataDisplay(it)
                })
            }
        }

        binding?.apply {
            mtoolbarDetail.setNavigationOnClickListener {
                Toast.makeText(activity, "Close detail fragment", Toast.LENGTH_SHORT).show()
            }
            mappbarSheet.outlineProvider = null
<<<<<<< HEAD
        }
    }

    private fun dataDisplay(coral: CoralsResponse) {
        binding?.let {
            text_coral_full_name_detail.text = coral.fullName
=======
>>>>>>> 87cbbc3052dda20dd09e6000e0f1ebfef09f2c7f
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}