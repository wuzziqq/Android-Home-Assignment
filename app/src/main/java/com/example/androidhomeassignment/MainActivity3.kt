package com.example.androidhomeassignment

import android.R.string.no
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.androidhomeassignment.databinding.ActivityMain3Binding


class MainActivity3 : AppCompatActivity() {
    lateinit var binding: ActivityMain3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main3)
        var binding = ActivityMain3Binding.inflate(layoutInflater)
        var production_Page = binding.root
        setContentView(production_Page)
        var price = 0
        val items = mutableListOf("")
        var context = this;
        var db = DataBaseHelper(context)
        var data = db.readData()
        for (i in 0 until data.size) {
            if (data.size <= 0) return
            items += (data.get(i).customerName.toString())

        }

        val linearLayout: LinearLayout = findViewById(R.id.linearLayout)

        linearLayout.setVisibility(View.INVISIBLE);


        binding.closeLayout.setOnClickListener {
            linearLayout.setVisibility(View.INVISIBLE);
        }

        binding.btnRim.setOnClickListener {
            linearLayout.setVisibility(View.VISIBLE);
            price = 1400

        }

        binding.btnSound.setOnClickListener {
            linearLayout.setVisibility(View.VISIBLE);
            price = 1200
        }

        binding.btnMirror.setOnClickListener {
            linearLayout.setVisibility(View.VISIBLE);
            price = 1000
        }

        var multiply: (Int, Int) -> Int = { x, y -> x * y }
        var selectedItem = ""
        val autoComplete: AutoCompleteTextView = findViewById(R.id.auto_complete)
        val adapter = ArrayAdapter(this, R.layout.client_item, items)
        autoComplete.setAdapter(adapter)

        autoComplete.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                var itemSelected = adapterView.getItemAtPosition(i)
                Toast.makeText(this, "Item : $itemSelected", Toast.LENGTH_SHORT).show()
                selectedItem = itemSelected.toString()
            }

        binding.SaveLayout.setOnClickListener {
            for (i in 0 until data.size) {
                if (selectedItem == data.get(i).customerName) {
                    val no = binding.quantiti.text.toString()
                    val no2 = no.toInt()
                    val result = multiply(price, no2) + data.get(i).total
                    db.updateTotal(result,selectedItem)
                    linearLayout.setVisibility(View.INVISIBLE);

                    Toast.makeText(this, " ${data.get(i).customerName}+${result}", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}