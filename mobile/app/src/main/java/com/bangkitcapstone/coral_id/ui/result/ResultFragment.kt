package com.bangkitcapstone.coral_id.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkitcapstone.coral_id.R
import com.bangkitcapstone.coral_id.data.source.remote.response.PredictionResponse
import com.bangkitcapstone.coral_id.databinding.FragmentResultBinding
import com.bangkitcapstone.coral_id.utils.PredictionCallback
import com.bangkitcapstone.coral_id.viewmodel.ViewModelFactory
import dagger.android.support.DaggerFragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject


class ResultFragment : DaggerFragment(), PredictionCallback {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ResultViewModel
    private var imageFile: String? = null

    @Inject
    lateinit var factory: ViewModelFactory

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

        imageFile = arguments?.getString("uri")

        binding.mtoolbarResult.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        showLoading(true)

        activity?.let {
            viewModel = ViewModelProvider(
                it,
                factory
            )[ResultViewModel::class.java]
            uploadImage(imageFile)
        }
    }

    private fun uploadImage(path: String?) {
        if (path != null) {
            val file = File(path)
            val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val coralPic = MultipartBody.Part.createFormData("image", file.name, requestFile)
            viewModel.postImageCoral(coralPic).observe(viewLifecycleOwner, {
                if (it.isNullOrEmpty()) {
                    emptyData()
                } else {
                    binding.rvResult.adapter.let { adapter ->
                        when (adapter) {
                            is ResultAdapter -> adapter.setList(it)
                        }
                    }
                    //File(imageFile.toString()).delete()
                    showLoading(false)
                }
            })
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

    override fun onItemClicked(prediction: PredictionResponse) {
        val bundle = bundleOf("id" to prediction.id)
        findNavController().navigate(R.id.action_resultFragment_to_detailFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}