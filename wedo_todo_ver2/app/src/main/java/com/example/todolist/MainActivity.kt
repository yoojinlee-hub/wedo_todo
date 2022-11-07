package com.example.todolist

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.MyApplication.Companion.prefs
import com.example.todolist.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    //Noti 객체 생성
    private lateinit var notificationHelper: NotificationHelper
    private lateinit var todoAdapter : TodoAdapter


    //if(rs.moveToNext())
        //Toast.makeText(applicationContext,rs.getString(1),Toast.LENGTH_LONG).show()
    //getting shared preferences
  //  public var sp = getSharedPreferences("your_shared_pref_name", MODE_PRIVATE)
    //initializing editor
 //   public var editor = sp.edit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Toast.makeText(this@MainActivity, "오늘도 파이팅", Toast.LENGTH_SHORT).show()

        todoAdapter = TodoAdapter(mutableListOf())
        for(i in TodoAdapter(mutableListOf()).todos){
            todoAdapter.addTodo(prefs.getIt("todo_all", i.title))
        }
        // for(i in TodoAdapter(mutableListOf()).todos){
        //    if(i.isChecked){
        //       sp.all
        //     }
        //    }

        //to change title of activity
        val actionBar = supportActionBar
        actionBar!!.title = "root@root-Wedo Todo: - "

        rvTodoItems.adapter = todoAdapter

        rvTodoItems.layoutManager = LinearLayoutManager(this)

        btnAddTodo.setOnClickListener {
            val todoTitle = etTodoTitle.text.toString()
            val todo = Todo(todoTitle)
            todoAdapter.addTodo(todo)
            etTodoTitle.text.clear()

            MyApplication.prefs.setString("todo_all", todoTitle)
        }
        btnDeleteDoneTodo.setOnClickListener {
            val builder = AlertDialog.Builder(this)

            builder.setIcon(R.drawable.ic_baseline_info_24)    // 제목 아이콘
            builder.setTitle("정말로 삭제하시겠습니까?")    // 제목
            builder.setMessage("동의하십니까?")    // 내용
            // 긍정 버튼 추가
            builder.setPositiveButton("예") { dialog, which ->
                todoAdapter.deleteDoneTodos()

                //Noti 초기화
                notificationHelper = NotificationHelper(this)

                val titleEdit = "Wedo Todo";
                val title: String = titleEdit.toString()
                val messageEdit = "[root]: Congratuation\uD83D\uDC4F\uD83D\uDC4F you finished something todo! "
                val message: String = messageEdit.toString()

                //알림 호출
                showNotification(title, message)
            }
            // 부정 버튼 추가
            builder.setNegativeButton("취소") { dialog, which ->

            }

            // 뒤로 가기 or 바깥 부분 터치
            builder.setOnCancelListener {

            }
            builder.show()
        }
    }
    //알림 호출
    private fun showNotification(title: String, message: String){

        val nb: NotificationCompat.Builder =
            notificationHelper.getChannelNotification(title, message)

        notificationHelper.getManager().notify(1, nb.build())
    }
}


