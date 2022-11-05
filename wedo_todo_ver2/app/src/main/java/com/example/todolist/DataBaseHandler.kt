package com.example.todolist

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DATABASE_NAME = "MyDB"
val TABLE_NAME = "TODOS"
val COL_NAME="content"
val COL_ISDONE = "done"


class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,1){
    override fun onCreate(p0: SQLiteDatabase?) {

        val createTable = "CREATE TABLE" + TABLE_NAME + " (" + COL_NAME + " VARCHAR(256)," + COL_ISDONE + "BOOLEAN";

        p0?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
    fun insertData( todo : Todo){
        val p0 = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_NAME,todo.title)
        cv.put(COL_ISDONE,todo.isChecked)
        var result = p0.insert(TABLE_NAME,null,cv)
        if(result == -1.toLong())
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show()
    }
}