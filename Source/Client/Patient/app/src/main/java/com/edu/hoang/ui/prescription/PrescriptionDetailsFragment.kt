package com.edu.hoang.ui.prescription

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import com.edu.hoang.R
import com.edu.hoang.databinding.FragmentPrescriptionBinding
import com.edu.hoang.databinding.ItemInstructionBinding
import com.edu.hoang.store.data.DrugType
import com.edu.hoang.store.data.Instruction
import com.edu.hoang.store.formatTime
import com.edu.hoang.ui.TimePickerFragment

class PrescriptionDetailsFragment : Fragment() {

    private lateinit var viewModel: PrescriptionDetailsViewModel
    private lateinit var listAdapter: InstructionListAdapter

    private var _binding: FragmentPrescriptionBinding? = null
    private val binding: FragmentPrescriptionBinding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(PrescriptionDetailsViewModel::class.java)
        _binding = FragmentPrescriptionBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        setClickListenerOnButtons()
        setAdapter()
        observeInstructions()
        observePrescriptionDetails()
        viewModel.renderLocalData()
        return binding.root
    }

    private fun setClickListenerOnButtons() {
        binding.btnHistory.setOnClickListener {
            findNavController().navigate(R.id.action_nav_prescription_to_nav_prescription_history)
        }
    }

    private fun setAdapter() {
        val diffCallback = InstructionItemCallback()
        listAdapter = InstructionListAdapter(
            diffCallback, layoutInflater, childFragmentManager, viewModel
        )
        binding.prescriptionEntryRecyclerView.adapter = listAdapter
    }

    private fun observePrescriptionDetails() {
        viewModel.prescriptionDetails.observe(viewLifecycleOwner) {
            binding.prescription = it.prescription
        }
    }

    private fun observeInstructions() {
        viewModel.prescriptionDetails.observe(this.viewLifecycleOwner) {
            listAdapter.submitList(it.instructions)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.renderLatestData()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    class InstructionListAdapter(
        callback: DiffUtil.ItemCallback<Pair<Instruction, DrugType>>,
        private val inflater: LayoutInflater,
        private val fragmentManager: FragmentManager,
        private val viewModel: PrescriptionDetailsViewModel
    ) : ListAdapter<Pair<Instruction, DrugType>, InstructionItemViewHolder>(callback) {

        override fun onCreateViewHolder(
            parent: ViewGroup, viewType: Int
        ): InstructionItemViewHolder {
            val holderBinding = ItemInstructionBinding.inflate(inflater, parent, false)
            return InstructionItemViewHolder(holderBinding, fragmentManager, viewModel)
        }

        override fun onBindViewHolder(
            holder: InstructionItemViewHolder, position: Int
        ) {
            val entry = getItem(position)
            holder.bindTo(entry)
        }

    }

    class InstructionItemViewHolder(
        private val binding: ItemInstructionBinding,
        private val fragmentManager: FragmentManager,
        private val viewModel: PrescriptionDetailsViewModel
    ) : RecyclerView.ViewHolder(binding.root) {
        private var timePicked = 0 to 0
        fun bindTo(pair: Pair<Instruction, DrugType>) {
            binding.apply {
                instruction = pair.first
                drug = pair.second
                btnPickTime.setOnClickListener {
                    TimePickerFragment { _, hourOfDay, minute ->
                        timePicked = hourOfDay to minute
                        binding.textTimePick.text = formatTime(hourOfDay, minute)
                    }.show(fragmentManager, null)
                }
                btnAdd.setOnClickListener {
                    viewModel.addRemindTime(
                        pair.first, timePicked.first, timePicked.second
                    )
                }
            }

        }
    }

    class InstructionItemCallback : DiffUtil.ItemCallback<Pair<Instruction, DrugType>>() {
        override fun areItemsTheSame(
            oldItem: Pair<Instruction, DrugType>, newItem: Pair<Instruction, DrugType>
        ): Boolean {
            return oldItem.first.id == newItem.first.id
        }

        override fun areContentsTheSame(
            oldItem: Pair<Instruction, DrugType>, newItem: Pair<Instruction, DrugType>
        ): Boolean {
            return oldItem.first == newItem.first
        }
    }
}