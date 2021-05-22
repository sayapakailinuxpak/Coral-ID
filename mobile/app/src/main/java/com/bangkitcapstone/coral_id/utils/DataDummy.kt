package com.bangkitcapstone.coral_id.utils

import com.bangkitcapstone.coral_id.data.DataCoral

object DataDummy {
    fun generateDummyCoral(): List<DataCoral> {
        val listCoral = ArrayList<DataCoral>()

        listCoral.add(
            DataCoral(
                1,
                "Acropora brueggemanni",
                "Acroporidae",
                "Acropora",
                "BROOK",
                "1983",
                "Koloni dengan percabangan arboresen, cabang dengan satu atau dua axial koralit. " +
                        "Percabangan tidak teratur dan mendekati kearah ujung mem-bengkak, " +
                        "berbenjol-benjol tidak teratur. Radial koralit membulat tersebar rapat tidak beraturan",
                "Coklat muda dan mendekati ujung memutih",
                "Jenis ini mudah dibedakan dari yang lain dengan ciri khas adanya benjolan-benjolan diujung " +
                        "dari percabangan yang kadang-kadang terdiri dari dua axial koralit.",
                "Tersebar di seluruh perairan Indonesia, jenis ini sangat umum dijumpai di daerah tubir."
            )
        )

        return listCoral
    }
}