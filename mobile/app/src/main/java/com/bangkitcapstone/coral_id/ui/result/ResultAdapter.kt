package com.bangkitcapstone.coral_id.ui.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkitcapstone.coral_id.data.DataCoral
import com.bangkitcapstone.coral_id.databinding.ItemResultBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ResultAdapter : RecyclerView.Adapter<ResultAdapter.FollowViewHolder>() {

    private val list = ArrayList<DataCoral>()

    fun setList(corals: List<DataCoral>) {
        list.clear()
        list.addAll(corals)
        notifyDataSetChanged()
    }

    inner class FollowViewHolder(private val binding: ItemResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(coral: DataCoral) {

            binding.apply {
                binding.textCoralFullNameResult.text = coral.name
                binding.textCoralType.text = coral.type
                Glide.with(itemView.context)
                    .load(coral.image)
                    .apply(RequestOptions().override(60, 60))
                    .into(binding.imageCoralResult)
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