package ru.ituli.eventposter.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isGone
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.ituli.eventposter.R
import ru.ituli.eventposter.databinding.SpectacleCardBinding
import ru.ituli.eventposter.spectacle.Spectacle
import ru.ituli.eventposter.ui.ExtraInfoView

class ChoiceSpectacleAdapter :
    ListAdapter<Spectacle, ChoiceSpectacleAdapter.ViewHolder>(SpectacleDiffCallback()) {

    class ViewHolder(val binding: SpectacleCardBinding) : RecyclerView.ViewHolder(binding.root) {

        private fun changeText(view: View, emoji: String, newText: String?) = when {
            newText == null -> view.isGone = true
            view is TextView || view is ExtraInfoView -> {
                if (view is TextView) view.text = "$emoji $newText"
                if (view is ExtraInfoView) view.text = "$emoji $newText"
                view.requestLayout()
                view.isGone = false
            }
            else -> throw IllegalArgumentException("Unsupported view type: ${view::class.java.simpleName}")
        }

        fun bind(spectacle: Spectacle) = with(binding) {
            Glide.with(imageView.context).clear(imageView)
            Glide.with(imageView.context)
                .load(spectacle.image)
                .placeholder(R.drawable.placeholder)
                .override(imageView.width, imageView.height)
                .into(imageView)

            changeText(time, "🕐", spectacle.date)
            changeText(cost, "💸", spectacle.cost)
            changeText(place, "➣", spectacle.place)
            header.text = spectacle.header
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder = ViewHolder(
        SpectacleCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder) {
        bind(getItem(position))
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemCount(): Int = currentList.size
}