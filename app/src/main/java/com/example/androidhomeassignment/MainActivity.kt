package com.example.androidhomeassignment

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Binder
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

        binding.buttonExport.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),1)
            } else {
                val db = DataBaseHelper(this)
                db.exportData(this,"customer ${System.currentTimeMillis()}")
            }

        }
    }
}