package com.jrodriguezva.mifilmo.ui.moviedetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jrodriguezva.mifilmo.databinding.ItemMessageBinding
import com.jrodriguezva.mifilmo.domain.model.Message
import com.jrodriguezva.mifilmo.utils.extensions.formatLarge

class MessagesAdapter : ListAdapter<Message, MessagesAdapter.MessageViewHolder>(
    diffCallback
) {
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bindTo(getItem(position))

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            ItemMessageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class MessageViewHolder(private val binding: ItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindTo(item: Message) {
            binding.apply {
                name.text = item.name
                date.text = item.date.formatLarge()
                text.text = item.text
            }
        }
    }

}

