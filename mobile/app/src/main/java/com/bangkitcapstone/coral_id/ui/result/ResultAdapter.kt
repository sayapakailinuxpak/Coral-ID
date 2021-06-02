package com.bangkitcapstone.coral_id.ui.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkitcapstone.coral_id.R
import com.bangkitcapstone.coral_id.data.source.remote.response.SpeciesResponse
import com.bangkitcapstone.coral_id.databinding.ItemResultBinding
import com.bangkitcapstone.coral_id.utils.SpeciesCallback
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ResultAdapter(private val callback: SpeciesCallback) : RecyclerView.Adapter<ResultAdapter.FollowViewHolder>() {

    private val list = ArrayList<SpeciesResponse>()

    fun setList(corals: List<SpeciesResponse>) {
        list.clear()
        list.addAll(corals)
        notifyDataSetChanged()
    }

    inner class FollowViewHolder(private val binding: ItemResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(species: SpeciesResponse) {
            binding.apply {
                binding.textCoralFullNameResult.text = species.name
                binding.textCoralType.text = species.id.toString()
                Glide.with(itemView.context)
                    .load(R.drawable.coral_image)
                    .apply(RequestOptions().override(60, 60))
                    .into(binding.imageCoralResult)
                itemView.setOnClickListener{
                    callback.onItemClicked(species)
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