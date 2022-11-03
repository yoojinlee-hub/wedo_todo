package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //Noti 객체 생성
    private lateinit var notificationHelper: NotificationHelper

    private lateinit var todoAdapter : TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        todoAdapter = TodoAdapter(mutableListOf())

        //to change title of activity
        val actionBar = supportActionBar
        actionBar!!.title = "Wedo Todo"

        rvTodoItems.adapter = todoAdapter
        rvTodoItems.layoutManager = LinearLayoutManager(this)

        btnAddTodo.setOnClickListener {
            val todoTitle = etTodoTitle.text.toString()
            if(todoTitle.isNotEmpty()) {
                val todo = Todo(todoTitle)
                todoAdapter.addTodo(todo)
                etTodoTitle.text.clear()
            }
        }
        btnDeleteDoneTodo.setOnClickListener {
            todoAdapter.deleteDoneTodos()

            //Noti 초기화
            notificationHelper = NotificationHelper(this)

            val titleEdit = "Wedo Todo";
            val title: String = titleEdit.toString()
            val messageEdit = "Congratuation\uD83D\uDC4F\uD83D\uDC4F you finished something todo! "
            val message: String = messageEdit.toString()

                //알림 호출
            showNotification(title, message)
        }
    }
    //알림 호출
    private fun showNotification(title: String, message: String){

        val nb: NotificationCompat.Builder =
            notificationHelper.getChannelNotification(title, message)

        notificationHelper.getManager().notify(1, nb.build())
    }
}