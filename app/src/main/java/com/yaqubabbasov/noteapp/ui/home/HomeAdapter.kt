package com.yaqubabbasov.noteapp.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.yaqubabbasov.noteapp.data.local.entity.Note
import com.yaqubabbasov.noteapp.databinding.NoteCardBinding

class HomeAdapter(val İtemClicked: (Note) -> Unit, val context: Context, var list: List<Note>) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    class HomeViewHolder(var binding: NoteCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int,
    ): HomeViewHolder {
        val card = NoteCardBinding.inflate(LayoutInflater.from(context), p0, false)
        return HomeViewHolder(card)

    }

    override fun onBindViewHolder(
        holder: HomeViewHolder,
        p1: Int,
    ) {
        val item = list[p1]
        holder.binding.titletext.text = item.title
        holder.binding.contenttext.text = item.content
        val gecis = HomeFragmentDirections.actionHomeFragmentToUpdateFragment(item)
        holder.binding.cardview.setOnClickListener {
            findNavController(it).navigate(gecis)

        }
        holder.binding.deletebutton.setOnClickListener {
            İtemClicked(item)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }


}