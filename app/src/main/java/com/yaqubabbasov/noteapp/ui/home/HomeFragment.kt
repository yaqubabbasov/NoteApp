package com.yaqubabbasov.noteapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.yaqubabbasov.noteapp.R
import com.yaqubabbasov.noteapp.databinding.FragmentHomeBinding
import com.yaqubabbasov.noteapp.ui.home.home_contract.HomeIntent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycleview.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        observestate()
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment)
        }
        searchview()


    }

    private fun searchview() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewModel.intent(HomeIntent.WordChanged(p0.toString()))
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                viewModel.intent(HomeIntent.WordChanged(p0.toString()))
                return true
            }

        })
    }

    private fun observestate() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                binding.recycleview.adapter = HomeAdapter({ item ->
                    item.id.let {
                        viewModel.intent(HomeIntent.DeleteItem(it))

                    }
                }, requireContext(), it.notes)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.intent(HomeIntent.LoadItem)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }
}