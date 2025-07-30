package com.mahvin.healthcare.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahvin.healthcare.model.StepItem
import com.mahvin.stresslezz.databinding.ItemStepBinding

class StepPagerAdapter(private val items: List<StepItem>) :
    RecyclerView.Adapter<StepPagerAdapter.StepViewHolder>() {

    inner class StepViewHolder(private val binding: ItemStepBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(step: StepItem) {
            binding.tvNumber.text = "Langkah ${step.number}"
            binding.tvAyat.text = step.ayat
            binding.tvArabic.text = step.arab
            binding.tvTranslation.text = step.arti
            binding.tvTafsir.text = step.tafsir
            binding.tvAction.text = step.aksi
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStepBinding.inflate(inflater, parent, false)
        return StepViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
