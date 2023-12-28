package com.github.yeetologist.palindromechecker.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.github.yeetologist.palindromechecker.R
import com.github.yeetologist.palindromechecker.databinding.ActivityMainBinding
import com.github.yeetologist.palindromechecker.util.PreferenceManager
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var preferenceManager: PreferenceManager
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(this)

        binding.btnNext.setOnClickListener {
            val intent = Intent(this@MainActivity, SecondActivity::class.java)
            if (binding.etName.text.toString() != "") {
                preferenceManager.setUsername(binding.etName.text.toString())
            }
            startActivity(intent)
        }

        binding.btnCheck.setOnClickListener {
            if (isPalindrome(binding.etPalindrome.text.toString())) {
                AlertDialog.Builder(this).apply {
                    setMessage(getString(R.string.isPalindrome))
                    setPositiveButton(getString(R.string.OK)) { _, _ ->
                    }
                    show()
                }
            } else {
                AlertDialog.Builder(this).apply {
                    setMessage(getString(R.string.notPalindrome))
                    setPositiveButton(getString(R.string.OK)) { _, _ ->
                    }
                    show()
                }
            }
        }
    }

    private fun isPalindrome(input: String): Boolean {
        val cleanInput = input.replace("\\s".toRegex(), "").lowercase(Locale.ROOT)
        return cleanInput == cleanInput.reversed()
    }
}