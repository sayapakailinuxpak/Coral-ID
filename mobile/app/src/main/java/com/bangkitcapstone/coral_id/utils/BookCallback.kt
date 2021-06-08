package com.bangkitcapstone.coral_id.utils

import com.bangkitcapstone.coral_id.data.source.local.entity.CoralsEntity

interface BookCallback {
    fun onItemClicked(coral: CoralsEntity)
}