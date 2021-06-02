package com.bangkitcapstone.coral_id.ui.result

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkitcapstone.coral_id.R
import com.bangkitcapstone.coral_id.data.source.remote.response.SpeciesResponse
import com.bangkitcapstone.coral_id.databinding.FragmentResultBinding
import com.bangkitcapstone.coral_id.utils.SpeciesCallback
import com.bangkitcapstone.coral_id.viewmodel.ViewModelFactory

class ResultFragment : Fragment(), SpeciesCallback {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ResultViewModel
    private val factory = ViewModelFactory.getInstance()

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

        setRecyclerView()

        binding.mtoolbarResult.setNavigationOnClickListener {
            Toast.makeText(activity, "Close result fragment", Toast.LENGTH_SHORT).show()
            it.findNavController().navigate(R.id.action_resultFragment_to_detailFragment)
        }

        showLoading(true)

        activity?.let {
            viewModel = ViewModelProvider(
                it,
                factory
            )[ResultViewModel::class.java]
            viewModel.getAllSpecies().observe(viewLifecycleOwner, {
                Log.d("Lihatt species", it.toString())
                binding.rvResult.adapter.let { adapter ->
                    when (adapter) {
                        is ResultAdapter -> adapter.setList(it)
                    }
                }
                showLoading(false)
            })
            /*viewModel.getAllCorals().observe(viewLifecycleOwner, {
                Log.d("Lihatt corals", it.toString())
            })*/
        }
    }

    private fun setRecyclerView() {
        binding.rvResult.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ResultAdapter(this@ResultFragment)
        }
    }

    private fun showLoading(state: Boolean) {
        binding.apply {
            if (state) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    override fun onItemClicked(species: SpeciesResponse) {
        findNavController().navigate(R.id.action_resultFragment_to_detailFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}