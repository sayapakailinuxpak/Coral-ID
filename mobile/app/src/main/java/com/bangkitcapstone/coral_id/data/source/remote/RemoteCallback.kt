package com.bangkitcapstone.coral_id.data.source.remote

import com.bangkitcapstone.coral_id.data.source.remote.response.CoralsResponse
import com.bangkitcapstone.coral_id.data.source.remote.response.SpeciesResponse

object RemoteCallback {
    interface LoadAllSpeciesCallback {
        fun onAllSpeciesReceived(speciesResponse: List<SpeciesResponse>)
    }

    interface LoadAllCoralsCallback {
        fun onAllCoralsReceived(coralsResponse: List<CoralsResponse>)
    }
}