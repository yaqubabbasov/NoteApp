package com.yaqubabbasov.noteapp.ui.update

import android.os.Bundle
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
import androidx.navigation.fragment.navArgs
import com.yaqubabbasov.noteapp.R
import com.yaqubabbasov.noteapp.databinding.FragmentUpdateBinding
import com.yaqubabbasov.noteapp.ui.detail.detail_contracts.DetailEffect
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
        binding.texttitle.setText(note.title)
        binding.textdescribe.setText(note.content)
        binding.popbackbutton.setOnClickListener {
            findNavController().popBackStack()
        }
        observestate()
        observeeffect()
        binding.updateButton.setOnClickListener {
            val title = binding.texttitle.text.toString().trim()
            val content = binding.textdescribe.text.toString().trim()
            viewModel.intent(UpdateIntent.UpdateItem(note.id, title, content))

        }


    }
    fun observestate() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    binding.progressBar.visibility = if (it.isLoading) View.VISIBLE else View.GONE
                    binding.constraintLayout.visibility = if (it.isLoading) View.GONE else View.VISIBLE

                }
            }
        }
    }
    fun observeeffect(){
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


