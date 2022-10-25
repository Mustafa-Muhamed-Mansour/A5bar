package com.a5bar.ui.splash

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.a5bar.R
import com.a5bar.databinding.FragmentSplashBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private lateinit var viewModel: SplashViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initViewModel()
        retrieveViewModel()

    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity())[SplashViewModel::class.java]
    }

    private fun retrieveViewModel() {
        viewModel.delayTime().observe(viewLifecycleOwner) {
            if (it) {
//                Toast.makeText(requireContext(), "Done 5 sec", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_splashFragment_to_breakingNewsFragment)
            }
        }
    }

}