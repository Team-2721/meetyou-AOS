package com.team2127.presentation.ui.main.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.team2127.domain.model.main.RoomResultInfo
import com.team2127.presentation.R
import com.team2127.presentation.databinding.ItemMainRoomBinding

class RoomListAdapter(
    private val deleteRoom : (placeId: Int) -> Unit
) :
    ListAdapter<RoomResultInfo, RoomListAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMainRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(
        private val binding: ItemMainRoomBinding
    ): RecyclerView.ViewHolder(binding.root) {
        init {
            val toggle = binding.btnItemMainRoomMemoMore
            toggle.setOnClickListener {
                if (toggle.isChecked){
                    binding.tvItemMainRoomMemo.maxLines = 1
                } else {
                    binding.tvItemMainRoomMemo.maxLines = 100
                }
            }
        }
        fun bind(item: RoomResultInfo) {
            binding.tvItemMainRoomTitle.text = item.roomName
            binding.tvItemMainRoomMemo.text = item.comment
            binding.tvItemMainRoomDate.text = item.date
            binding.tvItemMainRoomCode.text = String.format(itemView.context.getString(R.string.main_room_code), item.code)
            binding.tvItemMainRoomStatus.text = item.status

            binding.btnItemMainRoomDelete.setOnClickListener {
                deleteRoom(item.roomId)
            }
        }
    }

    companion object {
        private val DiffCallback: DiffUtil.ItemCallback<RoomResultInfo> =
            object : DiffUtil.ItemCallback<RoomResultInfo>() {
                override fun areItemsTheSame(
                    oldItem: RoomResultInfo,
                    newItem: RoomResultInfo
                ): Boolean {
                    return oldItem.roomId == newItem.roomId
                }

                override fun areContentsTheSame(
                    oldItem: RoomResultInfo,
                    newItem: RoomResultInfo
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }

}
