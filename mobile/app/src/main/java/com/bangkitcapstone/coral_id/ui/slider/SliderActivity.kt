package com.bangkitcapstone.coral_id.ui.slider

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.bangkitcapstone.coral_id.R
import com.bangkitcapstone.coral_id.databinding.ActivitySliderBinding
import com.bangkitcapstone.coral_id.ui.home.HomeActivity
import com.bangkitcapstone.coral_id.utils.DataSlide

class SliderActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySliderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySliderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences: SharedPreferences = getSharedPreferences("Coral-id", Context.MODE_PRIVATE)

        if (!preferences.getBoolean(IS_FIRST_RUN, true)) {
            Intent(applicationContext, HomeActivity::class.java).also {
                startActivity(it)
            }
            finish()
        }

        binding.viewPager2.adapter = DataSlide.slideAdapter
        setIndicator()
        setCurrentIndicator(0)
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position < DataSlide.slideAdapter.itemCount - 1) {
                    binding.btnSkip.text = "Skip"
                } else {
                    binding.btnSkip.text = "Done"
                }
                binding.btnSkip.setOnClickListener {
                    preferences.edit()
                        .putBoolean(IS_FIRST_RUN, false)
                        .apply()
                    Log.d("lihat", preferences.getBoolean(IS_FIRST_RUN, true).toString())
                    if (!preferences.getBoolean(IS_FIRST_RUN, true)) {
                        Intent(applicationContext, HomeActivity::class.java).also {
                            startActivity(it)
                        }
                        finish()
                    }
                }
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
    }

    private fun setIndicator() {
        val indicators = arrayOfNulls<ImageView>(DataSlide.slideAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
                this?.layoutParams = layoutParams
            }
            binding.dotsContainer.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = binding.dotsContainer.childCount
        for (i in 0 until childCount) {
            val imageView = binding.dotsContainer.get(i) as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
            }
        }
    }

    companion object {
        private const val IS_FIRST_RUN = "isFirstRun"
    }
}