package com.example.todolist

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.todolist.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    //Noti 객체 생성
    private lateinit var notificationHelper: NotificationHelper
    private var todoAdapter : TodoAdapter = TodoAdapter(mutableListOf())

    // room db
    private lateinit var db: TodoDatabase
    val datas = mutableListOf<Todo>()
    val datas_delete = mutableListOf<Todo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        //to change title of activity
        val actionBar = supportActionBar
        actionBar!!.title = "root@todo)list-WedoTodo: - "

        Toast.makeText(this@MainActivity, "오늘도 파이팅", Toast.LENGTH_SHORT).show()

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = TodoDatabase.getInstance(applicationContext)!!

        rvTodoItems.adapter = todoAdapter
        rvTodoItems.layoutManager = LinearLayoutManager(this)

        //room에서 데이터 불러와야 함
        val r = Runnable {
            val todoes = db.todoDao().getAll()
            datas.clear()

            for(todo in todoes) {
                //if(!todo.isChecked)
                    datas.add(Todo(todo.title,todo.isChecked))
            }
            todoAdapter.todos = datas
        }
        val thread = Thread(r)
        thread.start()

        todoAdapter.notifyDataSetChanged()


        btnAddTodo.setOnClickListener {
            val todoTitle = etTodoTitle.text.toString()
            val todo = Todo(todoTitle)

            CoroutineScope(Dispatchers.IO).launch { // 다른애 한테 일 시키기
                db!!.todoDao().insert(todo)
            }
            //db.todoDao().insert(todo) -> ERROR

            todoAdapter.addTodo(todo)
            etTodoTitle.text.clear()
        }
        btnDeleteDoneTodo.setOnClickListener {
            val builder = AlertDialog.Builder(this)

            builder.setIcon(R.drawable.ic_baseline_info_24)    // 제목 아이콘
            builder.setTitle("정말로 삭제하시겠습니까?")    // 제목
            builder.setMessage("동의하십니까?")    // 내용
            // 긍정 버튼 추가
            builder.setPositiveButton("예") { dialog, which ->
                var count = 0
                val r = Runnable {
                    val todoes = db.todoDao().getAll()

                    for(i in todoAdapter.todos){
                        if(i.isChecked){
                            db.todoDao().deleteTodoByName(i.title)
                            count+=1
                        }
                    }
                }
                val thread = Thread(r)
                thread.start()

                todoAdapter.deleteDoneTodos()

                //Noti 초기화
                notificationHelper = NotificationHelper(this)

                val titleEdit = "Wedo Todo";
                val title: String = titleEdit.toString()
                val messageEdit = "[root]: Congratuation\uD83D\uDC4F\uD83D\uDC4F you finished "+count+" things todo! "
                val message: String = messageEdit.toString()

                //알림 호출
                showNotification(title, message)
            }
            // 부정 버튼 추가
            builder.setNegativeButton("취소") { dialog, which ->
                Toast.makeText(this@MainActivity, "취소하셨습니다", Toast.LENGTH_SHORT).show()
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


