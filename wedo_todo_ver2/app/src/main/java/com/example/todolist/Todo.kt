package com.example.todolist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo (
    val title:      String,
    var isChecked:  Boolean = false
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
