package com.example.androidhomeassignment

import android.content.Intent
import android.os.Binder
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidhomeassignment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        var main_Page = binding.root
        setContentView(main_Page)
        binding.buttonCustomer.setOnClickListener {
            val customerPage = Intent(applicationContext,MainActivity2::class.java)
            startActivity(customerPage)
        }
        binding.buttonProduct.setOnClickListener {
            var productPage = Intent(applicationContext,MainActivity3::class.java)
            startActivity(productPage)
        }
    }
}