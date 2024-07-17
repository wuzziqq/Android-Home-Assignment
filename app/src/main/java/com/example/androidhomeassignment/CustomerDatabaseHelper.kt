package com.example.androidhomeassignment

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val database_Name = "Customer Database"
val table_Name = "Customer"
val col_name = "name"
val col_country = "country"
val col_total = "total"
val col_id = "id"

class DataBaseHelper (var context: Context):SQLiteOpenHelper(context,
    database_Name,null,1)
{
    override fun onCreate(db: SQLiteDatabase?) {
        // Veritabani olusturdugunda birkez calisir
        var createTable = " CREATE TABLE " + table_Name + "(" +
                col_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                col_name + " VARCHAR(50)," +
                col_country + " VARCHAR(50)," +
                col_total + " INTEGER)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        //Veritabanini yukseltmek icin kullanilir
    }

    fun insertData(customer: Customer)
    {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(col_name,customer.customerName)
        cv.put(col_country,customer.country)
        cv.put(col_total,customer.total)
        var insert = db.insert(table_Name,null,cv)
        if (insert == (-1).toLong())
        {
            Toast.makeText(context,"Error",Toast.LENGTH_LONG).show()
        }else
        {
            Toast.makeText(context,"Success",Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("Range")
    fun readData(): MutableList<Customer> {
        var list:MutableList<Customer> = ArrayList()
        val db = this.readableDatabase
        var query = "Select * from " + table_Name
        var result = db.rawQuery(query,null)
        if (result.moveToFirst())
        {
            do {
                var customer = Customer()
                customer.id = result.getString(result.getColumnIndex(col_id)).toInt()
                customer.customerName = result.getString(result.getColumnIndex(col_name))
                customer.country = result.getString(result.getColumnIndex(col_country))
                customer.total = result.getString(result.getColumnIndex(col_total)).toInt()
                list.add(customer)

            }while (result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }

    fun deleteData(id: Int) {
        val db = this.writableDatabase
        val whereArgs = arrayOf("$id")
        db.delete(table_Name, col_id + "=?", whereArgs)
    }
}