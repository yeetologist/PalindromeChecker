package com.github.yeetologist.palindromechecker.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.yeetologist.palindromechecker.R
import com.github.yeetologist.palindromechecker.data.DataItem
import com.github.yeetologist.palindromechecker.databinding.ItemUserBinding

class UserListAdapter : ListAdapter<DataItem, UserListAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(result: DataItem)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    class MyViewHolder(private val binding: ItemUserBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: DataItem) {
            binding.tvItemUsername.text =
                context.getString(R.string.full_name, result.firstName, result.lastName)
            Glide.with(binding.root)
                .load(result.avatar)
                .into(binding.ivUserProfile)
            binding.tvItemEmail.text = result.email
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val result = getItem(position)
        holder.bind(result)
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(result)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}