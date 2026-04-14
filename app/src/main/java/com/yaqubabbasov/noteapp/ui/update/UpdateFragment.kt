package com.yaqubabbasov.noteapp.ui.update

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
import androidx.navigation.fragment.navArgs
import com.yaqubabbasov.noteapp.databinding.FragmentUpdateBinding
import com.yaqubabbasov.noteapp.ui.update.update_contract.UpdateEffect
import com.yaqubabbasov.noteapp.ui.update.update_contract.UpdateIntent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpdateFragment : Fragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UpdateViewModel by viewModels()
    private val args: UpdateFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val note = args.Note
        viewModel.intent(UpdateIntent.LoadItem(note))
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        observeState()
        observeEffect()
        binding.updateButton.setOnClickListener {
            val title = binding.titleEditText.text.toString().trim()
            val content = binding.contentEditText.text.toString().trim()
            viewModel.intent(UpdateIntent.UpdateItem(note.id, title, content))

        }


    }
    fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    binding.noteProgressBar.isVisible=it.isLoading
                    binding.updateConstraintLayout.isVisible = !it.isLoading
                    binding.titleEditText.setText(it.title)
                    binding.contentEditText.setText(it.content)

                }
            }
        }
    }
    fun observeEffect(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.effect.collect{effect->
                    when(effect){
                        is UpdateEffect.ShowToast -> {
                            Toast.makeText(
                                requireContext(),
                                effect.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is UpdateEffect.NavigateBack -> {
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


