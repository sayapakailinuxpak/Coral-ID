package com.bangkitcapstone.coral_id.ui.book

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkitcapstone.coral_id.R
import com.bangkitcapstone.coral_id.data.source.remote.response.CoralsResponse
import com.bangkitcapstone.coral_id.databinding.FragmentBookBinding
import com.bangkitcapstone.coral_id.databinding.FragmentDetailBinding
import com.bangkitcapstone.coral_id.ui.detail.DetailViewModel
import com.bangkitcapstone.coral_id.ui.result.ResultAdapter
import com.bangkitcapstone.coral_id.utils.BookCallback
import com.bangkitcapstone.coral_id.viewmodel.ViewModelFactory

class BookFragment : Fragment(), BookCallback {

    private var _binding: FragmentBookBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BookViewModel
    private val factory = ViewModelFactory.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()


        binding.mtoolbarBook.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        showLoading(true)

        activity?.let {
            viewModel = ViewModelProvider(
                it,
                factory
            )[BookViewModel::class.java]
            viewModel.getAllCorals().observe(viewLifecycleOwner, {
                if (it.isNullOrEmpty()) {
                    emptyData()
                } else {
                    binding.rvCoralsBook.adapter.let { adapter ->
                        when (adapter) {
                            is BookAdapter -> adapter.setList(it)
                        }
                    }
                    showLoading(false)
                }
            })
        }
    }

    override fun onItemClicked(coral: CoralsResponse) {
        val bundle = bundleOf("id" to coral.id)
        findNavController().navigate(R.id.action_bookFragment_to_detailFragment, bundle)
    }

    private fun setRecyclerView() {
        binding.rvCoralsBook.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = BookAdapter(this@BookFragment)
        }
    }

    private fun showLoading(state: Boolean) {
        binding.apply {
            if (state) {
                cardProcessingMlLoading.visibility = View.VISIBLE
            } else {
                cardProcessingMlLoading.visibility = View.GONE
            }
        }
    }

    private fun emptyData() {
        binding.emptyData.visibility = View.VISIBLE
        binding.cardProcessingMlLoading.visibility = View.GONE
    }
}