package com.edu.hoang.ui.prescription

import org.junit.Assert.assertEquals
import org.junit.Test

class PrescriptionHistoryViewModelTest {
    private val viewModel = PrescriptionHistoryViewModel()

    @Test
    fun `test fetch local`() {
        viewModel.renderLocalHistory()
        assertEquals(2, viewModel.history.value!!.size)
    }
}