package com.yaqubabbasov.noteapp.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.yaqubabbasov.noteapp.data.local.entity.Note
import com.yaqubabbasov.noteapp.databinding.NoteCardBinding

class HomeAdapter(val itemClicked: (Note) -> Unit, val context: Context, var list: List<Note>) :
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
        holder.binding.cardTitleTextView.text = item.title
        holder.binding.cardContentTextView.text = item.content
        val gecis = HomeFragmentDirections.actionHomeFragmentToUpdateFragment(item)
        holder.binding.noteCardView.setOnClickListener {
            findNavController(it).navigate(gecis)

        }
        holder.binding.cardDeleteButton.setOnClickListener {
            itemClicked(item)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun updateList(list1: List<Note>){
        list = list1
        notifyDataSetChanged()

    }


}