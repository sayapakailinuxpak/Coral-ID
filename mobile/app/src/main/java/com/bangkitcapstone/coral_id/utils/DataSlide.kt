package com.bangkitcapstone.coral_id.utils

import com.bangkitcapstone.coral_id.R
import com.bangkitcapstone.coral_id.data.IntroSlide
import com.bangkitcapstone.coral_id.ui.slider.SlideAdapter

object DataSlide {
    val slideAdapter = SlideAdapter(
        listOf(
            IntroSlide(
                "Coral-ID",
                "The beauty of the ocean in your hand",
                R.drawable.ic_summer
            ),
            IntroSlide(
                "Did you know?",
                "Coral reefs are disappearing twice as fast as the rainforest. About one fifth of the world's coral reefs has already been lost or severely damaged.",
                R.drawable.ic_question
            ),
            IntroSlide(
                "Coral Reefs are Crucial",
                "Even though coral reefs comprises less than 1% area of the oceans, they are home of about 25% of all marine species.",
                R.drawable.ic_global_warming
            )
        )
    )
}