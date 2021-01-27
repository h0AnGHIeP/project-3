package com.edu.hoang.ui.doctor

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.edu.hoang.databinding.FragmentDoctorBinding

class DoctorDetailsFragment : Fragment() {
    private val requestPhoneCallCode = 11
    private lateinit var viewModel: DoctorDetailsViewModel
    private var _binding: FragmentDoctorBinding? = null
    private val binding: FragmentDoctorBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(DoctorDetailsViewModel::class.java)
        _binding = FragmentDoctorBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.contact.observe(viewLifecycleOwner) {
            setPhoneClickListener()
        }
        return binding.root
    }

    private fun setPhoneClickListener() {
        binding.btnCall.setOnClickListener {
            val call = Intent(Intent.ACTION_CALL).apply {
                data = Uri.parse("tel:$it")
            }
            if (ContextCompat.checkSelfPermission(
                    requireActivity(), Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CALL_PHONE),
                    requestPhoneCallCode
                )
            } else startActivity(call);

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}