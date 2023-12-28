package com.github.yeetologist.palindromechecker.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.yeetologist.palindromechecker.databinding.ActivitySecondBinding
import com.github.yeetologist.palindromechecker.util.PreferenceManager

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(this)

        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        if (preferenceManager.getUsername() != null) {
            binding.tvUsername.text = preferenceManager.getUsername()
        }

        if (preferenceManager.getSelected() != null) {
            binding.tvSelectedName.text = preferenceManager.getSelected()
        }

        binding.btnChoose.setOnClickListener {
            startActivity(Intent(this@SecondActivity, ThirdActivity::class.java))
        }
    }
}