package com.yaqubabbasov.noteapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.yaqubabbasov.noteapp.databinding.FragmentDetailBinding
import com.yaqubabbasov.noteapp.ui.detail.detail_contracts.DetailEffect
import com.yaqubabbasov.noteapp.ui.detail.detail_contracts.DetailIntent
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

        observeState()
        observeEffect()
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }




        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString().trim()
            val content = binding.contentEditText.text.toString().trim()
            viewModel.intent(DetailIntent.SaveItem(title, content))
        }


    }

    fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    binding.noteProgressBar.isVisible=it.isLoading
                    binding.noteLayout.isVisible = !it.isLoading

                }
            }
        }
    }

    fun observeEffect(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.effect.collect{effect->
                    when(effect){
                        is DetailEffect.ShowToast -> {
                            Toast.makeText(
                                requireContext(),
                                effect.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is DetailEffect.NavigateBack -> {
                            findNavController().popBackStack()
                        }
                        else -> {}

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