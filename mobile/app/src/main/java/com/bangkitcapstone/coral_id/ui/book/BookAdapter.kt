package com.bangkitcapstone.coral_id.ui.book

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkitcapstone.coral_id.R
import com.bangkitcapstone.coral_id.data.source.local.entity.CoralsEntity
import com.bangkitcapstone.coral_id.data.source.remote.response.CoralsResponse
import com.bangkitcapstone.coral_id.databinding.ItemBookBinding
import com.bangkitcapstone.coral_id.utils.BookCallback
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class BookAdapter(private val callback: BookCallback) :
    PagedListAdapter<CoralsEntity, BookAdapter.BookViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CoralsEntity>() {
            override fun areItemsTheSame(oldItem: CoralsEntity, newItem: CoralsEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CoralsEntity, newItem: CoralsEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class BookViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(coral: CoralsEntity) {
            binding.apply {
                binding.txtBookFullname.text = coral.fullName
                binding.txtBookType.text = coral.coralType
                Glide.with(itemView.context)
                    .load(R.drawable.coral_image)
                    .into(binding.imageBook)
                itemView.setOnClickListener {
                    callback.onItemClicked(coral)
                }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): BookViewHolder {
        val view = ItemBookBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val coral = getItem(position)
        if (coral != null) {
            holder.bind(coral)
        }
    }
}