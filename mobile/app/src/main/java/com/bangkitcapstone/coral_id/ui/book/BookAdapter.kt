package com.bangkitcapstone.coral_id.ui.book

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkitcapstone.coral_id.R
import com.bangkitcapstone.coral_id.data.source.remote.response.CoralsResponse
import com.bangkitcapstone.coral_id.databinding.ItemBookBinding
import com.bangkitcapstone.coral_id.utils.BookCallback
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class BookAdapter(private val callback: BookCallback) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    private val list = ArrayList<CoralsResponse>()

    fun setList(corals: List<CoralsResponse>?) {
        if (corals == null) return
        this.list.clear()
        this.list.addAll(corals)
        notifyDataSetChanged()
    }

    inner class BookViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(coral: CoralsResponse) {
            binding.apply {
                binding.txtBookFullname.text = coral.fullName
                binding.txtBookType.text = coral.coralType
                Glide.with(itemView.context)
                    .load(R.drawable.coral_image)
                    .apply(RequestOptions().override(60, 60))
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
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}