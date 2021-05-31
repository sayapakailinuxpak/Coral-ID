package com.bangkitcapstone.coral_id.utils

import com.bangkitcapstone.coral_id.R
import com.bangkitcapstone.coral_id.data.DataCoral
import com.bangkitcapstone.coral_id.data.DataSlider
import com.bangkitcapstone.coral_id.ui.slider.SlideAdapter

object DataDummy {

    val slideAdapter = SlideAdapter(
        listOf(
            DataSlider(
                "Coral-ID",
                "Coral-ID is initially built for Bangkit 2021 Capstone Project." +
                        "\n (B21-CAP0226)",
                R.drawable.image_bangkit
            ),
            DataSlider(
                "With this App",
                "The beauty of the ocean in your hand",
                R.drawable.ic_summer
            ),
            DataSlider(
                "Did you know?",
                "Coral reefs are disappearing twice as fast as the rainforest. About one fifth of the world's coral reefs has already been lost or severely damaged.",
                R.drawable.ic_question
            ),
            DataSlider(
                "Coral Reefs are Crucial",
                "Even though coral reefs comprises less than 1% area of the oceans, they are home of about 25% of all marine species.",
                R.drawable.ic_global_warming
            )
        )
    )

    fun generateDummyCoral(): List<DataCoral> {
        val listCoral = ArrayList<DataCoral>()

        listCoral.add(
            DataCoral(
                1,
                "Hard Coral",
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
                "Tersebar di seluruh perairan Indonesia, jenis ini sangat umum dijumpai di daerah tubir.",
                "https://miro.medium.com/max/3000/0*9hoWTBVQ27qeAtUo"
            )
        )

        listCoral.add(
            DataCoral(
                2,
                "Hard Coral",
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
                "Tersebar di seluruh perairan Indonesia, jenis ini sangat umum dijumpai di daerah tubir.",
                "https://miro.medium.com/max/3000/0*9hoWTBVQ27qeAtUo"
            )
        )

        return listCoral
    }
}