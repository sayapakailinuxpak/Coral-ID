package com.bangkitcapstone.coral_id.utils

import com.bangkitcapstone.coral_id.R
import com.bangkitcapstone.coral_id.data.model.DataSlider
import com.bangkitcapstone.coral_id.data.source.remote.response.CoralsResponse
import com.bangkitcapstone.coral_id.data.source.remote.response.SpeciesResponse
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

    fun generateDummyCoral(): List<CoralsResponse> {
        val listCoral = ArrayList<CoralsResponse>()

        listCoral.add(
            CoralsResponse(
                1,
                "Acropora",
                "Acroporidae",
                "Lamark",
                1816,
                "Koloni dapat membentuk tegakan beberapa meter. Mereka arborescent, terdiri dari cabang silinder yang jarang membelah. Corallites berbentuk tabung; corallites aksial berbeda. Spesies ini memiliki warna coklat pucat atau cokelat dengan corallites aksial putih.",
                "Acropora muricata di Indo-Pasifik memiliki bentuk pertumbuhan yang sama tetapi corallites memiliki dinding yang lebih tebal dan bibir lebih membulat hingga radial corallites.",
                "Lereng terumbu bagian atas hingga tengah dan laguna dengan air jernih.",
                1
            )
        )

        listCoral.add(
            CoralsResponse(
                2,
                "Acropora",
                "Acroporidae",
                "Lamark",
                1816,
                "Spesies ini membentuk koloni terbesar dari semua Acropora (umumnya berdiameter 4 meter, tinggi 2 meter, dengan alas setebal 0,4 meter) dengan cabang meruncing yang sejajar, miring miring, dan sangat tebal. Cabang diratakan secara horizontal ke arah ekstremitasnya. Corallites berbentuk tabung dan panjangnya tidak beraturan. Corallites aksial, jika terbentuk sama sekali, tidak jelas. Memiliki warna tan atau coklat pucat dengan corallites aksial putih.",
                "Acropora prolifera, yang lebih kecil, tidak seperti elkhorn, tetapi memiliki corallites radial yang serupa.",
                "Lereng terumbu luar yang dangkal terkena aksi gelombang.",
                2
            )
        )
        return listCoral
    }

    fun generateDummySpecies(): List<SpeciesResponse> {
        val listSpecies = ArrayList<SpeciesResponse>()

        listSpecies.add(
            SpeciesResponse(
                1,
                "Acropora cervicornis",
                listOf(1)
            )
        )

        listSpecies.add(
            SpeciesResponse(
                2,
                "Acropora palmata",
                listOf(2)
            )
        )
        return listSpecies
    }
}