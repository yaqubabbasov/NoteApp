package com.yaqubabbasov.noteapp.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.yaqubabbasov.noteapp.R
import com.yaqubabbasov.noteapp.databinding.FragmentDetailBinding
import com.yaqubabbasov.noteapp.ui.detail.detail_contracts.DetailIntent
import com.yaqubabbasov.noteapp.ui.home.home_contract.HomeIntent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observestate()


        binding.savebutton.setOnClickListener {

            val title = binding.texttitle.text.toString().trim()
            val content = binding.textdescribe.text.toString().trim()
            binding.titlelayout.error = null
            binding.contentlayout.error = null
            var hasError = false


            if (title.isEmpty()) {
                binding.titlelayout.error = "Title is required"
                hasError = true
            }
            if (content.isEmpty()) {
                binding.contentlayout.error = "Content is required"
                hasError = true
            }
            if (title.isEmpty() || content.isEmpty()) return@setOnClickListener
            viewModel.intent(DetailIntent.SaveItem(title, content))
        }


    }

    fun observestate() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    if (it.isSaved) {
                        Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }


}