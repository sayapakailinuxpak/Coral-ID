package com.bangkitcapstone.coral_id.ui.result

import androidx.lifecycle.ViewModel
import com.bangkitcapstone.coral_id.data.DataCoral
import com.bangkitcapstone.coral_id.utils.DataDummy

class ResultViewModel: ViewModel() {
    fun getCorals(): List<DataCoral> = DataDummy.generateDummyCoral()
}