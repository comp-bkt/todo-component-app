package com.example.todolistapp.data

import android.content.Context
import com.example.todolistapp.model.Todo
import java.util.*

class TodoModel private constructor() {
    val todos: ArrayList<Todo> = ArrayList()

    init {

        // refactor to pattern for data plugins
        // simulate some data for testing
        for (i in 0..29) {
            val todo = Todo()
            todo.title = "Todo title $i"
            todo.detail = "Detail for task " + todo.id.toString()
            todo.isComplete = false
            todos.add(todo)
        }
    }

    fun getTodo(todoId: UUID?): Todo? {
        for (todo in todos) {
            if (todo.id == todoId) {
                return todo
            }
        }
        return null
    }

    fun addTodo(todo: Todo) {
        todos.add(todo)
    }

    companion object {
        private var sTodoModel: TodoModel? = null
        fun get(): TodoModel? {
            if (sTodoModel == null) {
                sTodoModel = TodoModel()
            }
            return sTodoModel
        }
    }
}