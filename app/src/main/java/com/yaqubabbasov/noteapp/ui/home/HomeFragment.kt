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
import com.yaqubabbasov.noteapp.ui.home.home_contract.HomeState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeadapter: HomeAdapter
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
        binding.noteRecycleView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        homeadapter= HomeAdapter({viewModel.intent(HomeIntent.DeleteItem(it.id))},requireContext(),emptyList())
        binding.noteRecycleView.adapter = homeadapter
        viewModel.intent(HomeIntent.LoadItem)
        observeState()
        binding.fabButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment)
        }
        searchView()



    }

    private fun searchView() {
        binding.noteSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
    fun render(state: HomeState) = with(binding) {
        when {
                state.isLoading -> {

                    noteRecycleView.visibility = View.GONE
                    productFoundText.visibility = View.GONE
                    productFoundImageView.visibility = View.GONE

                }

                state.notes.isNotEmpty() -> {
                    noteRecycleView.visibility = View.VISIBLE
                    productFoundText.visibility = View.GONE
                    productFoundImageView.visibility = View.GONE

                }

                else -> {
                    noteRecycleView.visibility = View.GONE
                    productFoundText.visibility = View.VISIBLE
                    productFoundImageView.visibility = View.VISIBLE

                }
        }

    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {state->
                render(state)
                homeadapter.updateList(state.notes)
            }
        }
    }

    /* override fun onResume() {
         super.onResume()
         viewModel.intent(HomeIntent.LoadItem)
     }
     */

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }
}