package com.bangkitcapstone.coral_id.ui.slider

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkitcapstone.coral_id.data.model.DataSlider
import com.bangkitcapstone.coral_id.databinding.SlideItemContainerBinding
import com.bumptech.glide.Glide

class SlideAdapter(private val introSlide: List<DataSlider>) :
    RecyclerView.Adapter<SlideAdapter.SlideViewHolder>() {

    inner class SlideViewHolder(private val binding: SlideItemContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(introSlide: DataSlider) {
            with(binding) {
                textTitle.text = introSlide.title
                textDesc.text = introSlide.desc
                Glide.with(itemView.context)
                    .load(introSlide.image)
                    .into(imgSlider)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SlideViewHolder {
        val view = SlideItemContainerBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return SlideViewHolder(view)
    }

    override fun onBindViewHolder(holder: SlideViewHolder, position: Int) {
        holder.bind(introSlide[position])
    }

    override fun getItemCount(): Int = introSlide.size
}