package com.bangkitcapstone.coral_id.data.source.remote

import com.bangkitcapstone.coral_id.data.source.remote.response.CoralsResponse
import com.bangkitcapstone.coral_id.data.source.remote.response.PredictionResponse

object RemoteCallback {
    interface LoadAllCoralsCallback {
        fun onAllCoralsReceived(coralsResponse: List<CoralsResponse>)
    }

    interface LoadCoralByIdCallback {
        fun onCoralByIdReceived(coralsResponse: CoralsResponse)
    }

    interface LoadPredictionCoral {
        fun onredictionCoralReceived(predictionResponse: List<PredictionResponse>)
    }
}