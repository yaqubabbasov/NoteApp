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
        observestate()
        binding.updateButton.setOnClickListener {
            val title = binding.texttitle.text.toString().trim()
            val content = binding.textdescribe.text.toString().trim()
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
            viewModel.intent(UpdateIntent.UpdateItem(note.id, title, content))

        }


    }
    fun observestate(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect{
                    if (it.isUpdate){
                        Toast.makeText(requireContext(), "Updated", Toast.LENGTH_SHORT).show()
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


