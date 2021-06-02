package com.bangkitcapstone.coral_id.data.source.remote.voremote

data class VoApi<T>(val status: VoStatus, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): VoApi<T> = VoApi(VoStatus.SUCCESS, data, null)

        fun <T> error(msg: String?, data: T?): VoApi<T> =
            VoApi(VoStatus.ERROR, data, msg)
    }
}