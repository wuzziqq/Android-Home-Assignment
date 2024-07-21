package com.example.androidhomeassignment

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidhomeassignment.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {

    lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        var customer_Page = binding.root
        setContentView(customer_Page)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        var context = this;
        var db = DataBaseHelper(context)
        binding.btnSave.setOnClickListener {
            var etName = binding.customerName.text.toString()
            var etCountry = binding.customerCountry.text.toString()
            if (etName.isNotEmpty() && etCountry.isNotEmpty())
            {
                var customer = Customer(etName,etCountry,0)
                db.insertData(customer)
                UpdateData(db)
                binding.customerName.text.clear()
                binding.customerCountry.text.clear()
            }else
            {
                Toast.makeText(applicationContext,"Fill the empty context",Toast.LENGTH_LONG).show()
            }
        }

        binding.btnDel.setOnClickListener {
            var id = binding.numberText.text.toString()
            db.deleteData(id.toInt())
            UpdateData(db)
            binding.numberText.text.clear()
        }

        UpdateData(db)
    }

    fun UpdateData(db:DataBaseHelper) {
        var data = db.readData()
        binding.result.text = ""
        for (i in 0 until data.size){
            binding.result.append(data.get(i).id.toString()+ " "
                    +data.get(i).customerName + " " + data.get(i).country + " " + data.get(i).total + "\n")
        }
    }

}