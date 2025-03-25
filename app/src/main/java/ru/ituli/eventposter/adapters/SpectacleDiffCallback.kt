package ru.ituli.eventposter.adapters

import androidx.recyclerview.widget.DiffUtil
import ru.ituli.eventposter.spectacle.Spectacle

class SpectacleDiffCallback : DiffUtil.ItemCallback<Spectacle>() {

    override fun areItemsTheSame(oldItem: Spectacle, newItem: Spectacle): Boolean {
        return oldItem.header == newItem.header
    }

    override fun areContentsTheSame(oldItem: Spectacle, newItem: Spectacle): Boolean {
        return oldItem.date == newItem.date &&
                oldItem.place == newItem.place &&
                oldItem.description == newItem.description
    }

}