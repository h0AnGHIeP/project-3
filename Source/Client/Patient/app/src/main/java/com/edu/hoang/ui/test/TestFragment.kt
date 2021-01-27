package com.edu.hoang.ui.test

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import com.edu.hoang.R
import com.edu.hoang.databinding.FragmentRecentTestBinding
import com.edu.hoang.databinding.ItemTestListBinding
import com.edu.hoang.store.data.TestDetails

class TestFragment : Fragment() {
    private lateinit var viewModel: TestViewModel
    private lateinit var testResultAdapter: TestListAdapter
    private var _binding: FragmentRecentTestBinding? = null
    private val binding: FragmentRecentTestBinding
        get() = _binding!!


    private var itemSelected = 1
    private val editTestIndex: Float
        get() = try {
            binding.editTest.text.toString().toFloat()
        } catch (e: Exception) {
            0f
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(TestViewModel::class.java)
        _binding = FragmentRecentTestBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        setAdapter()
        observeTestResults()
        viewModel.renderLocalHistory()
        setSelectListenerForSpinner()
        setAddButtonListener()
        return binding.root
    }

    private fun setAddButtonListener() {
        val descriptions = requireContext().resources.getStringArray(R.array.spinner_items)
        binding.btnAddTest.setOnClickListener {
            if (editTestIndex == 0f) return@setOnClickListener
            when (itemSelected) {
                0 -> viewModel.addNewTest(descriptions[0], hba1c = editTestIndex)
                1 -> viewModel.addNewTest(descriptions[1], random = editTestIndex)
                2 -> viewModel.addNewTest(descriptions[2], craving = editTestIndex)
                3 -> viewModel.addNewTest(descriptions[3], after = editTestIndex)
            }
        }
    }

    private fun setSelectListenerForSpinner() {
        ArrayAdapter.createFromResource(
            requireActivity(), R.array.spinner_items, android.R.layout.simple_spinner_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.testTypeSpinner.adapter = it
        }

        binding.testTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                itemSelected = position
                Log.i("TAG", "setAddButtonListener: $position")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                itemSelected = 0
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.renderRecentHistory()
    }

    private fun observeTestResults() {
        viewModel.tests.observe(viewLifecycleOwner) {
            testResultAdapter.submitList(it)
        }
    }

    private fun setAdapter() {
        testResultAdapter = TestListAdapter(TestItemCallback(), layoutInflater)
        binding.testRecyclerView.adapter = testResultAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    class TestListAdapter(
        callback: DiffUtil.ItemCallback<TestDetails>, private val inflater: LayoutInflater
    ) : ListAdapter<TestDetails, TestViewHolder>(callback) {
        override fun onCreateViewHolder(
            parent: ViewGroup, viewType: Int
        ): TestViewHolder {
            val holderBinding = ItemTestListBinding.inflate(
                inflater, parent, false
            )
            return TestViewHolder(holderBinding)
        }

        override fun onBindViewHolder(
            holder: TestViewHolder, position: Int
        ) {
            val test = getItem(position)
            holder.bindTo(test)
        }

    }


    class TestItemCallback : DiffUtil.ItemCallback<TestDetails>() {
        override fun areItemsTheSame(
            oldItem: TestDetails, newItem: TestDetails
        ): Boolean {
            return oldItem.index == newItem.index
        }

        override fun areContentsTheSame(
            oldItem: TestDetails, newItem: TestDetails
        ): Boolean {
            return oldItem == newItem
        }
    }

    class TestViewHolder(
        private val binding: ItemTestListBinding
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bindTo(testDetails: TestDetails) {
            binding.test = testDetails
        }
    }

}