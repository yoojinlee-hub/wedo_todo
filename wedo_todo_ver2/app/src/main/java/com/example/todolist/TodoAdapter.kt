package com.example.todolist

import android.content.SharedPreferences
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.item_todo.view.*

class TodoAdapter(
    val todos: MutableList<Todo>
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
  //  val editor: SharedPreferences.Editor = MainActivity().editor

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_todo,
                parent,
                false
            )
        )
    }

    private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked: Boolean) {
        if(isChecked) {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG

        } else {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    fun addTodo(todo: Todo) {
        todos.add(todo)
        notifyItemInserted(todos.size -1)

        //getSharedPreferences add
       // editor.putString("todo", todo.toString());
       // editor.apply();

    }

    fun deleteDoneTodos() {

        //getSharedPreferences delete
        for(i in todos){
            if(i.isChecked)
                MyApplication.prefs.delete("todo_all",i.toString())
        }
        //  editor.apply();

        todos.removeAll {
            todo -> todo.isChecked
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val curTodo = todos[position]
        holder.itemView.apply {
            for(i in todos){
                if(!i.isChecked){
                    tvTodoTitle.text = curTodo.title
                    cbDone.isChecked = curTodo.isChecked
                    toggleStrikeThrough(tvTodoTitle, curTodo.isChecked)
                    cbDone.setOnCheckedChangeListener { _, isChecked ->
                        toggleStrikeThrough(tvTodoTitle, isChecked)
                        curTodo.isChecked = !curTodo.isChecked
                    }
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }
}