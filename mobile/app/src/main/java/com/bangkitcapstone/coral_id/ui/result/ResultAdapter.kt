package com.bangkitcapstone.coral_id.ui.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkitcapstone.coral_id.data.source.remote.response.PredictionResponse
import com.bangkitcapstone.coral_id.databinding.ItemResultBinding
import com.bangkitcapstone.coral_id.utils.PredictionCallback
import com.bumptech.glide.Glide

class ResultAdapter(private val callback: PredictionCallback) :
    RecyclerView.Adapter<ResultAdapter.FollowViewHolder>() {

    private val list = ArrayList<PredictionResponse>()

    fun setList(corals: List<PredictionResponse>?) {
        if (corals == null) return
        this.list.clear()
        this.list.addAll(corals)
        notifyDataSetChanged()
    }

    inner class FollowViewHolder(private val binding: ItemResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(coral: PredictionResponse) {
            binding.apply {
                binding.textCoralFullNameResult.text = coral.fullName
                binding.textCoralType.text = coral.coralType
                Glide.with(itemView.context)
                    .load(coral.imagePath)
                    .into(binding.imageCoralResult)
                itemView.setOnClickListener {
                    callback.onItemClicked(coral)
                }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): FollowViewHolder {
        val view = ItemResultBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return FollowViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}