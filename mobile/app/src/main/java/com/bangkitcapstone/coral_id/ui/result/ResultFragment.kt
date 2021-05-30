package com.bangkitcapstone.coral_id.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkitcapstone.coral_id.R
import com.bangkitcapstone.coral_id.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mtoolbarResult.setNavigationOnClickListener {
            Toast.makeText(activity, "Close result fragment", Toast.LENGTH_SHORT).show()
            it.findNavController().navigate(R.id.action_resultFragment_to_detailFragment)
        }

        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[ResultViewModel::class.java]

        val resultAdapter = ResultAdapter()
        resultAdapter.setList(viewModel.getCorals())

        with(binding.rvResult) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = resultAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}