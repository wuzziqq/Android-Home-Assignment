package com.example.androidhomeassignment

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val database_name = "Production Database"
val table_name = "Production"
val col_Name = "name"
val col_price = "price"

class ProductionDatabaseHelper (var context: Context):SQLiteOpenHelper(context,
    database_name,null,2)
{
    override fun onCreate(db: SQLiteDatabase?) {
        // Veritabani olusturdugunda birkez calisir
        var createTable = " CREATE TABLE " + table_name + "(" +
                col_Name + " VARCHAR(50)," +
                col_price + "INTEGER"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        //Veritabanini yukseltmek icin kullanilir
    }
    fun insertData(production: Production)
    {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(col_Name,production.productionName)
        cv.put(col_price,production.price)

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
    fun readData(): MutableList<Production> {
        var list:MutableList<Production> = ArrayList()
        val db = this.readableDatabase
        var query = "Select * from " + table_Name
        var result = db.rawQuery(query,null)
        if (result.moveToFirst())
        {
            do {
                var production = Production()
                production.productionName = result.getString(result.getColumnIndex(col_Name))
                production.price = result.getString(result.getColumnIndex(col_price)).toInt()
                list.add(production)
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

