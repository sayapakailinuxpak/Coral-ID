package com.bangkitcapstone.coral_id.data.source.remote

import com.bangkitcapstone.coral_id.data.source.remote.response.CoralsResponse
import com.bangkitcapstone.coral_id.data.source.remote.response.PredictionResponse

object RemoteCallback {

    interface LoadPredictionCoral {
        fun onredictionCoralReceived(predictionResponse: List<PredictionResponse>)
    }
}