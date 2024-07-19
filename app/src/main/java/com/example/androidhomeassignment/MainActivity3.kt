package com.example.androidhomeassignment
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidhomeassignment.databinding.ActivityMain2Binding
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

        var context = this;
        var db = DataBaseHelper(context)
        binding.button3.setOnClickListener {
            var etName = binding.productionName.text.toString()
            var etPrice = binding.production.price.text.toInt()
            if (etName.isNotEmpty() && etPrice.isNotEmpty())
            {
                var production = Production(etName,etPrice)
                db.insertData(production_Page)
                UpdateData(db)
                binding.productionName.text.clear()
                binding.production.price.text.clear()
            }else
            {
                Toast.makeText(applicationContext,"Fill the empty context",Toast.LENGTH_LONG).show()
            }
        }
        

        UpdateData(db)
    }

    fun UpdateData() {


    }
}