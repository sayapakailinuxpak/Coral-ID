package com.bangkitcapstone.coral_id.ui.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkitcapstone.coral_id.R
import com.bangkitcapstone.coral_id.data.source.local.entity.CoralsEntity
import com.bangkitcapstone.coral_id.data.source.local.volocal.Resource
import com.bangkitcapstone.coral_id.data.source.local.volocal.Status
import com.bangkitcapstone.coral_id.databinding.FragmentBookBinding
import com.bangkitcapstone.coral_id.utils.BookCallback
import com.bangkitcapstone.coral_id.viewmodel.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class BookFragment : DaggerFragment(), BookCallback {

    private var _binding: FragmentBookBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BookViewModel

    @Inject
    lateinit var factory: ViewModelFactory

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
            viewModel.getAllCorals().observe(viewLifecycleOwner, coralObserver)
        }
    }

    private val coralObserver = Observer<Resource<PagedList<CoralsEntity>>> { corals ->
        if (corals != null) {
            when (corals.status) {
                Status.LOADING -> showLoading(true)
                Status.SUCCESS -> {
                    showLoading(false)
                    binding.rvCoralsBook.adapter.let { adapter ->
                        when (adapter) {
                            is BookAdapter -> {
                                adapter.submitList(corals.data)
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
                Status.ERROR -> {
                    showLoading(true)
                    Toast.makeText(
                        context,
                        "Check your internet connection",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onItemClicked(coral: CoralsEntity) {
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
}