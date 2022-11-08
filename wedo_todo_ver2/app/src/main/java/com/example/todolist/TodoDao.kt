package com.example.todolist

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDao {
    @Insert
    fun insert(todo: Todo)

    @Update
    fun update(todo: Todo)

    @Delete
    fun delete(todo: Todo)

    @Query("SELECT * FROM Todo") // 테이블의 모든 값을 가져와라
    fun getAll(): LiveData<MutableList<Todo>>

    @Query("DELETE FROM Todo WHERE title = :todo") // 'name'에 해당하는 유저를 삭제해라
    fun deleteTodoByName(todo: String)
}