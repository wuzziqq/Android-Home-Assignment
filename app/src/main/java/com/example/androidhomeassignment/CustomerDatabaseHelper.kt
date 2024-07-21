package com.example.androidhomeassignment

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

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

    fun updateTotal(total:Int,name:String) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(col_total,total)
        cv.put(col_name,name)
        val whereArgs = arrayOf(name)
        db.update(table_Name,cv,"$col_name=?",whereArgs)
    }

    fun exportData(context: Context,filename:String) {
        val db = writableDatabase
        val cursor : Cursor = db.rawQuery("SELECT * FROM $table_name",null)
        try {
            val externalStorage = Environment.getExternalStorageDirectory()
            val formattedFileName = if(!filename.endsWith(".csv") && !filename.endsWith(".db")) {
                "$filename.csv"
            } else {
                filename
            }

            val exportDirection = File(externalStorage,"exampleApp/exports")
            if (!exportDirection.exists()) {
                exportDirection.mkdirs()
            }

            val  file = File(exportDirection,formattedFileName)
            val outputStream = FileOutputStream(file)

            while (cursor.moveToNext()) {
                val data = "${cursor.getString(0)}, ${cursor.getString(1)} , ${cursor.getString(2)}, ${cursor.getString(3)}\n"
                outputStream.write(data.toByteArray())
            }
            outputStream.close()
            cursor.close()

            val fileSize = file.length()
            if (fileSize>0) {
                Toast.makeText(context, "data successufully exported", Toast.LENGTH_SHORT).show()
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME,formattedFileName)
                    put(MediaStore.MediaColumns.MIME_TYPE,"appliation/x-sqlite3")
                    put(MediaStore.MediaColumns.RELATIVE_PATH,"Documents/exampleApp/exports")
                }
                context.contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI,contentValues)
            }
            else {
                Toast.makeText(context, "Error exporting data", Toast.LENGTH_SHORT).show()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            println("Error")
        }
    }

}