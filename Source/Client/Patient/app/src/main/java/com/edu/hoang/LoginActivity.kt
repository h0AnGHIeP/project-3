package com.edu.hoang

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.edu.hoang.databinding.ActivityLoginBinding
import com.edu.hoang.net.NetworkService
import com.edu.hoang.store.PersonalRepository

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val localId = (PersonalRepository.localId() ?: 0)
        binding.editTextId.setAutofillHints(localId.toString())

        binding.btnLogin.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                try {
                    val id = binding.editTextId.text.toString().toLong()
                    if (id > 0) {
                        it.putExtra(MainActivity.PERSONAL_ID, id)
                        startActivity(it)
                    }
                } catch (e: NumberFormatException) {
                    Toast.makeText(
                        this@LoginActivity,
                        R.string.number_format_exception,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.textIp.text=resources.getText(R.string.host_server)
    }

}