package com.edu.hoang.ui.test

import androidx.lifecycle.*
import com.edu.hoang.store.TestResultRepository
import com.edu.hoang.store.data.TestDetails
import kotlinx.coroutines.*

class TestViewModel : ViewModel() {
    private val repository = TestResultRepository()

    private val _tests = MutableLiveData<List<TestDetails>>()

    val tests: LiveData<List<TestDetails>>
        get() = _tests


    private val currentJob = Job()
    private val currentScope = CoroutineScope(Dispatchers.IO + currentJob)

    fun renderLocalHistory() = currentScope.launch {
        val history = repository.localHistory()
        setUiValues(history)
    }

    fun renderRecentHistory() = currentScope.launch {
        val recent = repository.reloadLocal()
        setUiValues(recent)
    }

    private suspend fun setUiValues(list: List<TestDetails>) {
        withContext(Dispatchers.Main) {
            _tests.value = list
        }
    }

    fun addNewTest(
        description: String,
        hba1c: Float = 0f,
        random: Float = 0f,
        craving: Float = 0f,
        after: Float = 0f

    ) {
        currentScope.launch {
            val newTest = repository.createNewTestResult(
                description, hba1c, random, craving, after
            )
            val newList = mutableListOf<TestDetails>()
            tests.value?.forEach { newList.add(it) }
            newList.add(newTest)
            setUiValues(newList)
        }
    }

    override fun onCleared() {
        super.onCleared()
        currentJob.cancel()
    }
}