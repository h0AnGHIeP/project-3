package com.edu.hoang.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.edu.hoang.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private lateinit var viewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null
    private val binding: FragmentDashboardBinding
        get() = _binding!!

    companion object {
        const val PERSONAL_ID_KEY = "com.edu.hoang.ui.dashboard.PERSONAL_ID_KEY"
        private var personalId = 0L
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val argId = arguments?.getLong(PERSONAL_ID_KEY)
        if (argId != null) personalId = argId
        val viewModelFactory = DashboardViewModelFactory(personalId)
        viewModel = ViewModelProvider(
            this, viewModelFactory
        ).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}