package com.edu.hoang.ui.prescription

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import com.edu.hoang.databinding.FragmentPrescriptionHistoryBinding
import com.edu.hoang.databinding.ItemPrescriptionBinding
import com.edu.hoang.store.data.Prescription
import kotlinx.coroutines.Job

class PrescriptionHistoryFragment : Fragment() {

    private lateinit var viewModel: PrescriptionHistoryViewModel
    private lateinit var prescriptionAdapter: PrescriptionAdapter
    private var _binding: FragmentPrescriptionHistoryBinding? = null
    private val binding: FragmentPrescriptionHistoryBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(PrescriptionHistoryViewModel::class.java)
        _binding = FragmentPrescriptionHistoryBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        setAdapter()
        observePrescriptionList()
       viewModel.renderLocalHistory()
        return binding.root
    }

    private fun observePrescriptionList() {
        viewModel.history.observe(viewLifecycleOwner) {
            prescriptionAdapter.submitList(it)
        }
    }

    private fun setAdapter() {
        prescriptionAdapter = PrescriptionAdapter(layoutInflater)
        binding.historyRecyclerView.adapter = prescriptionAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.renderCurrentHistory()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    class PrescriptionAdapter(private val inflater: LayoutInflater) : ListAdapter<Prescription, PrescriptionViewHolder>(
        PrescriptionDiff()
    ) {
        override fun onCreateViewHolder(
            parent: ViewGroup, viewType: Int
        ): PrescriptionViewHolder {
            val binding = ItemPrescriptionBinding.inflate(inflater, parent, false)
            return PrescriptionViewHolder(binding)
        }

        override fun onBindViewHolder(holder: PrescriptionViewHolder, position: Int) {
            val prescription = getItem(position)
            holder.bind(prescription)
        }

    }

    class PrescriptionViewHolder(private val binding: ItemPrescriptionBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {
        fun bind(data: Prescription) {
            binding.prescription = data
        }
    }


    class PrescriptionDiff : DiffUtil.ItemCallback<Prescription>() {
        override fun areItemsTheSame(
            oldItem: Prescription, newItem: Prescription
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Prescription, newItem: Prescription
        ): Boolean = oldItem == newItem

    }
}