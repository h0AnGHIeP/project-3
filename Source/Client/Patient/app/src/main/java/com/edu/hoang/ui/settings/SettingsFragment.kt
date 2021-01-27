package com.edu.hoang.ui.settings

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import com.edu.hoang.databinding.FragmentSettingsBinding
import com.edu.hoang.databinding.ItemRemindTimeBinding
import com.edu.hoang.store.data.DrugType
import com.edu.hoang.store.data.RemindTime
import com.edu.hoang.store.formatTime
import com.edu.hoang.ui.TimePickerFragment

class SettingsFragment : Fragment() {
    private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var remindAdapter: RemindAdapter
    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        settingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = settingsViewModel
        setAdapter()
        settingsViewModel.remindTime.observe(viewLifecycleOwner) {
            remindAdapter.submitList(it)
        }
        settingsViewModel.renderRemindTime()

        return binding.root
    }

    private fun setAdapter() {
        remindAdapter = RemindAdapter(
            layoutInflater, settingsViewModel, childFragmentManager
        )
        binding.remindTimeRecyclerView.adapter = remindAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    class RemindAdapter(
        private val inflater: LayoutInflater,
        private val viewModel: SettingsViewModel,
        private val fragmentManager: FragmentManager
    ) : ListAdapter<Pair<RemindTime, DrugType>, RemindTimeViewHolder>(
        RemindTimeDiff()
    ) {
        override fun onCreateViewHolder(
            parent: ViewGroup, viewType: Int
        ): RemindTimeViewHolder {
            val view = ItemRemindTimeBinding.inflate(inflater, parent, false)
            return RemindTimeViewHolder(view, viewModel, fragmentManager)
        }

        override fun onBindViewHolder(holder: RemindTimeViewHolder, position: Int) {
            val time = getItem(position)
            holder.bind(time)
        }

    }

    class RemindTimeViewHolder(
        private val binding: ItemRemindTimeBinding,
        private val viewModel: SettingsViewModel,
        private val fragmentManager: FragmentManager
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(pair: Pair<RemindTime, DrugType>) {
            binding.remindTime = pair.first
            binding.drugType = pair.second
            binding.btnTimePicker.setOnClickListener {
                TimePickerFragment { _, hourOfDay, minute ->
                    viewModel.updateTime(pair.first, hourOfDay, minute)
                    binding.textTime.text = formatTime(hourOfDay, minute)
                }.show(fragmentManager, null)
            }
            binding.btnDelete.setOnClickListener {
                viewModel.deleteTime(pair.first)
            }
            binding.btnDisable.setOnClickListener {
                viewModel.disableRemind(pair.first)
            }
            binding.btnEnable.setOnClickListener {
                viewModel.enableRemind(pair.first)
            }
        }
    }


    class RemindTimeDiff : DiffUtil.ItemCallback<Pair<RemindTime, DrugType>>() {

        override fun areItemsTheSame(
            oldItem: Pair<RemindTime, DrugType>, newItem: Pair<RemindTime, DrugType>
        ): Boolean {
            return oldItem.first.id == newItem.first.id && oldItem.second.id == newItem.second.id
        }

        override fun areContentsTheSame(
            oldItem: Pair<RemindTime, DrugType>, newItem: Pair<RemindTime, DrugType>
        ): Boolean {
            return oldItem.first == newItem.first && oldItem.second == newItem.second
        }

    }
}