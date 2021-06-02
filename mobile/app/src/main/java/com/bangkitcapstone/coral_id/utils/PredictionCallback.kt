package com.bangkitcapstone.coral_id.utils

import com.bangkitcapstone.coral_id.data.source.remote.response.CoralsResponse
import com.bangkitcapstone.coral_id.data.source.remote.response.PredictionResponse

interface PredictionCallback {
    fun onItemClicked(prediction: PredictionResponse)
}