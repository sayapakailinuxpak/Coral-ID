package com.bangkitcapstone.coral_id.utils

import com.bangkitcapstone.coral_id.data.source.remote.response.SpeciesResponse

interface SpeciesCallback {
    fun onItemClicked(species: SpeciesResponse)
}